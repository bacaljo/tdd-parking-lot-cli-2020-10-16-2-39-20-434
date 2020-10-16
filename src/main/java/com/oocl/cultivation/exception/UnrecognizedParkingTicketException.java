package com.oocl.cultivation.exception;

public class UnrecognizedParkingTicketException extends RuntimeException {
    public UnrecognizedParkingTicketException() {
        super("Unrecognized parking ticket.");
    }
}
