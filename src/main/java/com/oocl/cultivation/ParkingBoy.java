package com.oocl.cultivation;

import com.oocl.cultivation.exception.InvalidParkingException;
import com.oocl.cultivation.exception.MissingCarException;
import com.oocl.cultivation.strategy.FetchingStrategy;
import com.oocl.cultivation.strategy.ParkingStrategy;
import com.oocl.cultivation.strategy.fetching.DefaultFetchingStrategy;

import java.util.List;

import static com.oocl.cultivation.ValidationHelper.validate;

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
        validate(car != null, new MissingCarException());
        boolean isNotParked = parkingLotList.stream().noneMatch(parkingLot -> parkingLot.containsParkedCar(car));
        validate(isNotParked, new InvalidParkingException());

        return parkingStrategy.park(car, parkingLotList);
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
