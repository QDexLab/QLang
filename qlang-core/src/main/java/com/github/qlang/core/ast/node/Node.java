package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;

public abstract class Node {

    public abstract Object eval(Context context);
}
