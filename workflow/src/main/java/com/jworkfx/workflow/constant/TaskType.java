package com.jworkfx.workflow.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jworkfx.workflow.constant.deserializer.TaskTypeDeserializer;

@JsonDeserialize(using = TaskTypeDeserializer.class)
public enum TaskType {
    START,
    SERVICE,
    RECEIVE,
    END
}
