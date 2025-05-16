package com.github.qlang.core.ast;

import com.github.qlang.core.function.Function;
import com.github.qlang.core.function.FunctionContainer;
import com.github.qlang.core.type.QObject;

public interface Context {

    Object getVariable(String variable);

    void setVariable(String variable, QObject value);

    default Function getFunction(String functionName) {
        return FunctionContainer.getFunction(functionName);
    }
}
