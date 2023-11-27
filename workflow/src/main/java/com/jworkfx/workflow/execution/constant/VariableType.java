package com.jworkfx.workflow.execution.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jworkfx.workflow.constant.deserializer.TaskTypeDeserializer;

@JsonDeserialize(using = TaskTypeDeserializer.class)
public enum VariableType {
    STRING,
    BOOLEAN,
    DECIMAL
}
