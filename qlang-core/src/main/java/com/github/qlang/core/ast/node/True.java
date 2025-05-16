package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QBool;
import com.github.qlang.core.type.QObject;

public class True extends Node {

    public True() {
    }

    @Override
    public QObject eval(Context context) {
        return QBool.TRUE;
    }
}
