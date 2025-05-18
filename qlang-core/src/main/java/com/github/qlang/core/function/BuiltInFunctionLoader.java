package com.github.qlang.core.function;

import com.github.qlang.core.function.collection.MapFunction;
import com.github.qlang.core.function.math.AbsFunction;

import java.util.Arrays;
import java.util.List;

public class BuiltInFunctionLoader implements FunctionLoader {

    @Override
    public List<Function> loadFunctions() {
        return Arrays.asList(
                new AbsFunction(),
                new MapFunction()
        );
    }
}
