package com.github.qdexlab.qlang.core.type;

import java.util.LinkedHashSet;
import java.util.Objects;

public class QSet extends QObject {
    private final LinkedHashSet<QObject> value = new LinkedHashSet<>();

    public static QSet empty() {
        return new QSet();
    }

    public static QSet valueOf(QObject... values) {
        QSet set = new QSet();
        for (QObject value : values) {
            set.add(value);
        }
        return set;
    }

    public void add(QObject value) {
        this.value.add(value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QSet set = (QSet) o;
        return Objects.equals(value, set.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
