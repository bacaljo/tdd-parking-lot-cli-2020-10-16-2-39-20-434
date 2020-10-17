package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    public void should_return_ticket_when_order_parking_boy_to_park_given_parking_boy_and_car() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        Car car = new Car();

        // when
        ParkingTicket parkingTicket = parkingLotServiceManager.orderParkingBoyToPark(parkingBoy, car);

        // then
        assertNotNull(parkingTicket);
    }
}
