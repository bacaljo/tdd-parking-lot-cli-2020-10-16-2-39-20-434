package com.oocl.cultivation.strategy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingStrategy;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;

import java.util.List;

import static java.util.function.Predicate.not;

public class SequentialParkingStrategy implements ParkingStrategy {
    @Override
    public ParkingTicket park(Car car, List<ParkingLot> parkingLotList) {
        ParkingLot parkingLot = parkingLotList.stream()
                .filter(not(ParkingLot::isFull))
                .findFirst()
                .orElseThrow(FullParkingException::new);

        return parkingLot.park(car);
    }
}
