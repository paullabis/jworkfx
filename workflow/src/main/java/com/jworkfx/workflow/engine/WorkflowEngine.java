package com.jworkfx.workflow.engine;

import com.jworkfx.workflow.domain.Workflow;
import com.jworkfx.workflow.engine.parts.TaskFactory;
import com.jworkfx.workflow.engine.processor.WorkflowExecutor;
import com.jworkfx.workflow.engine.processor.WorkflowVariableConverter;
import com.jworkfx.workflow.execution.constant.WorkflowExecutionStatus;
import com.jworkfx.workflow.execution.entity.ExecutionLog;
import com.jworkfx.workflow.execution.entity.Variable;
import com.jworkfx.workflow.execution.entity.WorkflowExecution;
import com.jworkfx.workflow.execution.service.ExecutionLogService;
import com.jworkfx.workflow.execution.service.VariableService;
import com.jworkfx.workflow.execution.service.WorkflowExecutionService;
import com.jworkfx.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowEngine {

    private final WorkflowService workflowService;
    private final WorkflowExecutionService workflowExecutionService;
    private final VariableService variableService;
    private final ExecutionLogService executionLogService;
    private final TaskFactory taskFactory;
    private final WorkflowVariableConverter workflowVariableConverter;

    public void start(
            final String workflowId,
            final String externalReference
    ) {
        this.start(workflowId, externalReference, Collections.emptyList());
    }

    public void start(
            final String workflowId,
            final String externalReference,
            final List<Object> variables
    ) {
        final Workflow workflow = workflowService.getWorkflowById(workflowId);
        if (workflow == null) {
            throw new IllegalStateException("Workflow is missing.");
        }

        if (externalReference == null || externalReference.isBlank()) {
            throw new IllegalStateException("Invalid externalReference.");
        }

        WorkflowExecution workflowExecution = WorkflowExecution.builder()
                .workflowId(workflowId)
                .status(WorkflowExecutionStatus.QUEUED)
                .externalReference(externalReference)
                .build();
        workflowExecutionService.createWorkflowExecution(workflowExecution);

        ExecutionLog executionLog = ExecutionLog.builder()
                .workflowExecutionId(workflowExecution.getId())
                .duration(0L)
                .message("Queued / Waiting to start.")
                .build();
        executionLogService.createExecutionLog(executionLog);

        List<Variable> workflowExecutionVariables = workflowVariableConverter.convertToVariables(variables);
        workflowExecutionVariables.forEach(variable -> variable.setWorkflowExecutionId(workflowExecution.getId()));
        variableService.createAllVariable(workflowExecutionVariables);

        processWorkflowConcurrently(workflow, workflowExecution);
    }

    @Async("workflowThreadPoolExecutor")
    public void processWorkflowConcurrently(
            final Workflow workflow,
            final WorkflowExecution workflowExecution
    ) {
        try {
            final WorkflowExecutor workflowExecutor = WorkflowExecutor.builder()
                    .workflow(workflow)
                    .workflowExecution(workflowExecution)
                    .taskFactory(taskFactory)
                    .executionLogService(executionLogService)
                    .variableService(variableService)
                    .workflowExecutionService(workflowExecutionService)
                    .workflowService(workflowService)
                    .variableConverter(workflowVariableConverter)
                    .build();
            workflowExecutor.run();
        } catch (Exception ex) {
            log.error("run failed:", ex);
        }
    }

}