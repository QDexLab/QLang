package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.function.Function;
import io.github.qdexlab.qlang.core.type.QObject;

public class FuncOp extends Node {

    private final String name;
    private final Node[] arguments;

    public FuncOp(String name, Node[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public QObject eval(Context context) {
        Function function = context.getFunction(this.name);
        if (function != null) {
            QObject[] args = new QObject[this.arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                args[i] = this.arguments[i].eval(context);
            }
            return function.call(args);
        }
        throw new RuntimeException("function " + this.name + " not found");
    }

    public String getName() {
        return name;
    }

    public Node[] getArguments() {
        return arguments;
    }
}
