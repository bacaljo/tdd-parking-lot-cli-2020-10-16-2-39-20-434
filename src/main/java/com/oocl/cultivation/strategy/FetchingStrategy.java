package com.oocl.cultivation.strategy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;

import java.util.List;

public interface FetchingStrategy {
    Car fetch(ParkingTicket parkingTicket, List<ParkingLot> parkingLotList);
}
