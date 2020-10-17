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
        if (parkingTicket == null) {
            throw new MissingParkingTicketException();
        }

        for (ParkingLot parkingLot : parkingLotList) {
            if (parkingLot.isTicketInUsedTicketList(parkingTicket)) {
                throw new UnrecognizedParkingTicketException();
            }

            Car car = parkingLot.fetch(parkingTicket);
            if (car != null) {
                return car;
            }
        }

        throw new UnrecognizedParkingTicketException();
    }
}
