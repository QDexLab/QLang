package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QBool;
import com.github.qlang.core.type.QNumber;
import com.github.qlang.core.type.QObject;

public class LteOp extends BinaryOp {
    public LteOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        if (leftValue instanceof QNumber && rightValue instanceof QNumber) {
            return QBool.valueOf(((QNumber) leftValue).compareTo((QNumber) rightValue) <= 0);
        }
        throw new EvalException(
                "less than equal only supported number, "
                        + "leftValue: " + leftValue
                        + ", rightValue: " + rightValue
        );
    }
}
