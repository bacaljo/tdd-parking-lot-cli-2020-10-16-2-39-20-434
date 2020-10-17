package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingTicket;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotServiceManager {
    private List<ParkingBoy> managementList;

    public ParkingLotServiceManager() {
        managementList = new ArrayList<>();
    }

    public void manageParkingBoys(List<ParkingBoy> parkingBoyList) {
        managementList.addAll(parkingBoyList);
    }

    public List<ParkingBoy> getManagementList() {
        return managementList;
    }

    public ParkingTicket orderParkingBoyToPark(ParkingBoy parkingBoy, Car car) {
        return parkingBoy.park(car);
    }
}
