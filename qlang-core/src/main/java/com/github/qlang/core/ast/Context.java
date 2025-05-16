package com.github.qlang.core.ast;

public interface Context {

    Object getVariable(String variable);

    void setVariable(String variable, Object value);
}
