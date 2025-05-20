package com.github.qdexlab.qlang.core.ast;

import com.github.qdexlab.qlang.core.function.Function;
import com.github.qdexlab.qlang.core.function.FunctionContainer;
import com.github.qdexlab.qlang.core.type.QObject;

public interface Context {

    QObject getVariable(String variable);

    void setVariable(String variable, QObject value);

    default Function getFunction(String functionName) {
        return FunctionContainer.getFunction(functionName);
    }
}
