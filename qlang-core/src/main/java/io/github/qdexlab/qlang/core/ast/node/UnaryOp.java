package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.type.QObject;

public abstract class UnaryOp extends Node {

    private final Node node;

    public UnaryOp(Node node) {
        this.node = node;
    }

    @Override
    public QObject eval(Context context) {
        QObject nodeValue = node.eval(context);
        if (nodeValue != null) {
            return doEval(nodeValue, context);
        }
        return null;
    }

    protected abstract QObject doEval(QObject value, Context context);
}
