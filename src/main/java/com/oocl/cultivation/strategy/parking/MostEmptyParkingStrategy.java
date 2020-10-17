package com.oocl.cultivation.strategy.parking;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.strategy.ParkingStrategy;

import java.util.List;

public class MostEmptyParkingStrategy implements ParkingStrategy {
    @Override
    public ParkingTicket park(Car car, List<ParkingLot> parkingLotList) {
        ParkingLot parkingLot = parkingLotList.stream()
                .reduce((mostEmptyParkingLot, p) ->
                        (p.countEmptyPositions() > mostEmptyParkingLot.countEmptyPositions())
                                ? p
                                : mostEmptyParkingLot)
                .orElseThrow(FullParkingException::new);

        return parkingLot.park(car);
    }
}
