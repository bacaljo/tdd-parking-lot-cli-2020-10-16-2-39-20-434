package com.oocl.cultivation.exception;

public class UnrecognizedParkingTicketException extends RuntimeException {
    public UnrecognizedParkingTicketException(String message) {
        super(message);
    }
}
