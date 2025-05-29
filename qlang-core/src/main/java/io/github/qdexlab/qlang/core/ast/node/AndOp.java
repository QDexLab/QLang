package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.exception.EvalException;
import io.github.qdexlab.qlang.core.type.QBool;
import io.github.qdexlab.qlang.core.type.QObject;

public class AndOp extends BinaryOp {
    public AndOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
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
