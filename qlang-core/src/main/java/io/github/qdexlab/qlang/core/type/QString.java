package io.github.qdexlab.qlang.core.type;

import java.util.Objects;

public final class QString extends QObject {

    private final String value;

    private QString(String value) {
        this.value = value;
    }

    public static QString valueOf(CharSequence value) {
        return new QString(value.toString());
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
