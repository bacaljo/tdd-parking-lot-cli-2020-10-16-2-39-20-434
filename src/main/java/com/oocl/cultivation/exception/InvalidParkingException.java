package com.oocl.cultivation.exception;

public class InvalidParkingException extends RuntimeException {
    public InvalidParkingException() {
        super("Car is already parked.");
    }
}
