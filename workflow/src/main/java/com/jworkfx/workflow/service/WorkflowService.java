package com.jworkfx.workflow.service;

import com.jworkfx.workflow.WorkflowProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(WorkflowProperties.class)
public class WorkflowService {

    private final WorkflowProperties workflowProperties;

    public WorkflowService(WorkflowProperties workflowProperties) {
        this.workflowProperties = workflowProperties;
    }

    public boolean isEnable() {
        return this.workflowProperties.isEnable();
    }
}
