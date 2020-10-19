package com.oocl.cultivation.factory;

import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingBoyType;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.exception.UnsupportedParkingBoyTypeException;
import com.oocl.cultivation.strategy.parking.LargestAvailableRateParkingStrategy;
import com.oocl.cultivation.strategy.parking.MostEmptyParkingStrategy;
import com.oocl.cultivation.strategy.parking.SequentialParkingStrategy;

import java.util.List;

public class ParkingBoyFactory {
    public ParkingBoy getParkingBoy(ParkingBoyType parkingBoyType, List<ParkingLot> parkingLotList) {
        if (parkingBoyType == ParkingBoyType.PARKING_BOY) {
            return new ParkingBoy(new SequentialParkingStrategy(), parkingLotList);
        } else if (parkingBoyType == ParkingBoyType.SMART_PARKING_BOY) {
            return new ParkingBoy(new MostEmptyParkingStrategy(), parkingLotList);
        } else if (parkingBoyType == ParkingBoyType.SUPER_SMART_PARKING_BOY) {
            return new ParkingBoy(new LargestAvailableRateParkingStrategy(), parkingLotList);
        }
        throw new UnsupportedParkingBoyTypeException();
    }
}
