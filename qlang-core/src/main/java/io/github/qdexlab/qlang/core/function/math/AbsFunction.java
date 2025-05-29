package io.github.qdexlab.qlang.core.function.math;

import io.github.qdexlab.qlang.core.exception.FunctionEvalException;
import io.github.qdexlab.qlang.core.function.FunctionDefinition;
import io.github.qdexlab.qlang.core.type.QNumber;

@FunctionDefinition("abs")
public class AbsFunction extends MathFunction {

    @Override
    protected QNumber doCall(QNumber[] args) {
        if (args.length != 1) {
            throw new FunctionEvalException("AbsFunction requires exactly 1 argument, but got " + args.length);
        }
        return args[0].abs();
    }
}
