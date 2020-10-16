package com.oocl.cultivation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ParkingBoyTest {
    @Test
    public void should_return_a_parking_ticket_when_parking_boy_park_given_a_car() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());

        // WHEN
        ParkingTicket parkingTicket = parkingBoy.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_return_the_correct_car_when_parking_boy_fetch_given_the_correct_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        ParkingTicket parkingTicket = parkingBoy.park(car);

        // WHEN
        Car fetchedCar = parkingBoy.fetch(parkingTicket);

        // THEN
        assertSame(fetchedCar, car);
    }

    @Test
    public void should_return_the_correct_cars_when_parking_boy_fetch_given_two_tickets() {
        // GIVEN
        Car car1 = new Car();
        Car car2 = new Car();

        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());

        ParkingTicket parkingTicket1 = parkingBoy.park(car1);
        ParkingTicket parkingTicket2 = parkingBoy.park(car2);

        // WHEN
        Car fetchedCar1 = parkingBoy.fetch(parkingTicket1);
        Car fetchedCar2 = parkingBoy.fetch(parkingTicket2);

        // THEN
        assertSame(fetchedCar1, car1);
        assertSame(fetchedCar2, car2);
    }

    @Test
    public void should_return_null_when_parking_boy_fetch_given_unassociated_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        ParkingTicket parkingTicket = parkingBoy.park(car);

        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Car fetchedCar = parkingBoy.fetch(fakeParkingTicket);

        // THEN
        assertNull(fetchedCar);
    }

}
