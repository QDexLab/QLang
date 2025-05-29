package io.github.qdexlab.qlang.core.function.collection;

import io.github.qdexlab.qlang.core.function.Function;
import io.github.qdexlab.qlang.core.function.FunctionDefinition;
import io.github.qdexlab.qlang.core.type.QList;
import io.github.qdexlab.qlang.core.type.QObject;

@FunctionDefinition("list")
public class ListFunction implements Function {

    @Override
    public QObject call(QObject[] args) {
        return QList.valueOf(args);
    }
}
