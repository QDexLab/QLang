package com.github.qdexlab.qlang.core.ast.node;

import com.github.qdexlab.qlang.core.ast.Context;
import com.github.qdexlab.qlang.core.type.QBool;
import com.github.qdexlab.qlang.core.type.QObject;

public class True extends Node {

    public True() {
    }

    @Override
    public QObject eval(Context context) {
        return QBool.TRUE;
    }
}
