package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.EvalException;
import com.github.qlang.core.type.QMap;
import com.github.qlang.core.type.QObject;
import com.github.qlang.core.type.QString;

import java.util.Arrays;

public class MemberOp extends BinaryOp {
    public MemberOp(Node left, Node right) {
        super(left, right);
    }

    @Override
    public QObject eval(Context context) {
        QObject leftValue = left.eval(context);
        if (right instanceof FuncOp) {
            Node[] arguments = ((FuncOp) right).getArguments();
            QObject[] args = Arrays.stream(arguments).map(node -> node.eval(context)).toArray(QObject[]::new);
            return leftValue.call(((FuncOp) right).getName(), args);
        } else if (leftValue instanceof QMap && right instanceof Identifier) {
            return ((QMap) leftValue).get(QString.valueOf(((Identifier) right).getName()));
        }
        throw new EvalException("not a member op");
    }

    @Override
    protected QObject doEval(Context context, QObject leftValue, QObject rightValue) {
        throw new IllegalArgumentException("not supported");
    }
}
