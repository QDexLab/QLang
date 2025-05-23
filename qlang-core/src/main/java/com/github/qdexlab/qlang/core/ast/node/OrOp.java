package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.exception.EvalException;
import com.github.qdexlab.qlang.core.type.QBool;
import com.github.qdexlab.qlang.core.type.QObject;

public class OrOp extends BinaryOp {
    public OrOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        if (leftValue instanceof QBool && rightValue instanceof QBool) {
            return ((QBool) leftValue).or((QBool) rightValue);
        }
        throw new EvalException(
                "or only supported bool, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
