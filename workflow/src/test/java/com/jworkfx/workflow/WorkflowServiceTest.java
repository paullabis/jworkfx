package com.jworkfx.workflow;

import com.jworkfx.workflow.service.WorkflowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("workflow.enable=true")
public class WorkflowServiceTest {

    @Autowired
    private WorkflowService workflowService;

    @Test
    public void contextLoads() {
        assertThat(workflowService.isEnable()).isTrue();
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

}
