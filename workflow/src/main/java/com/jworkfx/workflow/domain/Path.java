package com.jworkfx.workflow.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Path {
    private String name;
    private String destinationTaskName;
}
