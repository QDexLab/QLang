package com.github.qlang.core.ast.node;

import com.github.qlang.core.ast.Context;
import com.github.qlang.core.exception.ParseException;
import com.github.qlang.core.type.QNumber;
import com.github.qlang.core.type.QObject;

public class Num extends Node {

    private final QNumber value;

    public Num(String value) {
        try {
            this.value = QNumber.valueOf(value);
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    @Override
    public QObject eval(Context context) {
        return this.value;
    }
}
