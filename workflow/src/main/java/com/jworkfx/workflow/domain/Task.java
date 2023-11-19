package com.jworkfx.workflow.domain;

import com.jworkfx.workflow.constant.TaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private TaskType type;
    private String name;
    private String description;
    private String beanId;
    private List<Path> nextPath;
}
