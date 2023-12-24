package com.jworkfx.workflow.execution.dao;

import com.jworkfx.workflow.execution.entity.WorkflowExecution;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowExecutionRepository extends MongoRepository<WorkflowExecution, String> {

}
