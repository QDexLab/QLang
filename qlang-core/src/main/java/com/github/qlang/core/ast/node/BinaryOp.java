package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public abstract class BinaryOp extends Node {
    protected final Node left;
    protected final Node right;

    public BinaryOp(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Object eval(Context context) {
        Object leftValue = left.eval(context);
        Object rightValue = right.eval(context);
        if (leftValue != null && rightValue != null) {
            return doEval(context, leftValue, rightValue);
        }
        return null;
    }

    protected abstract Object doEval(Context context, Object leftValue, Object rightValue);
}
