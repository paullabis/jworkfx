package com.jworkfx.workflow.app1.tasks;

import com.jworkfx.workflow.engine.parts.TaskCode;
import com.jworkfx.workflow.engine.parts.TaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("com.jworkfx.app1.tasks.LogRandomNameTask")
@Slf4j
public class LogRandomNameTask implements TaskCode {
    @Override
    public void execute(TaskExecutor taskExecutor) {

        try {
            log.info("Pause for 10 seconds.");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ignore) {}

        final String greeting = taskExecutor.getVariable(String.class, "name")
                .orElse("Greetings!");
        log.info(greeting.concat(RandomStringUtils.randomAlphabetic(5)).concat("..."));
        taskExecutor.setNextPath("EndTaskPath");
    }
}
