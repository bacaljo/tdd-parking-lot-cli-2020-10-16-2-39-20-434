package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkingLotServiceManagerTest {
    @Test
    public void should_get_2_parking_boys_when_manage_parking_boys_given_2_parking_boys() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager();
        ParkingBoy parkingBoy1 = new ParkingBoy(new ParkingLot());
        ParkingBoy parkingBoy2 = new ParkingBoy(new ParkingLot());

        int parkingBoysCount = 2;

        // when
        parkingLotServiceManager.manageParkingBoys(asList(parkingBoy1, parkingBoy2));

        // then
        assertEquals(parkingBoysCount, parkingLotServiceManager.getManagementList().size());
    }
}
