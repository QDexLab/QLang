package com.github.qlang.core.type;

import com.github.qlang.core.exception.EvalException;

import java.util.Collection;
import java.util.Map;

public class ValueWrapper {

    public static QObject wrap(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof QObject) {
            return (QObject) value;
        } else if (value instanceof Boolean) {
            return QBool.valueOf((Boolean) value);
        } else if (value instanceof CharSequence) {
            return QString.valueOf(value.toString());
        } else if (value instanceof Number) {
            return QNumber.valueOf(value.toString());
        } else if (value instanceof Map || value instanceof Collection) {
            // todo 实现
            throw new EvalException("unsupported type: " + value.getClass() + ", value: " + value);
        } else {
            throw new EvalException("unsupported type: " + value.getClass() + ", value: " + value);
        }
    }
}
