package com.github.qdexlab.qlang.core.function;

import com.github.qdexlab.qlang.core.function.collection.ListFunction;
import com.github.qdexlab.qlang.core.function.collection.MapFunction;
import com.github.qdexlab.qlang.core.function.collection.SetFunction;
import com.github.qdexlab.qlang.core.function.math.AbsFunction;

import java.util.Arrays;
import java.util.List;

public class BuiltInFunctionLoader implements FunctionLoader {

    @Override
    public List<Function> loadFunctions() {
        return Arrays.asList(
                new AbsFunction(),
                new ListFunction(),
                new SetFunction(),
                new MapFunction()
        );
    }
}
