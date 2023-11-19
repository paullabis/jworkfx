package com.jworkfx.workflow;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("workflow")
public class WorkflowProperties {

    private boolean enable;

    private String[] definitions;

}
