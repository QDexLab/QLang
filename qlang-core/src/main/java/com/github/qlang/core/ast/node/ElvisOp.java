package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QObject;

public class ElvisOp extends BinaryOp{

    public ElvisOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    public QObject eval(Context context) {
        QObject leftValue = left.eval(context);
        return leftValue != null ? leftValue : right.eval(context);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        throw new UnsupportedOperationException("unsupported operation");
    }
}
