package com.github.qlang.core.type;

/**
 * 有理数类，用于表示和处理有理数
 */
public class Rational {

    /**
     * 创建值为0的有理数
     */
    public static Rational ZERO = new Rational(0, 1);

    /**
     * 创建值为1的有理数
     */
    public static Rational ONE = new Rational(1, 1);

    /**
     * 创建值为10的有理数
     */
    public static Rational TEN = new Rational(10, 1);

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
     * 从整数创建有理数
     */
    public static Rational of(long value) {
        return new Rational(value, 1);
    }

    /**
     * 从字符串创建有理数，字符串格式为"numerator/denominator"
     */
    public static Rational parse(String str) {
        try {
            // 检查是否为分数格式
            if (str.contains("/")) {
                String[] parts = str.split("/");
                if (parts.length != 2) {
                    throw new IllegalArgumentException(str + "无效的分数格式，应为'分子/分母'");
                }
                long numerator = Long.parseLong(parts[0]);
                long denominator = Long.parseLong(parts[1]);
                return new Rational(numerator, denominator);
            }

            // 处理小数格式
            double value = Double.parseDouble(str);
            if (value == (long) value) {
                return new Rational((long) value, 1);
            }

            // 将小数转换为分数
            // 例如：1.5 -> 15/10 -> 3/2
            String decimal = str;
            int decimalPlaces = decimal.length() - decimal.indexOf('.') - 1;
            long denominator = (long) Math.pow(10, decimalPlaces);
            long numerator = (long) (value * denominator);
            return new Rational(numerator, denominator);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(str + "无效的数字格式");
        }
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