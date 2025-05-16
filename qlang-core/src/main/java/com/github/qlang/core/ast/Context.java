package com.github.qlang.core.ast;

import com.github.qlang.core.function.Function;
import com.github.qlang.core.function.FunctionContainer;

public interface Context {

    Object getVariable(String variable);

    void setVariable(String variable, Object value);

    default Function getFunction(String functionName) {
        return FunctionContainer.getFunction(functionName);
    }
}
