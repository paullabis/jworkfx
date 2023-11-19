package com.jworkfx.workflow.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Workflow {

    private String name;
    private String description;
    private List<Task> tasks;

}
