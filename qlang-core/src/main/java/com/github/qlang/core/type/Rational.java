package com.github.qlang.core.type;

/**
 * 有理数类，用于表示和处理有理数
 */
public class Rational {
    private final long numerator;   // 分子
    private final long denominator; // 分母

    /**
     * 构造一个有理数
     *
     * @param numerator   分子
     * @param denominator 分母
     * @throws IllegalArgumentException 当分母为0时抛出
     */
    public Rational(long numerator, long denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("分母不能为0");
        }

        // 处理负号
        if (denominator < 0) {
            numerator = -numerator;
            denominator = -denominator;
        }

        // 约分
        long gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    /**
     * 计算最大公约数
     */
    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * 加法运算
     */
    public Rational add(Rational other) {
        long newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        long newDenominator = this.denominator * other.denominator;
        return new Rational(newNumerator, newDenominator);
    }

    /**
     * 减法运算
     */
    public Rational subtract(Rational other) {
        long newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        long newDenominator = this.denominator * other.denominator;
        return new Rational(newNumerator, newDenominator);
    }

    /**
     * 乘法运算
     */
    public Rational multiply(Rational other) {
        long newNumerator = this.numerator * other.numerator;
        long newDenominator = this.denominator * other.denominator;
        return new Rational(newNumerator, newDenominator);
    }

    /**
     * 除法运算
     */
    public Rational divide(Rational other) {
        if (other.numerator == 0) {
            throw new ArithmeticException("除数不能为0");
        }
        long newNumerator = this.numerator * other.denominator;
        long newDenominator = this.denominator * other.numerator;
        return new Rational(newNumerator, newDenominator);
    }

    /**
     * 获取分子
     */
    public long getNumerator() {
        return numerator;
    }

    /**
     * 获取分母
     */
    public long getDenominator() {
        return denominator;
    }

    /**
     * 转换为浮点数
     */
    public double toDouble() {
        return (double) numerator / denominator;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Rational)) return false;
        Rational other = (Rational) obj;
        return this.numerator == other.numerator && this.denominator == other.denominator;
    }

    @Override
    public int hashCode() {
        return 31 * Long.hashCode(numerator) + Long.hashCode(denominator);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}