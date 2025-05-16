package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QBool;

public class True extends Node {

    public True() {
    }

    @Override
    public Object eval(Context context) {
        return QBool.TRUE;
    }
}
