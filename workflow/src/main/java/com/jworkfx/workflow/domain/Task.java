package com.jworkfx.workflow.domain;

import com.jworkfx.workflow.domain.constant.TaskType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Task {
    private TaskType type;
    private String name;
    private String description;
    private String beanId;
    private List<Path> nextPath;
}
