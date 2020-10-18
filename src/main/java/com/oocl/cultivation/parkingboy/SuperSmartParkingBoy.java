package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingLotEmployee;
import com.oocl.cultivation.strategy.parking.LargestAvailableRateParkingStrategy;

import java.util.List;

public class SuperSmartParkingBoy extends ParkingLotEmployee {
    public SuperSmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(new LargestAvailableRateParkingStrategy(), parkingLotList);
    }
}
