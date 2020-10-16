package com.oocl.cultivation.exception;

public class MissingParkingTicketException extends RuntimeException {
    public MissingParkingTicketException() {
        super("Please provide your parking ticket.");
    }
}
