package com.jworkfx.workflow.execution.dao;

import com.jworkfx.workflow.execution.entity.Variable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VariableRepository extends MongoRepository<Variable, String> {

    Optional<Variable> findByWorkflowExecutionIdAndName(String workflowExecutionId, String name);
    void removeByWorkflowExecutionIdAndName(String workflowExecutionId, String name);

}
