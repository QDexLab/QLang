package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.utils.NumberUtils;

public class NegOp extends UnaryOp {
    public NegOp(Node node) {
        super(node);
    }

    @Override
    protected Object doEval(Object value, Context context) {
        if (value instanceof Number) {
            Number number = (Number) value;
            if (NumberUtils.isInteger(number)){
                return -(number).longValue();
            } else {
                return -(number).doubleValue();
            }
        }
        throw new EvalException("neg only supported number, value: " + value);
    }
}
