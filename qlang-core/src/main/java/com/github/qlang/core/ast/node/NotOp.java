package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QBool;

public class NotOp extends UnaryOp {
    public NotOp(Node node) {
        super(node);
    }

    @Override
    protected Object doEval(Object value, Context context) {
        if (QBool.TRUE.equals(value)) {
            return QBool.FALSE;
        } else if (QBool.FALSE.equals(value)) {
            return QBool.TRUE;
        }
        throw new EvalException("pos only supported boolean, value: " + value);
    }
}
