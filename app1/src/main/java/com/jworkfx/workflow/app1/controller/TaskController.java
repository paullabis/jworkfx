package com.jworkfx.workflow.app1.controller;

import com.jworkfx.workflow.engine.WorkflowEngine;
import com.jworkfx.workflow.service.WorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/")
@Slf4j
public class TaskController {

    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private WorkflowEngine workflowEngine;

    @GetMapping
    public String version() {
        return "1.0";
    }

    @GetMapping("workflow")
    public String workflowEnabled() {
        return workflowService.isEnable() + "";
    }

    @GetMapping("workflow/test")
    public void runHelloWorkflow() {
        try {
            workflowEngine.start("HelloWorkflow", UUID.randomUUID().toString());
        } catch (Exception ex) {
            log.error("runHelloWorkflow failed because `{}`", ex.getMessage(), ex);
        }
    }

}
