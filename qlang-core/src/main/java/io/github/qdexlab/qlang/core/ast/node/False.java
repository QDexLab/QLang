package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.type.QBool;
import io.github.qdexlab.qlang.core.type.QObject;

public class False extends Node {

    public False() {
    }

    @Override
    public QObject eval(Context context) {
        return QBool.FALSE;
    }
}
