package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

import java.util.Objects;

public class EqOp extends BinaryOp {
    public EqOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    public Object eval(Context context) {
        Object leftValue = left.eval(context);
        Object rightValue = right.eval(context);
        return Objects.equals(leftValue, rightValue);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        return Objects.equals(leftValue, rightValue);
    }
}
