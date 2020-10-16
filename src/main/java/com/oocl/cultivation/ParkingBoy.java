package com.oocl.cultivation;

public class ParkingBoy {
    private final ParkingLot parkingLot;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingTicket park(Car car) {
        return parkingLot.park(car);
    }

    public Car fetch(ParkingTicket parkingTicket) {
        if (parkingTicket == null) {
            return null;
        }

        boolean ticketIsAlreadyUsed = parkingLot.getUsedParkingTicketList().contains(parkingTicket);
        if (ticketIsAlreadyUsed) {
            throw new ParkingTicketException("Unrecognized parking ticket.");
        }

        Car car = parkingLot.fetch(parkingTicket);
        if (car == null) {
            throw new ParkingTicketException("Unrecognized parking ticket.");
        }

        return car;
    }
}
