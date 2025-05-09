package com.github.qlang.core.ast;

import com.github.qlang.core.exception.ParseException;
import com.github.qlang.core.exception.TokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context() {};
    }

    @Test
    void baseArithmetic() {
        assertThrows(ParseException.class, () -> eval("1 - 2 + 3 3"));
        assertThrows(ParseException.class, () -> eval("1 - 2 + 3 /  "));
        // 基础运算
        assertEquals(2L, eval("1 - 2 + 3"));
        assertEquals(-3L, eval("4 / 2 - 5"));
        assertEquals(16L, eval("(3 + 5) * 2"));
        assertEquals(0.125, eval("0.5 * 0.25"));
        assertEquals(15L, eval("10 - -5"));
        assertEquals(5L, eval("10 - + - -5"));
        assertEquals(9L, eval("2^3 + 4 % 3"));
        // 复杂表达式
        assertEquals(16.0, eval("( (3 + 5 * 2) / (4 - 1) ) ^ 2"));
        assertEquals((1.5 + 2.5) * (3.2 - 4.8) / (-0.5), eval("(1.5 + 2.5) * (3.2 - 4.8) / (-0.5)"));
        assertEquals(13L, eval("2 * (3 + 4) / (5 % 3) + 6"));
        assertEquals(6L, eval("( (10 - 5) * 2^3 ) / (6 + 4 % 2)"));
        assertEquals(2L, eval("1--1"));
        assertEquals(-2L, eval("---1-++--1"));

        //边界条件与特殊值
        assertEquals(0L, eval("0 / 5"));
        assertThrows(ArithmeticException.class, () -> eval("5 / 0"));
        assertEquals(999999999 * 999999999L, eval("999999999 * 999999999"));
        assertEquals(0L, eval("-0.0 + 0.0"));
        assertEquals(3.14159265358979323846 / 2, eval("3.14159265358979323846 / 2"));

        // 空格与格式变化
        assertEquals(3L+4*2/(1-5), eval("3+4*2/(1-5)"));
        assertEquals(3L + 4 * 2 / ( 1 -5 ), eval("3 + 4 * 2 / ( 1 -5 )"));
        assertEquals(5L   -  3  *  2, eval(" 5   -  3  *  2"));
        assertEquals(( ( 3L + 2 ) * 5 ), eval("( ( 3 + 2 ) * 5 )"));

        // 非法表达式（需捕获错误）
        assertThrows(ParseException.class, () ->eval("3 + * 4"));
        assertThrows(ParseException.class, () ->eval("(5 + 3 "));
        assertThrows(ParseException.class, () ->eval("2.3.4 + 5"));
        assertThrows(TokenException.class, () ->eval("abc + 5"));
        assertThrows(ArithmeticException.class, () -> eval("5 + 4 % 0 "));
        /**
         * 边界条件与特殊值
         * 1.234e5 * 2.0        # 科学计数法
         */
    }

    private Object eval(String input) {
        return new Parser(input).parse().eval(context);
    }
}