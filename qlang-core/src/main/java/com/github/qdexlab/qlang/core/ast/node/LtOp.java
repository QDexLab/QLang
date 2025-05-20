package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.exception.EvalException;
import com.github.qdexlab.qlang.core.type.QBool;
import com.github.qdexlab.qlang.core.type.QNumber;
import com.github.qdexlab.qlang.core.type.QObject;

public class LtOp extends BinaryOp {
    public LtOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber) {
            return QBool.valueOf(((QNumber) leftValue).compareTo((QNumber) rightValue) < 0);
        }
        throw new EvalException(
                "less than only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
