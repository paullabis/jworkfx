package com.jworkfx.app1.controller;

import com.jworkfx.workflow.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class TaskController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping
    public String version() {
        return "1.0";
    }

    @GetMapping("workflow")
    public String workflowEnabled() {
        return workflowService.isEnable() + "";
    }

}
