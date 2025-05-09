package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.utils.NumberUtils;

public class BitReverseOp extends UnaryOp {
    public BitReverseOp(Node node) {
        super(node);
    }

    @Override
    protected Object doEval(Object value, Context context) {
        if (value instanceof Number) {
            return NumberUtils.bitReverse((Number) value);
        }
        throw new EvalException("bit reverse only supported number, value: " + value);
    }
}
