package com.jworkfx.workflow.engine.parts.task;

import com.jworkfx.workflow.engine.parts.TaskCode;
import com.jworkfx.workflow.engine.parts.TaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * A sample task whose ID is its class reference.
 */
@Component("com.jworkfx.workflow.engine.parts.task.LogTask")
@Slf4j
public class LogTask implements TaskCode {
    public void execute(TaskExecutor taskExecutor) {
        log.info("LogTask task");
    }
}
