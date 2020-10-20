package com.oocl.cultivation.strategy.parking;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.strategy.ParkingStrategy;

import java.util.List;

import static java.util.Comparator.comparingDouble;
import static java.util.function.Predicate.not;

public class LargestAvailableRateParkingStrategy implements ParkingStrategy {
    @Override
    public ParkingTicket park(Car car, List<ParkingLot> parkingLotList) {
        return parkingLotList.stream()
                .filter(not(ParkingLot::isFull))
                .max(comparingDouble(ParkingLot::getAvailablePositionRate))
                .orElseThrow(FullParkingException::new)
                .park(car);
    }
}
