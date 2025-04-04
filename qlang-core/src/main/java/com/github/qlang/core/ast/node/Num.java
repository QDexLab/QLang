package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.ParseException;

import java.text.NumberFormat;

public class Num extends Node {

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    private final Number value;

    public Num(String value) {
        try {
            this.value = NUMBER_FORMAT.parse(value);
        } catch (java.text.ParseException e) {
            throw new ParseException(e);
        }
    }

    @Override
    public Object eval(Context context) {
        return this.value;
    }
}
