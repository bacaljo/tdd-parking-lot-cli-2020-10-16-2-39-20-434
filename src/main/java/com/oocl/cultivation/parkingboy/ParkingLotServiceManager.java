package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.strategy.parking.SequentialParkingStrategy;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotServiceManager extends ParkingBoy {
    private final List<ParkingBoy> managementList;

    public ParkingLotServiceManager(List<ParkingLot> parkingLot) {
        super(new SequentialParkingStrategy(), parkingLot);
        managementList = new ArrayList<>();
    }

    public void enlistParkingBoys(List<ParkingBoy> parkingBoyList) {
        managementList.addAll(parkingBoyList);
    }

    public List<ParkingBoy> getManagementList() {
        return managementList;
    }

    public ParkingTicket delegatePark(Car car) {
        return managementList.stream()
                .findFirst()
                .get()
                .park(car);
    }
}
