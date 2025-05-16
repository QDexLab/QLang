package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QBool;
import com.github.qlang.core.type.QObject;

import java.util.Objects;

public class EqOp extends BinaryOp {
    public EqOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    public QObject eval(Context context) {
        QObject leftValue = left.eval(context);
        QObject rightValue = right.eval(context);
        return QBool.valueOf(Objects.equals(leftValue, rightValue));
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        return QBool.valueOf(Objects.equals(leftValue, rightValue));
    }
}
