package com.github.qdexlab.qlang.core.type;

import com.github.qdexlab.qlang.core.exception.MethodEvalException;
import com.github.qdexlab.qlang.core.utils.ReflectUtils;

import java.lang.reflect.Method;
import java.util.Arrays;


public class QObject {

    public QObject call(String name, QObject[] args) {
        try {
            Method method = ReflectUtils.findMethod(getClass(), name, Arrays.stream(args).map(QObject::getClass).toArray(Class[]::new));
            return (QObject) method.invoke(this, (Object[]) args);
        } catch (Exception e) {
            throw new MethodEvalException("error while calling " + name, e);
        }
    }

}
