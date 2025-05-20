package com.github.qdexlab.qlang.core.exception;

public class InitializeException extends QLangException {
    public InitializeException() {
    }

    public InitializeException(String message) {
        super(message);
    }

    public InitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializeException(Throwable cause) {
        super(cause);
    }

    public InitializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
