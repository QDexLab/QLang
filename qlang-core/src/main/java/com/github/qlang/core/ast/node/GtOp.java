package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QBool;
import com.github.qlang.core.type.QNumber;

public class GtOp extends BinaryOp {
    public GtOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected Object doEval(Context context, Object leftValue, Object rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber) {
            return QBool.valueOf(((QNumber) leftValue).compareTo((QNumber) rightValue) > 0);
        }
        throw new EvalException(
                "greater than only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
