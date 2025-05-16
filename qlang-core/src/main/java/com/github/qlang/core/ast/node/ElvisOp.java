package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public class ElvisOp extends BinaryOp{

    public ElvisOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    public Object eval(Context context) {
        Object leftValue = left.eval(context);
        return leftValue != null ? leftValue : right.eval(context);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        throw new UnsupportedOperationException("unsupported operation");
    }
}
