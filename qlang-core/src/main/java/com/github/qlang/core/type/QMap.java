package com.github.qlang.core.type;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class QMap extends QObject {
    private final Map<QObject, QObject> values = new LinkedHashMap<>();

    public static QMap valueOf(QObject... args) {
        if (args.length % 2 == 1) {
            throw new IllegalArgumentException("args count must be even");
        }
        QMap map = new QMap();
        for (int i = 0; i < args.length; i += 2) {
            map.put(args[i], args[i + 1]);
        }
        return map;
    }

    public QObject get(QObject key) {
        return values.get(key);
    }

    public void put(QObject key, QObject value) {
        values.put(key, value);
    }

    public void putAll(QMap other) {
        if (other == null) {
            return;
        }
        values.putAll(other.values);
    }

    public QNumber size() {
        return QNumber.valueOf(String.valueOf(values.size()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QMap map = (QMap) o;
        return Objects.equals(values, map.values);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
