package io.github.qdexlab.qlang.core.exception;

public class FunctionEvalException extends EvalException {
    public FunctionEvalException() {
    }

    public FunctionEvalException(String message) {
        super(message);
    }

    public FunctionEvalException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionEvalException(Throwable cause) {
        super(cause);
    }

    public FunctionEvalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
