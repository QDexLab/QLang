package io.github.qdexlab.qlang.core.ast.node;

import io.github.qdexlab.qlang.core.ast.Context;
import io.github.qdexlab.qlang.core.type.QObject;
import io.github.qdexlab.qlang.core.type.QString;

public class Str extends Node{

    private final QString value;

    public Str(String value) {
        this.value = QString.valueOf(value);
    }

    @Override
    public QObject eval(Context context) {
        return value;
    }
}
