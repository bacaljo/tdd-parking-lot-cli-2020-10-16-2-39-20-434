package com.oocl.cultivation.factory;

import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingBoyType;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.exception.UnsupportedParkingBoyTypeException;
import com.oocl.cultivation.strategy.parking.LargestAvailableRateParkingStrategy;
import com.oocl.cultivation.strategy.parking.MostEmptyParkingStrategy;
import com.oocl.cultivation.strategy.parking.SequentialParkingStrategy;

import java.util.List;

import static com.oocl.cultivation.ParkingBoyType.PARKING_BOY;
import static com.oocl.cultivation.ParkingBoyType.SMART_PARKING_BOY;
import static com.oocl.cultivation.ParkingBoyType.SUPER_SMART_PARKING_BOY;

public class ParkingBoyFactory {
    public ParkingBoyFactory() {
    }

    public ParkingBoy getParkingBoy(ParkingBoyType parkingBoyType, List<ParkingLot> parkingLotList) {
        if (parkingBoyType == PARKING_BOY) {
            return new ParkingBoy(new SequentialParkingStrategy(), parkingLotList);
        } else if (parkingBoyType == SMART_PARKING_BOY) {
            return new ParkingBoy(new MostEmptyParkingStrategy(), parkingLotList);
        } else if (parkingBoyType == SUPER_SMART_PARKING_BOY) {
            return new ParkingBoy(new LargestAvailableRateParkingStrategy(), parkingLotList);
        }

        throw new UnsupportedParkingBoyTypeException();
    }
}
