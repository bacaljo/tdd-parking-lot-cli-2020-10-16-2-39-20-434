package com.oocl.cultivation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingBoyTest {

    private static final String UNRECOGNIZED_TICKET_ERROR_MESSAGE = "Unrecognized parking ticket.";
    private static final String NULL_TICKET_ERROR_MESSAGE = "Please provide your parking ticket.";
    private static final String FULL_PARKING_ERROR_MESSAGE = "Not enough position.";

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
    public void should_throw_unrecognized_ticket_exception_with_message_when_parking_boy_fetch_given_unassociated_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        parkingBoy.park(car);

        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Executable executable = () -> {
            parkingBoy.fetch(fakeParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(ParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_TICKET_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_parking_ticket_exception_when_parking_boy_fetch_given_null_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        parkingBoy.park(car);

        ParkingTicket nullParkingTicket = null;

        // WHEN
        Executable executable = () -> {
            parkingBoy.fetch(nullParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(ParkingTicketException.class, executable);
        assertEquals(NULL_TICKET_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_parking_ticket_exception_with_message_when_parking_boy_fetch_given_an_already_used_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        ParkingTicket parkingTicket = parkingBoy.park(car);
        parkingBoy.fetch(parkingTicket);

        // WHEN
        Executable executable = () -> {
            parkingBoy.fetch(parkingTicket);
        };

        // THEN
        Exception exception = assertThrows(ParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_TICKET_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_a_full_parking_exception_when_parking_boy_park_given_a_parking_lot_with_capacity_1_and_a_parked_car() {
        // GIVEN
        int capacity = 1;
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot(capacity));
        parkingBoy.park(new Car());
        Car anotherCar = new Car();

        // WHEN
        Executable executable = () -> {
            parkingBoy.park(anotherCar);
        };

        // THEN
        Exception exception = assertThrows(FullParkingException.class, executable);
        assertEquals(FULL_PARKING_ERROR_MESSAGE, exception.getMessage());
    }
}
