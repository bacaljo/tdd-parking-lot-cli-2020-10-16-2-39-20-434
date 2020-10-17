package com.oocl.cultivation;

import com.oocl.cultivation.strategy.DefaultFetchingStrategy;
import com.oocl.cultivation.strategy.FetchingStrategy;
import com.oocl.cultivation.strategy.ParkingStrategy;

import java.util.List;

public class ParkingBoy {
    protected final List<ParkingLot> parkingLotList;
    private final ParkingStrategy parkingStrategy;
    private final FetchingStrategy fetchingStrategy;

    public ParkingBoy(ParkingStrategy parkingStrategy, List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
        this.parkingStrategy = parkingStrategy;
        fetchingStrategy = new DefaultFetchingStrategy();
    }

    public ParkingTicket park(Car car) {
        return parkingStrategy.park(car, parkingLotList);
    }

    public Car fetch(ParkingTicket parkingTicket) {
        return fetchingStrategy.fetch(parkingTicket, parkingLotList);
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }
}
