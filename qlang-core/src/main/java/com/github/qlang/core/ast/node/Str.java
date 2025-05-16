package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public class Str extends Node{

    private final String value;

    public Str(String value) {
        this.value = value;
    }

    @Override
    public Object eval(Context context) {
        return value;
    }
}
