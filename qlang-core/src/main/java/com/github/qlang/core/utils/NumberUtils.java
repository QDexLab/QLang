package com.github.qlang.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberUtils {

    public static Number plus(Number left, Number right) {
        if (NumberUtils.isInteger(left) && NumberUtils.isInteger(right)) {
            return left.longValue() + right.longValue();
        } else {
            return left.doubleValue() + right.doubleValue();
        }
    }

    public static Number minus(Number left, Number right) {
        if (NumberUtils.isInteger(left) && NumberUtils.isInteger(right)) {
            return left.longValue() - right.longValue();
        } else {
            return left.doubleValue() - right.doubleValue();
        }
    }

    public static Number mul(Number left, Number right) {
        if (NumberUtils.isInteger(left) && NumberUtils.isInteger(right)) {
            return left.longValue() * right.longValue();
        } else {
            return left.doubleValue() * right.doubleValue();
        }
    }

    public static Number div(Number left, Number right) {
        if (NumberUtils.isInteger(left) && NumberUtils.isInteger(right)) {
            return left.longValue() / right.longValue();
        } else {
            return left.doubleValue() / right.doubleValue();
        }
    }

    public static Number mod(Number left, Number right) {
        if (NumberUtils.isInteger(left) && NumberUtils.isInteger(right)) {
            return left.longValue() % right.longValue();
        } else {
            return left.doubleValue() % right.doubleValue();
        }
    }

    public static Number pow(Number left, Number right) {
        return Math.pow(left.doubleValue(), right.doubleValue());
    }

    public static Number bitReverse(Number number) {
        if (NumberUtils.isInteger(number)) {
            return ~number.longValue();
        } else {
            return null;
        }
    }

    public static Number leftShift(Number number, Number shift) {
        if (NumberUtils.isInteger(number) && NumberUtils.isInteger(shift)) {
            return number.longValue() << shift.longValue();
        } else {
            return null;
        }
    }

    public static Number rightShift(Number number, Number shift) {
        if (NumberUtils.isInteger(number) && NumberUtils.isInteger(shift)) {
            return number.longValue() >> shift.longValue();
        } else {
            return null;
        }
    }

    public static Number unsignedRightShift(Number number, Number shift) {
        if (NumberUtils.isInteger(number) && NumberUtils.isInteger(shift)) {
            return number.longValue() >>> shift.longValue();
        } else {
            return null;
        }
    }

    public static boolean isInteger(Number number) {
        if (number == null) {
            return false;
        }

        // 直接判断整数类型
        if (number instanceof Integer || number instanceof Long ||
                number instanceof Short || number instanceof Byte ||
                number instanceof BigInteger) {
            return true;
        }

        // 处理浮点类型（Double/Float）
        if (number instanceof Double || number instanceof Float) {
            double value = number.doubleValue();
            return !Double.isNaN(value) &&
                    !Double.isInfinite(value) &&
                    value == Math.floor(value);
        }

        // 处理 BigDecimal
        if (number instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) number;
            return bd.scale() <= 0 ||
                    bd.stripTrailingZeros().scale() <= 0;
        }

        // 处理其他类型（如 AtomicInteger、自定义子类等）
        try {
            BigDecimal bd = new BigDecimal(number.toString());
            return bd.scale() <= 0 ||
                    bd.stripTrailingZeros().scale() <= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
