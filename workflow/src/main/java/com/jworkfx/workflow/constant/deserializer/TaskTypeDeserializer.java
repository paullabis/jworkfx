package com.jworkfx.workflow.constant.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jworkfx.workflow.constant.TaskType;

import java.io.IOException;

public class TaskTypeDeserializer extends JsonDeserializer<TaskType> {
    @Override
    public TaskType deserialize(
            final JsonParser parser,
            final DeserializationContext ctxt) throws IOException {
        TaskType taskType;
        switch (parser.getText()) {
            case "START":
                taskType = TaskType.START;
                break;
            case "SERVICE":
                taskType = TaskType.SERVICE;
                break;
            case "RECEIVE":
                taskType = TaskType.RECEIVE;
                break;
            case "END":
                taskType = TaskType.END;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + parser.getText());
        }
        return taskType;
    }
}
