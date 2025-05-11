package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.utils.NumberUtils;

public class URShiftOp extends BinaryOp {
    public URShiftOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        if (leftValue instanceof Number && rightValue instanceof Number) {
            return NumberUtils.unsignedRightShift((Number) leftValue, (Number) rightValue);
        }
        throw new EvalException(
                "unsigned right shift only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
