package io.github.qdexlab.qlang.core.function;

import io.github.qdexlab.qlang.core.exception.InitializeException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class FunctionContainer {

    private static final Map<String, Function> functions = new LinkedHashMap<>();

    public static void loadFunctions() {
        ServiceLoader<FunctionLoader> loader = ServiceLoader.load(FunctionLoader.class);
        for (FunctionLoader functionLoader : loader) {
            List<Function> functions = functionLoader.loadFunctions();
            if (functions != null) {
                functions.forEach(FunctionContainer::addFunction);
            }
        }
    }

    public static Function getFunction(String name) {
        return functions.get(name);
    }

    public static void addFunction(Function function) {
        FunctionDefinition definition = function.getClass().getAnnotation(FunctionDefinition.class);
        if (definition == null) {
            throw new InitializeException("FunctionDefinition annotation not found: " + function.getClass().getName());
        }
        if (functions.containsKey(definition.value())) {
            throw new InitializeException("Duplicate function name: " + definition.value());
        }
        functions.put(definition.value(), function);
    }
}
