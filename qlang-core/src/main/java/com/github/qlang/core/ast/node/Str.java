package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QString;

public class Str extends Node{

    private final QString value;

    public Str(String value) {
        this.value = QString.valueOf(value);
    }

    @Override
    public Object eval(Context context) {
        return value;
    }
}
