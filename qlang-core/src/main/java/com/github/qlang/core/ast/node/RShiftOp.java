package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QNumber;

public class RShiftOp extends BinaryOp {
    public RShiftOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber && ((QNumber) rightValue).isInt()) {
            return ((QNumber) leftValue).rightShift(((QNumber) rightValue).intValue());
        }
        throw new EvalException(
                "shift operator must be number and integer number: "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
