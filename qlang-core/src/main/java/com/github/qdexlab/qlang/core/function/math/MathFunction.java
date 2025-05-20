package com.github.qdexlab.qlang.core.function.math;

import com.github.qdexlab.qlang.core.exception.FunctionEvalException;
import com.github.qdexlab.qlang.core.function.Function;
import com.github.qdexlab.qlang.core.type.QNumber;
import com.github.qdexlab.qlang.core.type.QObject;

public abstract class MathFunction implements Function {
    @Override
    public QObject call(QObject[] args) {
        QNumber[] numbers = new QNumber[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof QNumber) {
                numbers[i] = (QNumber) args[i];
            } else {
                throw new FunctionEvalException("math function arguments must be numbers, but got " + args[i]);
            }
        }
        return doCall(numbers);
    }

    protected abstract QNumber doCall(QNumber[] args);
}
