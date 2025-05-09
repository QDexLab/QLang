package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;

public class NotOp extends UnaryOp {
    public NotOp(Node node) {
        super(node);
    }

    @Override
    protected Object doEval(Object value, Context context) {
        if (Boolean.TRUE.equals(value)) {
            return Boolean.FALSE;
        } else if (Boolean.FALSE.equals(value)) {
            return Boolean.TRUE;
        }
        throw new EvalException("pos only supported boolean, value: " + value);
    }
}
