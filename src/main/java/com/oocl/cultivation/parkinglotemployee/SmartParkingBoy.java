package com.oocl.cultivation.parkinglotemployee;

import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingLotEmployee;
import com.oocl.cultivation.strategy.parking.MostEmptyParkingStrategy;

import java.util.List;

public class SmartParkingBoy extends ParkingLotEmployee {
    public SmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(new MostEmptyParkingStrategy(), parkingLotList);
    }
}
