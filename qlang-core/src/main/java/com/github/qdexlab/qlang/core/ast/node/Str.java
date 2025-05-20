package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.type.QObject;
import com.github.qdexlab.qlang.core.type.QString;

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
