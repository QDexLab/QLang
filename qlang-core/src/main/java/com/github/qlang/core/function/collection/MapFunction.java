package com.github.qlang.core.function.collection;

import com.github.qlang.core.exception.FunctionEvalException;
import com.github.qlang.core.function.Function;
import com.github.qlang.core.function.FunctionDefinition;
import com.github.qlang.core.type.QObject;

import java.util.LinkedHashMap;
import java.util.Map;

@FunctionDefinition("map")
public class MapFunction implements Function {

    @Override
    public QObject call(QObject[] args) {
        if (args.length % 2 == 1) {
            throw new FunctionEvalException("MapFunction requires even number of arguments, but got " + args.length);
        }
        Map<Object, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i = i + 2) {
            QObject key = args[i];
            QObject value = args[i + 1];
            map.put(key, value);
        }
        // todo
        return null;
    }
}
