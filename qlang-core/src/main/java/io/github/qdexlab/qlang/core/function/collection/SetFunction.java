package io.github.qdexlab.qlang.core.function.collection;

import io.github.qdexlab.qlang.core.function.Function;
import io.github.qdexlab.qlang.core.function.FunctionDefinition;
import io.github.qdexlab.qlang.core.type.QObject;
import io.github.qdexlab.qlang.core.type.QSet;

@FunctionDefinition("set")
public class SetFunction implements Function {

    @Override
    public QObject call(QObject[] args) {
        return QSet.valueOf(args);
    }
}
