package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;

public class XorOp extends BinaryOp {
    public XorOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        if (leftValue instanceof Boolean && rightValue instanceof Boolean) {
            return (Boolean) leftValue ^ (Boolean) rightValue;
        }
        throw new EvalException(
                "xor only supported bool, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
