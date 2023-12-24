package com.jworkfx.workflow.app1.tasks;

import com.jworkfx.workflow.engine.parts.TaskCode;
import com.jworkfx.workflow.engine.parts.TaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("com.jworkfx.app1.tasks.LogHelloTask")
@Slf4j
public class LogHelloTask implements TaskCode {
    @Override
    public void execute(TaskExecutor taskExecutor) {

        taskExecutor.setVariable("name", "Hello! Nice to meet you ");

        log.info("Generated greeting variable and store it in database.");
        taskExecutor.setNextPath("LogRandomNamePath");
    }
}
