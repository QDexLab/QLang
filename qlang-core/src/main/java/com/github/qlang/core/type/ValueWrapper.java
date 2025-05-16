package com.github.qlang.core.type;

import com.github.qlang.core.exception.EvalException;

import java.util.Collection;
import java.util.Map;

public class ValueWrapper {

    public static Object wrap(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof QObject) {
            return value;
        } else if (value instanceof QNumber || value instanceof String || value instanceof Boolean) {
            return value;
        } else if (value instanceof Map || value instanceof Collection) {
            return value;
        } else if (value instanceof CharSequence) {
            return value.toString();
        } else if (value instanceof Number) {
            return QNumber.valueOf(value.toString());
        } else {
            throw new EvalException("unsupported type: " + value.getClass() + ", value: " + value);
        }
    }
}
