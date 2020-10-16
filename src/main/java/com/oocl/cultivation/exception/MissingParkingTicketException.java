package com.oocl.cultivation.exception;

public class MissingParkingTicketException extends RuntimeException {
    public MissingParkingTicketException(String message) {
        super(message);
    }
}
