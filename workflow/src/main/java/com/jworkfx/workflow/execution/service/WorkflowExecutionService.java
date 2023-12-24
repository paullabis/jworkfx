package com.jworkfx.workflow.execution.service;

import com.jworkfx.workflow.execution.dao.WorkflowExecutionRepository;
import com.jworkfx.workflow.execution.entity.WorkflowExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkflowExecutionService {
    private final WorkflowExecutionRepository workflowExecutionRepository;

    public List<WorkflowExecution> getAllWorkflowExecutions() {
        return workflowExecutionRepository.findAll();
    }

    public Optional<WorkflowExecution> getWorkflowExecutionById(String id) {
        return workflowExecutionRepository.findById(id);
    }

    public WorkflowExecution createWorkflowExecution(WorkflowExecution execution) {
        return workflowExecutionRepository.save(execution);
    }

    public WorkflowExecution updateWorkflowExecution(WorkflowExecution execution) {
        return workflowExecutionRepository.save(execution);
    }

    public void deleteWorkflowExecution(String id) {
        workflowExecutionRepository.deleteById(id);
    }
}
