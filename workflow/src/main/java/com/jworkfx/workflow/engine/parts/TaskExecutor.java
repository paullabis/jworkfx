package com.jworkfx.workflow.engine.parts;

import com.jworkfx.workflow.execution.entity.Variable;

import java.util.Optional;

public interface TaskExecutor extends Runnable {

    <T> Optional<T> getVariable(Class<T> clazz, String name);

    Variable setVariable(String name, Object value);

    boolean hasVariable(String name);

    void removeVariable(String name);

    void setNextPath(String name);
}
