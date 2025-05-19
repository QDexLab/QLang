package com.github.qlang.core.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QList extends QObject {

    private final List<QObject> value = new ArrayList<QObject>();

    public static QList empty() {
        return new QList();
    }

    public static QList valueOf(QObject... values) {
        QList list = new QList();
        for (QObject value : values) {
            list.add(value);
        }
        return list;
    }

    public void add(QObject value) {
        this.value.add(value);
    }

    private QObject get(QNumber index) {
        return this.value.get(index.intValue());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QList qList = (QList) o;
        return Objects.equals(value, qList.value);
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
