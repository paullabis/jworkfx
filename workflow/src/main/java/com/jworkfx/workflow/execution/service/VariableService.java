package com.jworkfx.workflow.execution.service;

import com.jworkfx.workflow.execution.dao.VariableRepository;
import com.jworkfx.workflow.execution.entity.Variable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VariableService {
    private final VariableRepository variableRepository;

    public List<Variable> getAllVariables() {
        return variableRepository.findAll();
    }

    public Optional<Variable> getVariableById(String id) {
        return variableRepository.findById(id);
    }

    public Variable createVariable(Variable variable) {
        return variableRepository.save(variable);
    }

    public Variable updateVariable(Variable variable) {
        return variableRepository.save(variable);
    }

    public void deleteVariable(String id) {
        variableRepository.deleteById(id);
    }

    public List<Variable> createAllVariable(List<Variable> variables) {
        return variableRepository.saveAll(variables);
    }

    public Optional<Variable> getVariableByWorkflowExecutionIdAndName(String workflowExecutionId, String name) {
        return variableRepository.findByWorkflowExecutionIdAndName(workflowExecutionId, name);
    }

    public void removeVariableByWorkflowExecutionIdAndName(String workflowExecutionId, String name) {
        variableRepository.removeByWorkflowExecutionIdAndName(workflowExecutionId, name);
    }
}
