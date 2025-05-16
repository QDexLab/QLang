package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public class Null extends Node{
    @Override
    public Object eval(Context context) {
        return null;
    }
}
