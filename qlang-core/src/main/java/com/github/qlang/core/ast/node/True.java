package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public class True extends Node {

    public True() {
    }

    @Override
    public Object eval(Context context) {
        return Boolean.TRUE;
    }
}
