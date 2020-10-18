package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingLotEmployee;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.ParkingBoyManagementException;
import com.oocl.cultivation.strategy.parking.SequentialParkingStrategy;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotServiceManager extends ParkingLotEmployee {
    private final List<ParkingBoy> managementList;

    public ParkingLotServiceManager(List<ParkingLot> parkingLot) {
        super(new SequentialParkingStrategy(), parkingLot);
        managementList = new ArrayList<>();
    }

    public void manageParkingBoys(List<ParkingBoy> parkingBoyList) {
        managementList.addAll(parkingBoyList);
    }

    public List<ParkingBoy> getManagementList() {
        return managementList;
    }

    public ParkingTicket orderParkingBoyToPark(ParkingBoy parkingBoy, Car car) {
        validateForUnmanagedParkingBoy(parkingBoy);

        return parkingBoy.park(car);
    }

    public Car orderParkingBoyToFetch(ParkingBoy parkingBoy, ParkingTicket parkingTicket) {
        validateForUnmanagedParkingBoy(parkingBoy);

        return parkingBoy.fetch(parkingTicket);
    }

    private void validateForUnmanagedParkingBoy(ParkingBoy parkingBoy) {
        boolean parkingBoyManaged = managementList.contains(parkingBoy);
        if (parkingBoyManaged) {
            return;
        }

        throw new ParkingBoyManagementException();
    }
}
