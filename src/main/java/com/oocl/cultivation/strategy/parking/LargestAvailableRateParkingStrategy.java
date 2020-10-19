package com.oocl.cultivation.strategy.parking;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.strategy.ParkingStrategy;

import java.util.List;

import static java.util.Comparator.comparingDouble;

public class LargestAvailableRateParkingStrategy implements ParkingStrategy {
    @Override
    public ParkingTicket park(Car car, List<ParkingLot> parkingLotList) {
        return parkingLotList.stream()
                .max(comparingDouble(ParkingLot::getAvailablePositionRate))
                .orElseThrow(FullParkingException::new)
                .park(car);
    }
}
