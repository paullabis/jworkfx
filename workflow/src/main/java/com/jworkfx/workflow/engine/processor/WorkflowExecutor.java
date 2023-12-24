package com.jworkfx.workflow.engine.processor;

import com.jworkfx.workflow.constant.TaskType;
import com.jworkfx.workflow.domain.Path;
import com.jworkfx.workflow.domain.Task;
import com.jworkfx.workflow.domain.Workflow;
import com.jworkfx.workflow.engine.parts.TaskCode;
import com.jworkfx.workflow.engine.parts.TaskExecutor;
import com.jworkfx.workflow.engine.parts.TaskFactory;
import com.jworkfx.workflow.execution.entity.ExecutionLog;
import com.jworkfx.workflow.execution.entity.Variable;
import com.jworkfx.workflow.execution.entity.WorkflowExecution;
import com.jworkfx.workflow.execution.service.ExecutionLogService;
import com.jworkfx.workflow.execution.service.VariableService;
import com.jworkfx.workflow.execution.service.WorkflowExecutionService;
import com.jworkfx.workflow.service.WorkflowService;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static com.jworkfx.workflow.execution.constant.WorkflowExecutionStatus.*;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Builder
@Data
@Slf4j
public class WorkflowExecutor implements TaskExecutor {

    private final WorkflowService workflowService;
    private final WorkflowExecutionService workflowExecutionService;
    private final VariableService variableService;
    private final ExecutionLogService executionLogService;
    private final TaskFactory taskFactory;
    private final Workflow workflow;
    private final WorkflowVariableConverter variableConverter;
    private WorkflowExecution workflowExecution;

    @Override
    public void run() {
        try {
            final Optional<WorkflowExecution> workflowExecutionOpt = workflowExecutionService
                    .getWorkflowExecutionById(workflowExecution.getId());
            if (workflowExecutionOpt.isEmpty()) {
                log.error("No WorkflowExecution found for id = {}", workflowExecution.getId());
                return;
            }

            workflowExecution = workflowExecutionOpt.get();
            if (EnumSet.of(SUSPENDED, DELETED, RUNNING).contains(workflowExecution.getStatus())) {
                log.error("WorkflowExecution with id = {} is not qualified to run", workflowExecution.getId());
                return;
            }

            workflowExecution.setStatus(RUNNING);
            workflowExecution = workflowExecutionService.updateWorkflowExecution(workflowExecution);

            runWorkflowTasks();
        } catch (Exception ex) {
            log.error("run failed: {}", ex.getMessage(), ex);
        }
    }

    private void runWorkflowTasks() {

        boolean isExecute = true;
        while (isExecute) {
            String currentTaskName = workflowExecution.getCurrentTask();
            if (isBlank(currentTaskName)) {
                updateWorkflowExecutionStartTime();
                addExecutionLog(TaskType.START.name(), 0L, "Workflow is starting.");
                currentTaskName = findStartTaskDestinationTaskName(workflow.getTasks());
                workflowExecution.setCurrentTask(currentTaskName);
            }

            Task currentTask = getTaskByName(workflow.getTasks(), currentTaskName);
            if (TaskType.END.equals(currentTask.getType())) {
                addExecutionLog(TaskType.END.name(), 0L, "Workflow is completed.");
                endWorkflowExecution();
                isExecute = false;
                continue;
            }

            final TaskCode taskCode = taskFactory.getTask(currentTask.getBeanId());
            final long startTime = Instant.now().toEpochMilli();
            taskCode.execute(this);
            final long duration = Instant.now().toEpochMilli() - startTime;
            addExecutionLog(currentTaskName, duration, "Executing task.");
        }

    }

    private void endWorkflowExecution() {
        workflowExecution.setStatus(COMPLETED);
        workflowExecution.setEndTime(Instant.now().toEpochMilli());
        workflowExecutionService.updateWorkflowExecution(workflowExecution);
    }

    private void updateWorkflowExecutionStartTime() {
        workflowExecution.setStartTime(Instant.now().toEpochMilli());
        workflowExecutionService.updateWorkflowExecution(workflowExecution);
    }

    private void addExecutionLog(
            final String taskName,
            final Long durationMillis,
            final String message
    ) {
        final ExecutionLog executionLog = ExecutionLog.builder()
                .workflowExecutionId(workflowExecution.getId())
                .duration(durationMillis)
                .task(taskName)
                .message(message)
                .build();
        executionLogService.createExecutionLog(executionLog);
    }

    @Override
    public <T> Optional<T> getVariable(
            @NonNull final Class<T> clazz,
            @NonNull final String name
    ) {
        final Optional<Variable> variableOpt = variableService
                .getVariableByWorkflowExecutionIdAndName(workflowExecution.getId(), name);
        return variableOpt.map(variable -> variableConverter.convertVariableValue(variable, clazz));
    }

    @Override
    public Variable setVariable(
            @NonNull final String name,
            @NonNull final Object value
    ) {
        final Optional<Variable> variableOpt = variableService
                .getVariableByWorkflowExecutionIdAndName(workflowExecution.getId(), name);
        variableOpt.ifPresent(variable -> variableService.deleteVariable(variable.getId()));

        final Variable variable = variableConverter.convertToVariable(value);
        variable.setName(name);
        variable.setWorkflowExecutionId(workflowExecution.getId());
        return variableService.createVariable(variable);
    }

    @Override
    public boolean hasVariable(@NonNull final String name) {
        return variableService.getVariableByWorkflowExecutionIdAndName(workflowExecution.getId(), name).isPresent();
    }

    @Override
    public void removeVariable(@NonNull final String name) {
        variableService.removeVariableByWorkflowExecutionIdAndName(workflowExecution.getId(), name);
    }

    @Override
    public void setNextPath(final String pathName) {
        final Task currentTask = getTaskByName(workflow.getTasks(), workflowExecution.getCurrentTask());
        final Path nextPath = currentTask.getNextPath().stream()
                .filter(path -> path.getName().equalsIgnoreCase(pathName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No path found with name `{0}`.", pathName)));
        workflowExecution.setCurrentTask(nextPath.getDestinationTaskName());
    }

    // HELPER
    private String findStartTaskDestinationTaskName(@NonNull final List<Task> tasks) {
        Optional<String> startTaskDestination = tasks.stream()
                .filter(task -> task.getType() == TaskType.START)
                .flatMap(startTask -> startTask.getNextPath().stream())
                .map(Path::getDestinationTaskName)
                .findFirst();
        return startTaskDestination.orElseThrow(() -> new IllegalArgumentException("No starting task found with a destination task name."));
    }

    private Task getTaskByName(List<Task> tasks, @NonNull final String taskName) {
        return tasks.stream()
                .filter(task -> equalsIgnoreCase(taskName, task.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No task found with name `{0}`", taskName)));
    }
}
