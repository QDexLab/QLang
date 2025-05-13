package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QNumber;

public class DivOp extends BinaryOp {
    public DivOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber) {
            return ((QNumber) leftValue).divide((QNumber) rightValue);
        }
        throw new EvalException(
                "div only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
