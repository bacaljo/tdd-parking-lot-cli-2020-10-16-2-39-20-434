package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.ParkingBoy;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotServiceManager {
    private List<ParkingBoy> managementList;

    public ParkingLotServiceManager() {
        managementList = new ArrayList<>();
    }

    public void manageParkingBoy(ParkingBoy parkingBoy) {
        managementList.add(parkingBoy);
    }

    public List<ParkingBoy> getManagementList() {
        return managementList;
    }
}
