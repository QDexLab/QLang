package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.exception.EvalException;
import com.github.qdexlab.qlang.core.type.QNumber;
import com.github.qdexlab.qlang.core.type.QObject;

public class DivOp extends BinaryOp {
    public DivOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
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
