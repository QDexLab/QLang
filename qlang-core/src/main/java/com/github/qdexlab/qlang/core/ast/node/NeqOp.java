package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.type.QBool;
import com.github.qdexlab.qlang.core.type.QObject;

import java.util.Objects;

public class NeqOp extends BinaryOp {
    public NeqOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    public QObject eval(Context context) {
        QObject leftValue = left.eval(context);
        QObject rightValue = right.eval(context);
        return QBool.valueOf(!Objects.equals(leftValue, rightValue));
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        return QBool.valueOf(!Objects.equals(leftValue, rightValue));
    }
}
