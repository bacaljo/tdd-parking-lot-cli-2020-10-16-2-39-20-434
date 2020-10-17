package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;

import java.util.List;

public class SmartParkingBoy extends ParkingBoy {
    public SmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    public ParkingTicket park(Car car) {
        ParkingLot parkingLot = parkingLotList.stream()
                .reduce((mostEmptyParkingLot, p) ->
                        (p.countEmptyPositions() > mostEmptyParkingLot.countEmptyPositions())
                                ? p
                                : mostEmptyParkingLot)
                .orElseThrow(FullParkingException::new);

        return parkingLot.park(car);
    }
}
