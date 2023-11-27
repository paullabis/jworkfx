package com.jworkfx.workflow.execution.entity;

import com.jworkfx.workflow.execution.constant.VariableType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document
public class Variable extends AuditableEntity {
    private String workflowExecutionId;
    private String name;
    private VariableType type;
    private String value;
}
