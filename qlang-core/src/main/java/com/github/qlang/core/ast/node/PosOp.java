package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QNumber;
import com.github.qlang.core.type.QObject;

public class PosOp extends UnaryOp {
    public PosOp(Node node) {
        super(node);
    }

    @Override
    protected QObject doEval(QObject value, Context context) {
        if (value instanceof QNumber) {
            return value;
        }
        throw new EvalException("pos only supported number, value: " + value);
    }
}
