package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.type.QObject;

public class Null extends Node{
    @Override
    public QObject eval(Context context) {
        return null;
    }
}
