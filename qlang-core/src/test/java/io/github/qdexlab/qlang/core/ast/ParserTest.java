package io.github.qdexlab.qlang.core.ast;

import io.github.qdexlab.qlang.core.ast.context.SimpleContext;
import io.github.qdexlab.qlang.core.exception.ParseException;
import io.github.qdexlab.qlang.core.exception.TokenException;
import io.github.qdexlab.qlang.core.type.QBool;
import io.github.qdexlab.qlang.core.type.QList;
import io.github.qdexlab.qlang.core.type.QMap;
import io.github.qdexlab.qlang.core.type.QNumber;
import io.github.qdexlab.qlang.core.type.QSet;
import io.github.qdexlab.qlang.core.type.QString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private Context context;

    @BeforeEach
    void setUp() {
        Context parent = new SimpleContext();
        for (int i = 0; i < 10; i++) {
            parent.setVariable("number_" + i, QNumber.valueOf(String.valueOf(i)));
        }
        parent.setVariable("_a", QNumber.ZERO);
        parent.setVariable("_b", QBool.FALSE);
        parent.setVariable("_c", QString.valueOf("hello"));
        context = new SimpleContext(parent);
        context.setVariable("a", QNumber.ONE);
        context.setVariable("b", QBool.TRUE);
        context.setVariable("c", QString.valueOf("world"));
    }

    @Test
    void identifier() {
        assertNumber(0, eval("_a"));
        assertBool(false, eval("_b"));
        assertString("hello", eval("_c"));
        assertNumber(1, eval("a"));
        assertBool(true, eval("b"));
        assertString("world", eval("c"));
        assertNull(eval("d"));
    }

    @Test
    void baseArithmetic() {
        assertThrows(ParseException.class, () -> eval("1 - 2 + 3 3"));
        assertThrows(ParseException.class, () -> eval("1 - 2 + 3 /  "));
        // 基础运算
        assertNumber(2L, eval("1 - 2 + 3"));
        assertNumber(-3L, eval("4 / 2 - 5"));
        assertNumber(16L, eval("(3 + 5) * 2"));
        assertNumber(0.125, eval("0.5 * 0.25"));
        assertNumber(15L, eval("10 - -5"));
        assertNumber(5L, eval("10 - + - -5"));
        assertNumber(9L, eval("2**3 + 4 % 3"));
        // 复杂表达式
        assertNumber(new BigDecimal("13").divide(new BigDecimal("3"), MathContext.DECIMAL128).pow(2), eval("( (3 + 5 * 2) / (4 - 1) ) ** 2"));
        assertNumber(12.8, eval("(1.5 + 2.5) * (3.2 - 4.8) / (-0.5)"));
        assertNumber(13L, eval("2 * (3 + 4) / (5 % 3) + 6"));
        assertNumber(new BigDecimal("40").divide(new BigDecimal("6"), MathContext.DECIMAL128), eval("( (10 - 5) * 2**3 ) / (6 + 4 % 2)"));
        assertNumber(2L, eval("1--1"));
        assertNumber(-2L, eval("---1-++--1"));

        // 按位取反
        assertNumber(~9L, eval("~9"));
        assertNumber(~9L, eval("~(~~9.000)"));
        assertThrows(ArithmeticException.class, () -> eval("~9.1"));

        //边界条件与特殊值
        assertNumber(0L, eval("0 / 5"));
        assertThrows(ArithmeticException.class, () -> eval("5 / 0"));
        assertNumber(999999999 * 999999999L, eval("999999999 * 999999999"));
        assertNumber(0L, eval("-0.0 + 0.0"));
        assertNumber(new BigDecimal("1.57079632679489661923"), eval("3.14159265358979323846 / 2"));

        // 空格与格式变化
        assertNumber(3L + 4 * 2 / (1 - 5), eval("3+4*2/(1-5)"));
        assertNumber(3L + 4 * 2 / (1 - 5), eval("3 + 4 * 2 / ( 1 -5 )"));
        assertNumber(5L - 3 * 2, eval(" 5   -  3  *  2"));
        assertNumber(((3L + 2) * 5), eval("( ( 3 + 2 ) * 5 )"));

        // 带变量计算
        assertNumber(1, eval("number_3 + number_4 * number_2 / ( number_1 -number_5 )"));

        // 非法表达式（需捕获错误）
        assertThrows(ParseException.class, () -> eval("3 + * 4"));
        assertThrows(ParseException.class, () -> eval("(5 + 3 "));
        assertThrows(ParseException.class, () -> eval("2.3.4 + 5"));
        assertThrows(ArithmeticException.class, () -> eval("5 + 4 % 0 "));
        /**
         * 边界条件与特殊值
         * 1.234e5 * 2.0        # 科学计数法
         */
    }

    @Test
    void testShift() {
        // 正数左移
        assertNumber(3L << 2, eval("3 << 2"));     // 3 << 2 = 12
        assertNumber(0x7FL << 8, eval("127 << 8")); // 127 → 32512 (0x7F00)
        // 正数右移
        assertNumber(16L >> 2, eval("16 >> 2"));    // 16 → 4
        assertNumber(255L >> 4, eval("255 >> 4"));  // 255 → 15
        // 负数左移（符号位变化）
        assertNumber(-5L << 1, eval("-5 << 1"));     // -5 << 1 = -10
        assertNumber(-1L << 31, eval("-1 << 31"));   // -1 << 31 = Integer.MIN_VALUE
        // 负数算术右移（保留符号位）
        assertNumber(-8L >> 1, eval("-8 >> 1"));     // -8 → -4
        assertNumber(-128L >> 4, eval("-128 >> 4")); // -128 → -8
        // 零左移/右移
        assertNumber(0L << 5, eval("0 << 5"));      // 0 << 任何数 = 0
        assertNumber(0L >> 3, eval("0 >> 3"));      // 0 >> 任何数 = 0
        // 移零位
        assertNumber(123L << 0, eval("123 << 0"));  // 值不变
        assertNumber(-456L >> 0, eval("-456 >> 0"));// 值不变
        // 类型溢出
        assertNumber(0x80000000L << 1, eval("2147483648 << 1"));  // 溢出为 0（实际为long则结果为4294967296）
        assertNumber(0x7FFFFFFFL << 1, eval("2147483647 << 1"));  // 32位int溢出为-2
        assertNumber(new BigInteger("2147483648").shiftLeft(60), eval("2147483648 << 60"));
        assertNumber(new BigInteger("2147483647").shiftLeft(60), eval("2147483647 << 60"));
        // 移位数等于位数（Java对移位数取模）
        assertNumber(1L << 32, eval("1 << 32"));    // 1 << (32%32=0) = 1
        assertNumber(-1L >> 32, eval("-1 >> 32"));  // -1 >> 任何数 = -1（全1保留）
        // Long类型大数
        assertNumber(new BigInteger("1").shiftLeft(63), eval("1 << 63"));   // 结果 = Long.MIN_VALUE (-9223372036854775808)
        assertNumber(0xFFFFFFFFL << 16, eval("4294967295 << 16")); // 0xFFFFFFFF0000
        // 链式移位
        assertNumber((5L << 2) >> 1, eval("(5 << 2) >> 1")); // 5 → 20 → 10
        // 混合位移方向
        assertNumber((255L << 24) >> 16, eval("(255 << 24) >> 16")); // 0xFF000000 → 0xFF0000 (高位补0)
    }

    @Test
    public void testAllBitwiseOperations() {
        // 按位与测试
        assertNumber(8, eval("8 & 12"));    // 8 & 12 = 8
        assertNumber(1, eval("5 & 3"));     // 5 & 3 = 1
        // 按位或测试
        assertNumber(14, eval("10 | 12"));  // 10 | 12 = 14
        assertNumber(7, eval("5 | 3"));     // 5 | 3 = 7
        // 按位异或测试
        assertNumber(6, eval("10 ^ 12"));   // 10 ^ 12 = 6
        assertNumber(6, eval("5 ^ 3"));     // 5 ^ 3 = 6
        // 按位取反测试
        assertNumber(-11, eval("~10"));     // ~10 = -11
        assertNumber(-6, eval("~5"));       // ~5 = -6
        // 复合按位与运算
        assertNumber(0, eval("(255 & 170) & 85"));          // 255&170=170 → 170&85=0
        assertNumber(51, eval("(240 & 60) | (15 & 51)"));   // 240&60=48 | 15&51=3 → 48|3=51
        // 嵌套异或运算
        assertNumber(68, eval("(170 ^ 102) ^ 136"));        // 170^102=204 → 204^136=68
        assertNumber(-86, eval("(~170) ^ 255"));            // ~170=85 → 85^255=170 → ~85=-86
        // 混合运算链
        assertNumber(9, eval("((12 | 10) & ~9) ^ 15"));     // 12|10=14 & ~9=6 → 6^15=9
        // 长整型边界测试
        assertNumber((-4294967296L & -281470681743360L) | 281474976710655L, eval("(-4294967296 & -281470681743360) | 281474976710655"));
        assertNumber(~-4294967296L & -1L, eval("~-4294967296 & -1"));
    }

    private void assertNumber(Number expected, Object actual) {
        assertEquals(QNumber.valueOf(expected.toString()), actual);
    }

    private void assertBool(Boolean expected, Object actual) {
        assertEquals(expected == null ? null : QBool.valueOf(expected), actual);
    }

    private void assertString(String expected, Object actual) {
        assertEquals(expected == null ? null : QString.valueOf(expected), actual);
    }

    @Test
    void testCompare() {
        assertBool(true, eval("1==1"));
        assertBool(false, eval("5.1>5==false "));
        assertBool(true, eval("5.1>5==true "));
        // 基础相等测试
        assertBool(true, eval("1 == 1"));
        assertBool(false, eval("1 == 2"));
        assertBool(true, eval("0==-0"));

        // 不等运算符测试
        assertBool(true, eval("3 != 2"));
        assertBool(false, eval("5 != 5"));
        assertBool(true, eval("-5 != 5"));

        // 大小比较测试
        assertBool(true, eval("5 > 3"));
        assertBool(false, eval("3 > 5"));
        assertBool(true, eval("-2 > -5"));
        assertBool(true, eval("3 < 5"));
        assertBool(false, eval("5 < 3"));
        assertBool(true, eval("-5 < -2"));

        // 边界值测试
        assertBool(true, eval("2147483647 == 2147483647"));
        assertBool(true, eval("9223372036854775807 == 9223372036854775807"));
        assertBool(false, eval("9223372036854775807 == 9223372036854775807 - 1"));

        // 复合比较测试
        assertBool(true, eval("5 >= 5"));
        assertBool(true, eval("6 >= 3"));
        assertBool(false, eval("2 >= 3"));
        assertBool(true, eval("3 <= 5"));
        assertBool(true, eval("5 <= 5"));
        assertBool(false, eval("6 <= 5"));

        // 类型转换测试
        assertBool(true, eval("10 == 10.0"));   // 整型浮点隐式转换
    }

    @Test
    void testBoolean() {
        assertBool(true, eval("true"));
        assertBool(false, eval("false"));
        assertBool(false, eval("!!!true"));
        assertBool(true, eval("!!!false"));
        // 异常情况
        assertNull(eval("true_"));
        assertNull(eval("true5"));
        assertNull(eval("trued"));
        assertNull(eval("trueD"));
        assertThrows(ParseException.class, () -> eval("true!"));
    }

    @Test
    public void testBooleanOperations() {
        // 基础运算测试
        assertBool(true, eval("true && true"));
        assertBool(false, eval("false || false"));
        assertBool(true, eval("true ^^ false"));
        assertBool(false, eval("!true"));

        // 复合运算测试
        assertBool(true, eval("(true || false) && (!false)"));
        assertBool(true, eval("(true && false) || (false ^^ true)"));
        assertBool(false, eval("!((false || true) && (false ^^ true))"));

        // 多级嵌套运算
        assertBool(true, eval("!((true && (false || true)) ^^ (!false && true))"));
        assertBool(true, eval("((true ^^ false) || (false && true)) && (!false || true)"));
    }

    @Test
    void elvis() {
        assertNumber(666, eval("null?:666?:777"));
        assertNumber(666, eval("(null?:null?:null)?:null?:(null?:666)"));
    }

    @Test
    void string() {
        assertString("", eval("\"\""));
        assertString("abc\bde\nfg\rhi\tjk\flm\0no\\pq\"rs\'tuvw", eval("\"abc\\bde\\nfg\\rhi\\tjk\\flm\\0no\\\\pq\\\"rs\\'tu\\v\\w\""));
        assertThrows(TokenException.class, () -> eval("\"aaa"));
        assertThrows(TokenException.class, () -> eval("\""));
        assertThrows(TokenException.class, () -> eval("\"\\"));
        assertThrows(TokenException.class, () -> eval("\"\\ "));
    }

    @Test
    void function() {
        assertNumber(66, eval("abs ( -66)  "));
    }

    @Test
    void method() {
        assertNumber(4, eval("map(1,2,3,4,\"5\",true).get(3)"));
        assertBool(true, eval("map(1,2,3,4,\"_5\",true)._5"));
        assertNumber(88, eval("(-88).abs()"));
    }

    @Test
    void index() {
        String expr = "list(1,2,3,list(false,true))";
        assertNumber(1, eval(expr + "[0]"));
        assertNumber(2, eval(expr + "[1]"));
        assertNumber(3, eval(expr + "[2]"));
        assertEquals(QList.valueOf(QBool.FALSE, QBool.TRUE), eval(expr + "[3]"));
        assertEquals(QList.valueOf(QBool.FALSE, QBool.TRUE), eval(expr + "[1+2]"));
        assertBool(false, eval(expr + "[1+2][0]"));
        assertBool(true, eval(expr + "[1+2][0+1]"));
        assertBool(true, eval(expr + "[number_1 + number_2] [number_0 + 1 ]"));
    }

    @Test
    void map() {
        assertEquals(new QMap(), eval("map()"));
        assertEquals(QMap.valueOf(
                QString.valueOf("hello"), QNumber.valueOf("6"),
                QString.valueOf("bool"), QBool.TRUE,
                QString.valueOf("world"), QMap.valueOf(
                        QNumber.valueOf("15"), QNumber.valueOf("-1"),
                        null, QNumber.valueOf("6"),
                        QString.valueOf("shanghai"), null
                )
        ), eval("map(\"hello\", 1+2+3, \"bool\",true, \"world\", map(7+8,9-10,null,6,  \"shanghai\", null))"));
    }

    @Test
    void list() {
        assertEquals(QList.empty(), eval("list(  )"));
        assertEquals(QList.valueOf(QString.valueOf("hello")), eval("list( \"hello\" )"));
        assertEquals(QList.valueOf(QString.valueOf("hello"), QString.valueOf("world")), eval("list( \"hello\" , \"world\")"));
        assertEquals(QList.valueOf(
                QString.valueOf("hello"),
                QString.valueOf("world"),
                QList.valueOf(QBool.FALSE, QBool.TRUE)
        ), eval("list( \"hello\" , \"world\",list(false,true))"));
    }

    @Test
    void set() {
        assertEquals(QSet.empty(), eval("set(  )"));
        assertEquals(QSet.valueOf(QString.valueOf("hello")), eval("set( \"hello\" )"));
        assertEquals(QSet.valueOf(QString.valueOf("hello"), QString.valueOf("world")), eval("set( \"hello\" , \"world\")"));
        assertEquals(QSet.valueOf(
                QString.valueOf("hello"),
                QString.valueOf("world"),
                QSet.valueOf(QBool.FALSE, QBool.TRUE)
        ), eval("set( \"hello\" , \"world\",set(false,true))"));
    }

    private Object eval(String input) {
        return new Parser(input).parse().eval(context);
    }
}