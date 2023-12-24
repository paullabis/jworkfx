package com.jworkfx.workflow.execution.service;

import com.jworkfx.workflow.execution.dao.ExecutionLogRepository;
import com.jworkfx.workflow.execution.entity.ExecutionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExecutionLogService {

    private final ExecutionLogRepository executionLogRepository;


    public List<ExecutionLog> getAllExecutionLogs() {
        return executionLogRepository.findAll();
    }

    public Optional<ExecutionLog> getExecutionLogById(String id) {
        return executionLogRepository.findById(id);
    }

    public ExecutionLog createExecutionLog(ExecutionLog executionLog) {
        return executionLogRepository.save(executionLog);
    }

    public ExecutionLog updateExecutionLog(ExecutionLog executionLog) {
        return executionLogRepository.save(executionLog);
    }

    public void deleteExecutionLog(String id) {
        executionLogRepository.deleteById(id);
    }
}
