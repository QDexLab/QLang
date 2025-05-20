package com.github.qdexlab.qlang.core.function.collection;

import com.github.qdexlab.qlang.core.function.Function;
import com.github.qdexlab.qlang.core.function.FunctionDefinition;
import com.github.qdexlab.qlang.core.type.QList;
import com.github.qdexlab.qlang.core.type.QObject;

@FunctionDefinition("list")
public class ListFunction implements Function {

    @Override
    public QObject call(QObject[] args) {
        return QList.valueOf(args);
    }
}
