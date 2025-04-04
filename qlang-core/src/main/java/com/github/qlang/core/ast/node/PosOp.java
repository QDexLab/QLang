package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;

public class PosOp extends UnaryOp {
    public PosOp(Node node) {
        super(node);
    }

    @Override
    protected Object doEval(Object value, Context context) {
        if (value instanceof Number) {
            return value;
        }
        throw new EvalException("pos only supported number, value: " + value);
    }
}
