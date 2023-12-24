package com.jworkfx.workflow.engine.parts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class TaskFactory {

    @Autowired
    private Map<String, TaskCode> tasks;

    public TaskCode getTask(String name) {
        return tasks.get(name);
    }

}
