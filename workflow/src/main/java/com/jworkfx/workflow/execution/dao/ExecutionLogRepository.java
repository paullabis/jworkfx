package com.jworkfx.workflow.execution.dao;

import com.jworkfx.workflow.execution.entity.ExecutionLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionLogRepository extends MongoRepository<ExecutionLog, String> {

}
