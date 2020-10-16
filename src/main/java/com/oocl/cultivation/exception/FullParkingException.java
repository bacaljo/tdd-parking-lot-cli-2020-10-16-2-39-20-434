package com.oocl.cultivation.exception;

public class FullParkingException extends RuntimeException {
    public FullParkingException() {
        super("Not enough position.");
    }
}
