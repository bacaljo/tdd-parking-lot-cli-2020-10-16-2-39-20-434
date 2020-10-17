package com.oocl.cultivation;

import java.util.List;

public interface ParkingStrategy {
    ParkingTicket park(Car car, List<ParkingLot> parkingLotList);
}
