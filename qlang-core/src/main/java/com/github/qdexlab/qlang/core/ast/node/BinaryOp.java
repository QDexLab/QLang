package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.type.QObject;

public abstract class BinaryOp extends Node {
    protected final Node left;
    protected final Node right;

    public BinaryOp(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public QObject eval(Context context) {
        QObject leftValue = left.eval(context);
        QObject rightValue = right.eval(context);
        if (leftValue != null && rightValue != null) {
            return doEval(context, leftValue, rightValue);
        }
        return null;
    }

    protected abstract QObject doEval(Context context, QObject leftValue, QObject rightValue);
}
