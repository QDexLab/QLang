package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.type.QObject;

public abstract class Node {

    public abstract QObject eval(Context context);
}
