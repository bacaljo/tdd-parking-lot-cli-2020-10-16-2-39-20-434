package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.ParkingBoyManagementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.oocl.cultivation.TestHelper.PARKING_BOY_MANAGEMENT_EXCEPTION_MESSAGE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void should_return_ticket_when_order_parking_boy_to_park_given_a_managed_parking_boy_and_car() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        parkingLotServiceManager.manageParkingBoys(asList(parkingBoy));
        Car car = new Car();

        // when
        ParkingTicket parkingTicket = parkingLotServiceManager.orderParkingBoyToPark(parkingBoy, car);

        // then
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_throw_an_error_when_order_parking_boy_to_park_given_an_unmanaged_parking_boy() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        Car car = new Car();

        // when
        Executable executable = () -> {
            parkingLotServiceManager.orderParkingBoyToPark(parkingBoy, car);
        };

        // then
        Exception exception = assertThrows(ParkingBoyManagementException.class, executable);
        assertEquals(PARKING_BOY_MANAGEMENT_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_return_the_correct_car_when_order_parking_boy_to_fetch_given_a_managed_parking_boy_and_a_ticket() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        Car car = new Car();
        ParkingTicket parkingTicket = parkingBoy.park(car);
        parkingLotServiceManager.manageParkingBoys(asList(parkingBoy));

        // when
        Car fetchedCar = parkingLotServiceManager.orderParkingBoyToFetch(parkingBoy, parkingTicket);

        // then
        assertSame(car, fetchedCar);
    }
}
