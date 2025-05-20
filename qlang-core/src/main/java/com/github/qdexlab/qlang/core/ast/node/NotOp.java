package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.exception.EvalException;
import com.github.qdexlab.qlang.core.type.QBool;
import com.github.qdexlab.qlang.core.type.QObject;

public class NotOp extends UnaryOp {
    public NotOp(Node node) {
        super(node);
    }

    @Override
    protected QObject doEval(QObject value, Context context) {
        if (QBool.TRUE.equals(value)) {
            return QBool.FALSE;
        } else if (QBool.FALSE.equals(value)) {
            return QBool.TRUE;
        }
        throw new EvalException("pos only supported boolean, value: " + value);
    }
}
