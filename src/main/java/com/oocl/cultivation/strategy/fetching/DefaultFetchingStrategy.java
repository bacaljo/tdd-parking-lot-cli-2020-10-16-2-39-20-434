package com.oocl.cultivation.strategy.fetching;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import com.oocl.cultivation.strategy.FetchingStrategy;

import java.util.List;

public class DefaultFetchingStrategy implements FetchingStrategy {

    @Override
    public Car fetch(ParkingTicket parkingTicket, List<ParkingLot> parkingLotList) {
        validateForMissingParkingTicket(parkingTicket);

        for (ParkingLot parkingLot : parkingLotList) {
            validateForUsedParkingTicket(parkingTicket, parkingLot);

            Car car = parkingLot.fetch(parkingTicket);
            if (car != null) {
                return car;
            }
        }

        throw new UnrecognizedParkingTicketException();
    }

    private void validateForMissingParkingTicket(ParkingTicket parkingTicket) {
        if (parkingTicket == null) {
            throw new MissingParkingTicketException();
        }
    }

    private void validateForUsedParkingTicket(ParkingTicket parkingTicket, ParkingLot parkingLot) {
        if (parkingLot.isTicketInUsedTicketList(parkingTicket)) {
            throw new UnrecognizedParkingTicketException();
        }
    }
}
