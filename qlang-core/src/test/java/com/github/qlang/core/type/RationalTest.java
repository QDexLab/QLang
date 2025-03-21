package com.github.qlang.core.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RationalTest {

    @Test
    void testConstructor() {
        // 测试基本构造
        Rational r1 = new Rational(1, 2);
        assertEquals(1, r1.getNumerator());
        assertEquals(2, r1.getDenominator());

        // 测试约分
        Rational r2 = new Rational(2, 4);
        assertEquals(1, r2.getNumerator());
        assertEquals(2, r2.getDenominator());

        // 测试负数处理
        Rational r3 = new Rational(1, -2);
        assertEquals(-1, r3.getNumerator());
        assertEquals(2, r3.getDenominator());

        // 测试分母为0的情况
        assertThrows(IllegalArgumentException.class, () -> new Rational(1, 0));
    }

    @Test
    void testAdd() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(1, 3);
        Rational result = r1.add(r2);
        assertEquals(5, result.getNumerator());
        assertEquals(6, result.getDenominator());
    }

    @Test
    void testSubtract() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(1, 3);
        Rational result = r1.subtract(r2);
        assertEquals(1, result.getNumerator());
        assertEquals(6, result.getDenominator());
    }

    @Test
    void testMultiply() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(2, 3);
        Rational result = r1.multiply(r2);
        assertEquals(1, result.getNumerator());
        assertEquals(3, result.getDenominator());
    }

    @Test
    void testDivide() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(2, 3);
        Rational result = r1.divide(r2);
        assertEquals(3, result.getNumerator());
        assertEquals(4, result.getDenominator());

        // 测试除以0的情况
        Rational zero = new Rational(0, 1);
        assertThrows(ArithmeticException.class, () -> r1.divide(zero));
    }

    @Test
    void testToDouble() {
        Rational r = new Rational(1, 2);
        assertEquals(0.5, r.toDouble(), 1e-10);
    }

    @Test
    void testEquals() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(2, 4);
        Rational r3 = new Rational(2, 3);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(r1, null);
        assertNotEquals(r1, "not a rational");
    }

    @Test
    void testToString() {
        Rational r = new Rational(1, 2);
        assertEquals("1/2", r.toString());
    }

    @Test
    void testParse() {
        // 测试分数格式
        assertEquals(new Rational(1, 2), Rational.parse("1/2"));
        assertEquals(new Rational(-3, 4), Rational.parse("-3/4"));

        // 测试小数格式
        assertEquals(new Rational(3, 2), Rational.parse("1.5"));
        assertEquals(new Rational(-25, 10), Rational.parse("-2.5"));

        // 测试整数格式
        assertEquals(new Rational(5, 1), Rational.parse("5"));
        assertEquals(new Rational(-7, 1), Rational.parse("-7"));
        assertEquals(Rational.ZERO, Rational.parse("0"));

        // 测试无效格式
        assertThrows(IllegalArgumentException.class, () -> Rational.parse("abc"));
        assertThrows(IllegalArgumentException.class, () -> Rational.parse("1/0"));
        assertThrows(IllegalArgumentException.class, () -> Rational.parse("1/"));
        assertThrows(IllegalArgumentException.class, () -> Rational.parse("/2"));
        assertThrows(IllegalArgumentException.class, () -> Rational.parse("1/2/3"));
    }
}