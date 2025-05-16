package com.github.qlang.core.function;

import com.github.qlang.core.type.ValueWrapper;
import com.github.qlang.core.exception.FunctionEvalException;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapFunction implements Function {

    @Override
    public Object call(Object[] args) {
        if (args.length % 2 == 1) {
            throw new FunctionEvalException("MapFunction requires even number of arguments, but got " + args.length);
        }
        Map<Object, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < args.length; i = i + 2) {
            Object key = args[i];
            Object value = args[i + 1];
            map.put(ValueWrapper.wrap(key), ValueWrapper.wrap(value));
        }
        return map;
    }
}
