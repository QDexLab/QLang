package io.github.qdexlab.qlang.core.exception;

public class MethodEvalException extends EvalException {
    public MethodEvalException() {
    }

    public MethodEvalException(String message) {
        super(message);
    }

    public MethodEvalException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodEvalException(Throwable cause) {
        super(cause);
    }

    public MethodEvalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
