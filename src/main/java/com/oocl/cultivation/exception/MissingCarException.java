package com.oocl.cultivation.exception;

public class MissingCarException extends RuntimeException {
    public MissingCarException() {
        super("There is no car to park.");
    }
}
