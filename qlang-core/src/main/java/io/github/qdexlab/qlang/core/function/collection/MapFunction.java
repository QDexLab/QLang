package io.github.qdexlab.qlang.core.function.collection;

import io.github.qdexlab.qlang.core.exception.FunctionEvalException;
import io.github.qdexlab.qlang.core.function.Function;
import io.github.qdexlab.qlang.core.function.FunctionDefinition;
import io.github.qdexlab.qlang.core.type.QMap;
import io.github.qdexlab.qlang.core.type.QObject;

@FunctionDefinition("map")
public class MapFunction implements Function {

    @Override
    public QObject call(QObject[] args) {
        if (args.length % 2 == 1) {
            throw new FunctionEvalException("MapFunction requires even number of arguments, but got " + args.length);
        }
        return QMap.valueOf(args);
    }
}
