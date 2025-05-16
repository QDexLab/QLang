package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QBool;

public class AndOp extends BinaryOp {
    public AndOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        if (leftValue instanceof QBool && rightValue instanceof QBool) {
            return ((QBool) leftValue).and((QBool) rightValue);
        }
        throw new EvalException(
                "and only supported bool, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
