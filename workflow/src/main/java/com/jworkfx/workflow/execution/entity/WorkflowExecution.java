package com.jworkfx.workflow.execution.entity;

import com.jworkfx.workflow.execution.constant.WorkflowExecutionStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document
public class WorkflowExecution extends AuditableEntity {
    private String workflowId;
    private WorkflowExecutionStatus status;
    private String externalReference;
    private long startTime;
    private long endTime;
    private String currentTask;

}
