package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QNumber;

public class PlusOp extends BinaryOp {
    public PlusOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber) {
            return ((QNumber) leftValue).add((QNumber) rightValue);
        }
        throw new EvalException(
                "plus only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
