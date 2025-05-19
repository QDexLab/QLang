package com.github.qlang.core.function.collection;

import com.github.qlang.core.function.Function;
import com.github.qlang.core.function.FunctionDefinition;
import com.github.qlang.core.type.QObject;
import com.github.qlang.core.type.QSet;

@FunctionDefinition("set")
public class SetFunction implements Function {

    @Override
    public QObject call(QObject[] args) {
        return QSet.valueOf(args);
    }
}
