package com.oocl.cultivation;

import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;

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
            throw new MissingParkingTicketException();
        }

        boolean ticketIsAlreadyUsed = parkingLot.getUsedParkingTicketList().contains(parkingTicket);
        if (ticketIsAlreadyUsed) {
            throw new UnrecognizedParkingTicketException();
        }

        Car car = parkingLot.fetch(parkingTicket);
        if (car == null) {
            throw new UnrecognizedParkingTicketException();
        }

        return car;
    }
}
