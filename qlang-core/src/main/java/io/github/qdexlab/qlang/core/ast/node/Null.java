package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.type.QObject;

public class Null extends Node{
    @Override
    public QObject eval(Context context) {
        return null;
    }
}
