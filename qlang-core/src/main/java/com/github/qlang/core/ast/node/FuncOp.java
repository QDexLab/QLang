package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.function.Function;
import com.github.qlang.core.type.ValueWrapper;

public class FuncOp extends Node {

    private final String name;
    private final Node[] arguments;

    public FuncOp(String name, Node[] arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public Object eval(Context context) {
        Function function = context.getFunction(this.name);
        if (function != null) {
            Object[] args = new Object[this.arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                args[i] = this.arguments[i].eval(context);
            }
            return ValueWrapper.wrap(function.call(args));
        }
        throw new RuntimeException("function " + this.name + " not found");
    }
}
