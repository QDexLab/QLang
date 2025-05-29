package io.github.qdexlab.qlang.core.function;

import io.github.qdexlab.qlang.core.function.collection.ListFunction;
import io.github.qdexlab.qlang.core.function.collection.MapFunction;
import io.github.qdexlab.qlang.core.function.collection.SetFunction;
import io.github.qdexlab.qlang.core.function.math.AbsFunction;

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
