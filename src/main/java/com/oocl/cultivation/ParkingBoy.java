package com.oocl.cultivation;

public class ParkingBoy {
    private static final String UNRECOGNIZED_TICKET_ERROR_MESSAGE = "Unrecognized parking ticket.";
    private static final String NULL_TICKET_ERROR_MESSAGE = "Please provide your parking ticket.";
    private final ParkingLot parkingLot;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingTicket park(Car car) {
        return parkingLot.park(car);
    }

    public Car fetch(ParkingTicket parkingTicket) {
        if (parkingTicket == null) {
            throw new ParkingTicketException(NULL_TICKET_ERROR_MESSAGE);
        }

        boolean ticketIsAlreadyUsed = parkingLot.getUsedParkingTicketList().contains(parkingTicket);
        if (ticketIsAlreadyUsed) {
            throw new ParkingTicketException(UNRECOGNIZED_TICKET_ERROR_MESSAGE);
        }

        Car car = parkingLot.fetch(parkingTicket);
        if (car == null) {
            throw new ParkingTicketException(UNRECOGNIZED_TICKET_ERROR_MESSAGE);
        }

        return car;
    }
}
