package com.oocl.cultivation.parkinglotemployee;

import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingLotEmployee;
import com.oocl.cultivation.strategy.parking.SequentialParkingStrategy;

import java.util.List;

public class ParkingBoy extends ParkingLotEmployee {

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        super(new SequentialParkingStrategy(), parkingLotList);
    }
}
