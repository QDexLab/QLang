package com.github.qlang.core.function;

import com.github.qlang.core.function.math.AbsFunction;

public class FunctionLoader {

    static {
        FunctionContainer.addFunction("map", new MapFunction());
        FunctionContainer.addFunction("abs", new AbsFunction());
    }
}
