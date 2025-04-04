package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public abstract class UnaryOp extends Node {

    private final Node node;

    public UnaryOp(Node node) {
        this.node = node;
    }

    @Override
    public Object eval(Context context) {
        Object nodeValue = node.eval(context);
        if (nodeValue != null) {
            return doEval(nodeValue, context);
        }
        return null;
    }

    protected abstract Object doEval(Object value, Context context);
}
