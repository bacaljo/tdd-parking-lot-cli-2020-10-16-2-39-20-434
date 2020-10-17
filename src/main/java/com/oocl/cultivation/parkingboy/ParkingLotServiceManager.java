package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.ParkingBoyManagementException;

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
        boolean parkingBoyIsNotManaged = !managementList.contains(parkingBoy);
        if (parkingBoyIsNotManaged) {
            throw new ParkingBoyManagementException();
        }

        return parkingBoy.park(car);
    }

    public Car orderParkingBoyToFetch(ParkingBoy parkingBoy, ParkingTicket parkingTicket) {
        boolean parkingBoyIsNotManaged = !managementList.contains(parkingBoy);
        if (parkingBoyIsNotManaged) {
            throw new ParkingBoyManagementException();
        }

        return parkingBoy.fetch(parkingTicket);
    }
}
