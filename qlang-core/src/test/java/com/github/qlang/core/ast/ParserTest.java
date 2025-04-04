package com.github.qlang.core.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context() {};
    }

    @Test
    void baseArithmetic() {
        // 基础运算
        assertEquals(2L, eval("1 - 2 + 3"));
        assertEquals(-3L, eval("4 / 2 - 5"));
        assertEquals(16L, eval("(3 + 5) * 2"));
        assertEquals(0.125, eval("0.5 * 0.25"));
        assertEquals(15L, eval("10 - -5"));
        assertEquals(5L, eval("10 - + - -5"));
        assertEquals(9L, eval("2^3 + 4 % 3"));

        /**
         * 基础运算
         *
         * 2^3 + 4 % 3
         *
         * 复杂表达式
         * ( (3 + 5 * 2) / (4 - 1) ) ^ 2
         * (1.5 + 2.5) * (3.2 - 4.8) / (-0.5)
         * 2 * (3 + 4) / (5 % 3) + 6
         * ( (10 - 5) * 2^3 ) / (6 + 4 % 2)
         *
         * 边界条件与特殊值
         * 0 / 5                # 除数为零（非错误）
         * 5 / 0                # 除以零（需抛出异常）
         * 999999999 * 999999999 # 大数溢出测试
         * -0.0 + 0.0           # 负零与零运算
         * 1.234e5 * 2.0        # 科学计数法
         * 3.14159265358979323846 / 2  # 高精度小数
         *
         * ‌空格与格式变化
         * 3+4*2/(1-5)          # 无空格
         * 3 + 4 * 2 / ( 1 -5 )# 混合空格
         *   5   -  3  *  2    # 多空格
         * ( ( 3 + 2 ) * 5 )    # 多余括号
         *
         * 非法表达式（需捕获错误）
         * 3 + * 4              # 运算符连续
         * (5 + 3               # 括号不匹配
         * 2.3.4 + 5            # 小数点错误
         * abc + 5              # 非法字符
         * 5 + 4 % 0            # 取模零
         */
    }

    private Object eval(String input) {
        return new Parser(input).parse().eval(context);
    }
}