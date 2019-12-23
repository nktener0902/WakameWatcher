package com.wakame.observer.raspberry.domain.messaging;

public class SenderException extends Exception {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new exception object.
     *
     * @param message the error message
     */
    public SenderException(String message) {
        super(message);
    }

    /**
     * Instantiates a new exception object.
     *
     * @param cause the cause. A null value is permitted, and indicates that the
     *              cause is nonexistent or unknown.
     */
    public SenderException(Throwable cause) {
        super(cause);
    }

}
