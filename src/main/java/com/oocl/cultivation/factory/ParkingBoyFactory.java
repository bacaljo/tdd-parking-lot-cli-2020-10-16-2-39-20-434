package com.oocl.cultivation.factory;

import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingBoyType;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.strategy.parking.SequentialParkingStrategy;

import java.util.List;

import static com.oocl.cultivation.ParkingBoyType.PARKING_BOY;

public class ParkingBoyFactory {
    public ParkingBoyFactory() {
    }

    public ParkingBoy getParkingBoy(ParkingBoyType parkingBoyType, List<ParkingLot> parkingLotList) {
        if (parkingBoyType == PARKING_BOY) {
            return new ParkingBoy(new SequentialParkingStrategy(), parkingLotList);
        }
        return null;
    }
}
