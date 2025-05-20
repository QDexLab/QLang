package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QList;
import com.github.qlang.core.type.QNumber;
import com.github.qlang.core.type.QObject;

public class IndexOp extends BinaryOp {
    public IndexOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        if (leftValue instanceof QList && rightValue instanceof QNumber) {
            return ((QList) leftValue).get((QNumber) rightValue);
        }
        throw new EvalException("IndexOp expects a list and number but got " + leftValue + " and " + rightValue);
    }
}
