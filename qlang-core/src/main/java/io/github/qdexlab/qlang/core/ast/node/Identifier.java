package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.type.QObject;

public class Identifier extends Node {

    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public QObject eval(Context context) {
        return context.getVariable(name);
    }

    public String getName() {
        return name;
    }
}
