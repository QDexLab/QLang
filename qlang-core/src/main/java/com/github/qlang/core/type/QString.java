package com.github.qlang.core.type;

import java.util.Objects;

public class QString extends QObject {

    private final String value;

    private QString(String value) {
        this.value = value;
    }

    public static QString valueOf(String value) {
        return new QString(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QString qString = (QString) o;
        return Objects.equals(value, qString.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
