package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QObject;

public class Null extends Node{
    @Override
    public QObject eval(Context context) {
        return null;
    }
}
