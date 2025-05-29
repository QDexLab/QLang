package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.exception.EvalException;
import io.github.qdexlab.qlang.core.type.QList;
import io.github.qdexlab.qlang.core.type.QNumber;
import io.github.qdexlab.qlang.core.type.QObject;

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
