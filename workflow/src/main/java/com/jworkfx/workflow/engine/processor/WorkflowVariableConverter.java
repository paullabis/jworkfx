package com.jworkfx.workflow.engine.processor;
import com.jworkfx.workflow.execution.constant.VariableType;
import com.jworkfx.workflow.execution.entity.Variable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowVariableConverter {
    public List<Variable> convertToVariables(List<Object> objects) {
        return objects.stream()
                .map(this::convertToVariable)
                .collect(Collectors.toList());
    }

    public Variable convertToVariable(Object object) {
        if (object instanceof Variable) {
            return (Variable) object;
        } else if (object instanceof String) {
            return createVariableFromString((String) object);
        } else if (object instanceof Boolean) {
            return createVariableFromBoolean((Boolean) object);
        } else if (object instanceof Integer) {
            return createVariableFromInteger((Integer) object);
        } else if (object instanceof Long) {
            return createVariableFromLong((Long) object);
        } else if (object instanceof Double) {
            return createVariableFromDouble((Double) object);
        } else if (object instanceof BigDecimal) {
            return createVariableFromBigDecimal((BigDecimal) object);
        } else {
            throw new IllegalArgumentException("Unsupported object type: " + object.getClass());
        }
    }

    private Variable createVariableFromString(String value) {
        Variable variable = new Variable();
        variable.setType(VariableType.STRING);
        variable.setValue(value);
        return variable;
    }

    private Variable createVariableFromBoolean(Boolean value) {
        Variable variable = new Variable();
        variable.setType(VariableType.BOOLEAN);
        variable.setValue(value.toString());
        return variable;
    }

    private Variable createVariableFromDouble(Double value) {
        Variable variable = new Variable();
        variable.setType(VariableType.BIG_DECIMAL);
        variable.setValue(value.toString());
        return variable;
    }

    private Variable createVariableFromInteger(Integer value) {
        Variable variable = new Variable();
        variable.setType(VariableType.INTEGER);
        variable.setValue(value.toString());
        return variable;
    }

    private Variable createVariableFromLong(Long value) {
        Variable variable = new Variable();
        variable.setType(VariableType.LONG);
        variable.setValue(value.toString());
        return variable;
    }

    private Variable createVariableFromBigDecimal(BigDecimal value) {
        Variable variable = new Variable();
        variable.setType(VariableType.BIG_DECIMAL);
        variable.setValue(value.toString());
        return variable;
    }

    @SuppressWarnings("unchecked")
    public <T> T convertVariableValue(
            final Variable variable,
            final Class<T> clazz
    ) {
        final String value = variable.getValue();
        if (value == null || clazz == null) {
            return null;
        }

        final VariableType type = variable.getType();
        switch (type) {
            case STRING:
                return (T) value;
            case BOOLEAN:
                if (Boolean.class.isAssignableFrom(clazz) || Boolean.TYPE.isAssignableFrom(clazz)) {
                    return (T) Boolean.valueOf(value);
                }
                break;
            case INTEGER:
                if (Integer.class.isAssignableFrom(clazz) || Integer.TYPE.isAssignableFrom(clazz)) {
                    return (T) Integer.valueOf(value);
                }
                break;
            case LONG:
                if (Long.class.isAssignableFrom(clazz) || Long.TYPE.isAssignableFrom(clazz)) {
                    return (T) Long.valueOf(value);
                }
                break;
            case DOUBLE:
                if (Double.class.isAssignableFrom(clazz) || Double.TYPE.isAssignableFrom(clazz)) {
                    return (T) Double.valueOf(value);
                }
                break;
            case BIG_DECIMAL:
                if (BigDecimal.class.isAssignableFrom(clazz)) {
                    return (T) new BigDecimal(value);
                }
                break;
            default:
                break;
        }

        return null;
    }
}
