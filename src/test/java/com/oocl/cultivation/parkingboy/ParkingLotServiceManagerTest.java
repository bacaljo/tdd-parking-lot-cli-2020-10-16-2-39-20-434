package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.Test;

import static com.oocl.cultivation.ParkingBoyType.PARKING_BOY;
import static com.oocl.cultivation.TestHelper.generateParkingBoy;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParkingLotServiceManagerTest {

    @Test
    public void should_have_a_list_of_parking_lot_when_initialized_given_a_list_of_parking_lot() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));

        int expectedParkingLotCount = 1;

        // when
        int parkingLotCount = parkingLotServiceManager.getParkingLotList().size();

        // then
        assertEquals(expectedParkingLotCount, parkingLotCount);
    }

    @Test
    public void should_get_2_parking_boys_when_enlist_parking_boys_given_2_parking_boys() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingBoy parkingBoy1 = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));
        ParkingBoy parkingBoy2 = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));

        int parkingBoysCount = 2;

        // when
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy1, parkingBoy2));

        // then
        assertEquals(parkingBoysCount, parkingLotServiceManager.getManagementList().size());
    }

    @Test
    public void should_return_ticket_when_delegate_park_given_a_managed_parking_boy_and_a_car() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy));
        Car car = new Car();

        // when
        ParkingTicket parkingTicket = parkingLotServiceManager.delegatePark(car);

        // then
        assertNotNull(parkingTicket);
    }
}
