package com.github.qlang.core.ast;

import com.github.qlang.core.exception.ParseException;
import com.github.qlang.core.exception.TokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

        // 按位取反
        assertEquals(~9L, eval("~9"));
        assertEquals(~9L, eval("~(~~9.000)"));
        assertNull(eval("~9.1"));

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

    @Test
    void testShift() {
        // 正数左移
        assertEquals(3L << 2, eval("3 << 2"));     // 3 << 2 = 12
        assertEquals(0x7FL << 8, eval("127 << 8")); // 127 → 32512 (0x7F00)
        // 正数右移
        assertEquals(16L >> 2, eval("16 >> 2"));    // 16 → 4
        assertEquals(255L >> 4, eval("255 >> 4"));  // 255 → 15
        // 负数左移（符号位变化）
        assertEquals(-5L << 1, eval("-5 << 1"));     // -5 << 1 = -10
        assertEquals(-1L << 31, eval("-1 << 31"));   // -1 << 31 = Integer.MIN_VALUE
        // 负数算术右移（保留符号位）
        assertEquals(-8L >> 1, eval("-8 >> 1"));     // -8 → -4
        assertEquals(-128L >> 4, eval("-128 >> 4")); // -128 → -8
        // 零左移/右移
        assertEquals(0L << 5, eval("0 << 5"));      // 0 << 任何数 = 0
        assertEquals(0L >> 3, eval("0 >> 3"));      // 0 >> 任何数 = 0
        // 移零位
        assertEquals(123L << 0, eval("123 << 0"));  // 值不变
        assertEquals(-456L >> 0, eval("-456 >> 0"));// 值不变
        // 类型溢出
        assertEquals(0x80000000L << 1, eval("2147483648 << 1"));  // 溢出为 0（实际为long则结果为4294967296）
        assertEquals(0x7FFFFFFFL << 1, eval("2147483647 << 1"));  // 32位int溢出为-2
        assertEquals(0x80000000L << 60, eval("2147483648 << 60"));
        assertEquals(0x7FFFFFFFL << 60, eval("2147483647 << 60"));
        // 移位数等于位数（Java对移位数取模）
        assertEquals(1L << 32, eval("1 << 32"));    // 1 << (32%32=0) = 1
        assertEquals(-1L >> 32, eval("-1 >> 32"));  // -1 >> 任何数 = -1（全1保留）
        // 正数无符号右移（等价于普通右移）
        assertEquals(64L >>> 3, eval("64 >>> 3"));  // 64 → 8
        // 负数无符号右移（高位补0）
        assertEquals(-1L >>> 1, eval("-1 >>> 1"));  // -1 → 0x7FFFFFFF (int) 或 0x7FFFFFFFFFFFFFFF (long)
        assertEquals(-128L >>> 4, eval("-128 >>> 4")); // 高位补0，结果为正数
        // Long类型大数
        assertEquals(1L << 63, eval("1 << 63"));   // 结果 = Long.MIN_VALUE (-9223372036854775808)
        assertEquals(0xFFFFFFFFL << 16, eval("4294967295 << 16")); // 0xFFFFFFFF0000
        // 链式移位
        assertEquals((5L << 2) >> 1, eval("(5 << 2) >> 1")); // 5 → 20 → 10
        // 混合位移方向
        assertEquals((255L << 24) >>> 16, eval("(255 << 24) >>> 16")); // 0xFF000000 → 0xFF0000 (高位补0)
    }

    @Test
    void testBoolean() {
        assertEquals(true, eval("true"));
        assertEquals(false, eval("false"));
        assertEquals(false, eval("!!!true"));
        assertEquals(true, eval("!!!false"));
        // 异常情况
        assertThrows(TokenException.class, () -> assertEquals(true, eval("true_")));
        assertThrows(TokenException.class, () -> assertEquals(true, eval("true5")));
        assertThrows(TokenException.class, () -> assertEquals(true, eval("trued")));
        assertThrows(TokenException.class, () -> assertEquals(true, eval("trueD")));
        assertThrows(ParseException.class, () -> assertEquals(true, eval("true!")));
    }

    private Object eval(String input) {
        return new Parser(input).parse().eval(context);
    }
}