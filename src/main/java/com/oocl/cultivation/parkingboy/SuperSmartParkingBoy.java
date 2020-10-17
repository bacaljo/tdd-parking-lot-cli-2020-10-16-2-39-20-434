package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;

import java.util.List;

public class SuperSmartParkingBoy extends ParkingBoy {
    public SuperSmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    public ParkingTicket park(Car car) {
        ParkingLot parkingLot = parkingLotList.stream()
                .reduce((largestAvailablePositionRateParkingLot, p) ->
                        (p.getAvailablePositionRate() > largestAvailablePositionRateParkingLot.getAvailablePositionRate())
                                ? p
                                : largestAvailablePositionRateParkingLot)
                .orElseThrow(FullParkingException::new);

        return parkingLot.park(car);
    }
}
