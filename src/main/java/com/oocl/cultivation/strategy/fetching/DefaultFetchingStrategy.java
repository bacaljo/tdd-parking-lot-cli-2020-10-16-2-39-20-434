package com.oocl.cultivation.strategy.fetching;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import com.oocl.cultivation.strategy.FetchingStrategy;

import java.util.List;
import java.util.Objects;

public class DefaultFetchingStrategy implements FetchingStrategy {

    @Override
    public Car fetch(ParkingTicket parkingTicket, List<ParkingLot> parkingLotList) {
        validateForMissingParkingTicket(parkingTicket);

        return parkingLotList.stream()
                .map(parkingLot -> parkingLot.fetch(parkingTicket))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(UnrecognizedParkingTicketException::new);
    }

    private void validateForMissingParkingTicket(ParkingTicket parkingTicket) {
        if (parkingTicket == null) {
            throw new MissingParkingTicketException();
        }
    }
}
