package com.github.qdexlab.qlang.core.function.collection;

import com.github.qdexlab.qlang.core.exception.FunctionEvalException;
import com.github.qdexlab.qlang.core.function.Function;
import com.github.qdexlab.qlang.core.function.FunctionDefinition;
import com.github.qdexlab.qlang.core.type.QMap;
import com.github.qdexlab.qlang.core.type.QObject;

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
