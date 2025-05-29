package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.exception.EvalException;
import io.github.qdexlab.qlang.core.type.QNumber;
import io.github.qdexlab.qlang.core.type.QObject;

public class BitAndOp extends BinaryOp {
    public BitAndOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber) {
            return ((QNumber) leftValue).and((QNumber) rightValue);
        }
        throw new EvalException(
                "bit and only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
