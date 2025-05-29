package io.github.qdexlab.qlang.core.ast;

import io.github.qdexlab.qlang.core.function.Function;
import io.github.qdexlab.qlang.core.function.FunctionContainer;
import io.github.qdexlab.qlang.core.type.QObject;

public interface Context {

    QObject getVariable(String variable);

    void setVariable(String variable, QObject value);

    default Function getFunction(String functionName) {
        return FunctionContainer.getFunction(functionName);
    }
}
