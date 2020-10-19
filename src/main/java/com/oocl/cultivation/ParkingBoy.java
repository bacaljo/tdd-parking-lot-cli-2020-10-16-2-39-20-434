package com.oocl.cultivation;

import com.oocl.cultivation.exception.InvalidParkingException;
import com.oocl.cultivation.exception.MissingCarException;
import com.oocl.cultivation.strategy.FetchingStrategy;
import com.oocl.cultivation.strategy.ParkingStrategy;
import com.oocl.cultivation.strategy.fetching.DefaultFetchingStrategy;

import java.util.List;

public class ParkingBoy {
    private final List<ParkingLot> parkingLotList;
    private final ParkingStrategy parkingStrategy;
    private final FetchingStrategy fetchingStrategy;

    public ParkingBoy(ParkingStrategy parkingStrategy, List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
        this.parkingStrategy = parkingStrategy;
        fetchingStrategy = new DefaultFetchingStrategy();
    }

    public ParkingTicket park(Car car) {
        validateForMissingCar(car);
        validateForAlreadyParkedCar(car);

        return parkingStrategy.park(car, parkingLotList);
    }

    private void validateForMissingCar(Car car) {
        if (car == null) {
            throw new MissingCarException();
        }
    }

    private void validateForAlreadyParkedCar(Car car) {
        boolean isAlreadyParked = parkingLotList.stream().anyMatch(parkingLot -> parkingLot.containsParkedCar(car));
        if (isAlreadyParked) {
            throw new InvalidParkingException();
        }
    }

    public Car fetch(ParkingTicket parkingTicket) {
        return fetchingStrategy.fetch(parkingTicket, parkingLotList);
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }

    public ParkingStrategy getParkingStrategy() {
        return parkingStrategy;
    }
}
