package com.github.qlang.core.type;

import java.util.Objects;

public class QBool extends QObject {

    public static final QBool TRUE = new QBool(true);
    public static final QBool FALSE = new QBool(false);

    private final boolean value;

    private QBool(boolean value) {
        this.value = value;
    }

    public static QBool valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }

    public QBool and(QBool other) {
        return new QBool(this.value && other.value);
    }

    public QBool or(QBool other) {
        return new QBool(this.value || other.value);
    }

    public QBool not() {
        return new QBool(!value);
    }

    public QBool xor(QBool other) {
        return new QBool(this.value ^ other.value);
    }

    public boolean isTrue() {
        return value;
    }

    public boolean isFalse() {
        return !value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QBool qBool = (QBool) o;
        return value == qBool.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
