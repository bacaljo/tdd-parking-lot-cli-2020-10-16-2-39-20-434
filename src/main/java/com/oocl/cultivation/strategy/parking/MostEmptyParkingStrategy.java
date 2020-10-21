package com.oocl.cultivation.strategy.parking;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.strategy.ParkingStrategy;

import java.util.List;

import static java.util.Comparator.comparingInt;
import static java.util.function.Predicate.not;

public class MostEmptyParkingStrategy implements ParkingStrategy {
    @Override
    public ParkingTicket park(Car car, List<ParkingLot> parkingLotList) {
        return parkingLotList.stream()
                .filter(not(ParkingLot::isFull))
                .max(comparingInt(ParkingLot::countEmptyPositions))
                .orElseThrow(FullParkingException::new)
                .park(car);
    }
}
