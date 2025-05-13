package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QNumber;

public class NegOp extends UnaryOp {
    public NegOp(Node node) {
        super(node);
    }

    @Override
    protected Object doEval(Object value, Context context) {
        if (value instanceof QNumber) {
            return ((QNumber) value).negate();
        }
        throw new EvalException("neg only supported number, value: " + value);
    }
}
