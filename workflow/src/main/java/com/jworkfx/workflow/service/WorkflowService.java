package com.jworkfx.workflow.service;

import com.jworkfx.workflow.WorkflowProperties;
import com.jworkfx.workflow.domain.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableConfigurationProperties(WorkflowProperties.class)
@Slf4j
public class WorkflowService {

    private final JsonDefinitionParser jsonDefinitionParser;
    private final WorkflowProperties workflowProperties;

    private Map<String, Workflow> workflows = new HashMap<>();

    public WorkflowService(
            final JsonDefinitionParser jsonDefinitionParser,
            final WorkflowProperties workflowProperties
    ) {
        this.jsonDefinitionParser = jsonDefinitionParser;
        this.workflowProperties = workflowProperties;

        // Load workflows from configuration during initialization
        loadWorkflowsFromConfiguration();
    }

    public boolean isEnable() {
        return this.workflowProperties.isEnable();
    }
    public Workflow getWorkflowByName(final String name) {
        return workflows.get(name);
    }

    public List<Workflow> getAllWorkflows() {
        return List.copyOf(workflows.values());
    }

    private void loadWorkflowsFromConfiguration() {
        final String[] workflowDefinitions = workflowProperties.getDefinitions();
        if (workflowDefinitions == null) {
            log.info("No definitions configured.");
            return;
        }
        for (final String workflowDefinition : workflowDefinitions) {
            jsonDefinitionParser.parseJsonDefinition(workflowDefinition)
                    .ifPresent(workflow -> workflows.put(workflow.getName(), workflow));
        }
    }
}
