package com.jworkfx.workflow.execution.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document
public class ExecutionLog extends AuditableEntity {
    private String workflowId;
    private String task;
    private String message;
    private long duration;
}
