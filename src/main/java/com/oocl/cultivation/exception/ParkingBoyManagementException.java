package com.oocl.cultivation.exception;

public class ParkingBoyManagementException extends RuntimeException {
    public ParkingBoyManagementException() {
        super("The parking boy is not under your management.");
    }
}
