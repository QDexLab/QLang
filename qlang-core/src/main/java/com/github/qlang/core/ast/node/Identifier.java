package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public class Identifier extends Node {

    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public Object eval(Context context) {
        return context.getVariable(name);
    }
}
