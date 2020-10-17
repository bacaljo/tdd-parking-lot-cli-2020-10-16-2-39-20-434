package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.ParkingBoyManagementException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotServiceManager extends ParkingBoy {
    private List<ParkingBoy> managementList;

    public ParkingLotServiceManager(List<ParkingLot> parkingLot) {
        super(parkingLot);
        managementList = new ArrayList<>();
    }

    public void manageParkingBoys(List<ParkingBoy> parkingBoyList) {
        managementList.addAll(parkingBoyList);
    }

    public List<ParkingBoy> getManagementList() {
        return managementList;
    }

    public ParkingTicket orderParkingBoyToPark(ParkingBoy parkingBoy, Car car) {
        validateIfParkingBoyIsUnderManagement(parkingBoy);

        return parkingBoy.park(car);
    }

    public Car orderParkingBoyToFetch(ParkingBoy parkingBoy, ParkingTicket parkingTicket) {
        validateIfParkingBoyIsUnderManagement(parkingBoy);

        return parkingBoy.fetch(parkingTicket);
    }

    private void validateIfParkingBoyIsUnderManagement(ParkingBoy parkingBoy) {
        boolean parkingBoyManaged = managementList.contains(parkingBoy);
        if (parkingBoyManaged) {
            return;
        }

        throw new ParkingBoyManagementException();
    }
}
