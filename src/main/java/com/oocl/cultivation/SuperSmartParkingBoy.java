package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;

import java.util.List;

public class SuperSmartParkingBoy extends ParkingBoy {
    public SuperSmartParkingBoy(ParkingLot... parkingLot) {
        super(parkingLot);
    }

    public SuperSmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

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
