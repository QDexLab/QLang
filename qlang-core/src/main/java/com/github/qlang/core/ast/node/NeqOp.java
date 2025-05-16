package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QBool;

import java.util.Objects;

public class NeqOp extends BinaryOp {
    public NeqOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    public Object eval(Context context) {
        Object leftValue = left.eval(context);
        Object rightValue = right.eval(context);
        return QBool.valueOf(!Objects.equals(leftValue, rightValue));
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        return !Objects.equals(leftValue, rightValue);
    }
}
