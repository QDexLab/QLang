package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.exception.EvalException;
import com.github.qdexlab.qlang.core.type.QNumber;
import com.github.qdexlab.qlang.core.type.QObject;

public class MinusOp extends BinaryOp {
    public MinusOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber) {
            return ((QNumber) leftValue).subtract((QNumber) rightValue);
        }
        throw new EvalException(
                "minus only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
