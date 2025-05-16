package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QBool;

public class False extends Node {

    public False() {
    }

    @Override
    public Object eval(Context context) {
        return QBool.FALSE;
    }
}
