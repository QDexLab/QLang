package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.exception.EvalException;
import io.github.qdexlab.qlang.core.type.QNumber;
import io.github.qdexlab.qlang.core.type.QObject;

public class NegOp extends UnaryOp {
    public NegOp(Node node) {
        super(node);
    }

    @Override
    protected QObject doEval(QObject value, Context context) {
        if (value instanceof QNumber) {
            return ((QNumber) value).negate();
        }
        throw new EvalException("neg only supported number, value: " + value);
    }
}
