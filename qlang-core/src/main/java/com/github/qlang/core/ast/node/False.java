package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public class False extends Node {

    public False() {
    }

    @Override
    public Object eval(Context context) {
        return Boolean.FALSE;
    }
}
