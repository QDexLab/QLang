package com.github.qdexlab.qlang.core.utils;

import com.github.qdexlab.qlang.core.exception.MethodEvalException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ReflectUtils {
    private static final Map<MethodSignature, Method> CACHE = new ConcurrentHashMap<>();

    public static Method findMethod(Class<?> type, String name, Class<?>... args) {
        MethodSignature signature = new MethodSignature(type, name, args);
        Method method = CACHE.get(signature);
        if (method == null) {
            method = nearestMethod(type, name, args);
            if (method == null) {
                throw new MethodEvalException("could not find method " + name);
            }
            CACHE.put(signature, method);
        }
        return method;
    }

    public static List<Method> getMethods(Class<?> type, String name) {
        return Arrays.stream(type.getMethods()).filter(m -> m.getName().equals(name)).collect(Collectors.toList());
    }

    public static int getInheritanceLevels(Class<?> childClass, Class<?> parentClass) {
        if (!parentClass.isAssignableFrom(childClass)) {
            return -1; // 如果不是继承关系返回-1
        }

        int levels = 0;
        Class<?> current = childClass;

        while (current != null && current != parentClass) {
            levels++;
            current = current.getSuperclass();
        }

        return levels;
    }

    public static Method nearestMethod(Class<?> type, String name, Class<?>... args) {
        return getMethods(type, name).stream().filter(m -> m.getParameterCount() == args.length)
                .map(method -> {
                    Object[] result = new Object[2];
                    result[0] = method;
                    int distance = 0;
                    Parameter[] parameters = method.getParameters();
                    for (int i = 0; i < parameters.length; i++) {
                        int levels = getInheritanceLevels(args[i], parameters[i].getType());
                        if (levels == -1) {
                            return null;
                        }
                        distance = distance * 10 + levels;
                    }
                    result[1] = distance;
                    return result;
                }).filter(Objects::nonNull)
                .min(Comparator.comparingInt(o -> (int) o[1]))
                .map(objects -> (Method) objects[0])
                .orElse(null);

    }

    static class MethodSignature {
        private final Class<?> type;
        private final String name;
        private final Class<?>[] args;

        MethodSignature(Class<?> type, String name, Class<?>[] args) {
            this.type = type;
            this.name = name;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            MethodSignature that = (MethodSignature) o;
            return Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects.deepEquals(args, that.args);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, name, Arrays.hashCode(args));
        }
    }
}
