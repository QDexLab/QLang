package com.github.qlang.core.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Objects;

public class QNumber implements Comparable<QNumber> {
    public static final QNumber ZERO = QNumber.valueOf("0");
    public static final QNumber ONE = QNumber.valueOf("1");
    public static final QNumber FIVE = QNumber.valueOf("5");
    public static final QNumber TEN = QNumber.valueOf("10");

    private final BigDecimal value;

    private QNumber(String value) {
        this(new BigDecimal(value));
    }

    private QNumber(BigInteger value) {
        this(new BigDecimal(value));
    }

    private QNumber(BigDecimal value) {
        this.value = value.stripTrailingZeros();
    }

    public static QNumber valueOf(String value) {
        return new QNumber(value);
    }

    public QNumber add(QNumber other) {
        if (other == null) return this;
        return new QNumber(value.add(other.value));
    }

    public QNumber subtract(QNumber other) {
        if (other == null) return this;
        return new QNumber(value.subtract(other.value));
    }

    public QNumber multiply(QNumber other) {
        if (other == null) return this;
        return new QNumber(value.multiply(other.value));
    }

    public QNumber divide(QNumber other) {
        if (other == null) return this;
        return new QNumber(value.divide(other.value, MathContext.DECIMAL128));
    }

    public QNumber remainder(QNumber other) {
        if (other == null) return this;
        return new QNumber(value.remainder(other.value));
    }

    public QNumber leftShift(QNumber shift) {
        return new QNumber(value.toBigIntegerExact().shiftLeft(shift.value.intValueExact()));
    }

    public QNumber rightShift(QNumber shift) {
        return new QNumber(value.toBigIntegerExact().shiftRight(shift.value.intValueExact()));
    }

    public QNumber power(QNumber other) {
        if (other == null) return this;
        return new QNumber(value.pow(other.value.toBigIntegerExact().intValue()));
    }

    public QNumber not() {
        return new QNumber(value.toBigIntegerExact().not());
    }

    public QNumber negate() {
        return new QNumber(value.negate());
    }
    public boolean isInt() {
        return value.scale() <= 0 || value.signum() == 0;
    }

    public int intValue() {
        return value.intValue();
    }

    @Override
    public int compareTo(QNumber other) {
        if (other == null) return 1;
        return this.value.compareTo(other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QNumber number = (QNumber) o;
        return Objects.equals(value, number.value);
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
