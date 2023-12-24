package com.jworkfx.workflow.execution.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "execution_log")
public class ExecutionLog extends AuditableEntity {
    private String workflowExecutionId;
    private String task;
    private String message;
    private long duration;
}
