package com.github.qdexlab.qlang.core.exception;

public class QLangException extends RuntimeException {
    public QLangException() {
    }

    public QLangException(String message) {
        super(message);
    }

    public QLangException(String message, Throwable cause) {
        super(message, cause);
    }

    public QLangException(Throwable cause) {
        super(cause);
    }

    public QLangException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
