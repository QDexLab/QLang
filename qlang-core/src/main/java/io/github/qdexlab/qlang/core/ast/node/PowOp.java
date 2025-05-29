package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.exception.EvalException;
import io.github.qdexlab.qlang.core.type.QNumber;
import io.github.qdexlab.qlang.core.type.QObject;

public class PowOp extends BinaryOp {
    public PowOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber) {
            return ((QNumber) leftValue).power((QNumber) rightValue);
        }
        throw new EvalException(
                "pow only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
