package com.github.qlang.core.function;

import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionContainer {

    private static final Map<String, Function> functions = new LinkedHashMap<>();

    public static Function getFunction(String name) {
        return functions.get(name);
    }

    public static void addFunction(String name, Function function) {
        functions.put(name, function);
    }
}
