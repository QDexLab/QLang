package com.github.qlang.core.function.math;

import com.github.qlang.core.exception.FunctionEvalException;
import com.github.qlang.core.function.Function;
import com.github.qlang.core.type.QNumber;
import com.github.qlang.core.type.QObject;

public class AbsFunction implements Function {
    @Override
    public QObject call(QObject[] args) {
        if (args.length != 1) {
            throw new FunctionEvalException("AbsFunction requires exactly 1 argument, but got " + args.length);
        }
        if (args[0] == null) {
            return null;
        }
        if (!(args[0] instanceof QNumber)) {
            throw new FunctionEvalException("AbsFunction must have a number, but got " + args[0]);
        }
        return ((QNumber) args[0]).abs();
    }
}
