package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.utils.NumberUtils;

public class LShiftOp extends BinaryOp {
    public LShiftOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        if (leftValue instanceof Number && rightValue instanceof Number) {
            return NumberUtils.leftShift((Number) leftValue, (Number) rightValue);
        }
        throw new EvalException(
                "left shift only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
