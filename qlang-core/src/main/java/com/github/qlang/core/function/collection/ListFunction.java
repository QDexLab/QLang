package com.github.qlang.core.function.collection;

import com.github.qlang.core.function.Function;
import com.github.qlang.core.function.FunctionDefinition;
import com.github.qlang.core.type.QList;
import com.github.qlang.core.type.QObject;

@FunctionDefinition("list")
public class ListFunction implements Function {

    @Override
    public QObject call(QObject[] args) {
        return QList.valueOf(args);
    }
}
