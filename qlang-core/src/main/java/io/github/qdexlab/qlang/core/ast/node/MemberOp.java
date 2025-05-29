package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.exception.EvalException;
import io.github.qdexlab.qlang.core.type.QMap;
import io.github.qdexlab.qlang.core.type.QObject;
import io.github.qdexlab.qlang.core.type.QString;

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
