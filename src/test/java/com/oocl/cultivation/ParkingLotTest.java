package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingLotTest {
    private static final String FULL_PARKING_ERROR_MESSAGE = "Not enough position.";

    @Test
    public void should_have_a_default_capacity_of_10_when_is_initialized_given_no_capacity() {
        // GIVEN
        ParkingLot parkingLot;
        int defaultCapacity = 10;

        // WHEN
        parkingLot = new ParkingLot();

        // THEN
        assertEquals(defaultCapacity, parkingLot.getCapacity());
    }

    @Test
    public void should_return_the_correct_capacity_when_is_initialized_given_a_capacity() {
        // GIVEN
        ParkingLot parkingLot;
        int capacity = 2;

        // WHEN
        parkingLot = new ParkingLot(capacity);

        // THEN
        assertEquals(capacity, parkingLot.getCapacity());
    }

    @Test
    public void should_return_a_parking_ticket_when_park_given_a_car() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();

        // WHEN
        ParkingTicket parkingTicket = parkingLot.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_return_the_correct_car_when_fetch_given_the_correct_ticket() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        ParkingTicket parkingTicket = parkingLot.park(car);

        // WHEN
        Car fetchedCar = parkingLot.fetch(parkingTicket);

        // THEN
        assertSame(fetchedCar, car);
    }

    @Test
    public void should_return_the_correct_cars_when_fetch_given_two_tickets() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        Car car1 = new Car();
        Car car2 = new Car();

        ParkingTicket parkingTicket1 = parkingLot.park(car1);
        ParkingTicket parkingTicket2 = parkingLot.park(car2);

        // WHEN
        Car fetchedCar1 = parkingLot.fetch(parkingTicket1);
        Car fetchedCar2 = parkingLot.fetch(parkingTicket2);

        // THEN
        assertSame(fetchedCar1, car1);
        assertSame(fetchedCar2, car2);
    }

    @Test
    public void should_return_null_when_fetch_given_unassociated_ticket() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        parkingLot.park(car);

        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Car fetchedCar = parkingLot.fetch(fakeParkingTicket);

        // THEN
        assertNull(fetchedCar);
    }

    @Test
    public void should_return_null_when_fetch_given_null_ticket() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        parkingLot.park(car);

        ParkingTicket nullParkingTicket = null;

        // WHEN
        Car fetchedCar = parkingLot.fetch(nullParkingTicket);

        // THEN
        assertNull(fetchedCar);
    }

    @Test
    public void should_return_null_when_fetch_given_an_already_used_ticket() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car();
        ParkingTicket parkingTicket = parkingLot.park(car);
        parkingLot.fetch(parkingTicket);

        // WHEN
        Car fetchedCar = parkingLot.fetch(parkingTicket);

        // THEN
        assertNull(fetchedCar);
    }

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_a_parking_lot_with_capacity_1_and_a_parked_car() {
        // GIVEN
        int capacity = 1;
        ParkingLot parkingLot = new ParkingLot(capacity);
        parkingLot.park(new Car());
        Car anotherCar = new Car();

        // WHEN
        Executable executable = () -> {
            parkingLot.park(anotherCar);
        };

        // THEN
        Exception exception = assertThrows(FullParkingException.class, executable);
        assertEquals(FULL_PARKING_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_return_2_when_get_number_of_parked_cars_given_two_parked_cars() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.park(new Car());
        parkingLot.park(new Car());
        int expectedParkedCars = 2;

        // WHEN
        int carsParked = parkingLot.getNumberOfParkedCars();

        // THEN
        assertEquals(expectedParkedCars, carsParked);
    }

    @Test
    public void should_return_true_when_is_ticket_in_used_ticket_list_given_used_ticket() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        ParkingTicket parkingTicket = parkingLot.park(new Car());
        parkingLot.fetch(parkingTicket);
        boolean expectedResult = true;

        // WHEN
        boolean isTicketInUsedTicketList = parkingLot.isTicketInUsedTicketList(parkingTicket);

        // THEN
        assertEquals(expectedResult, isTicketInUsedTicketList);
    }

    @Test
    public void should_return_false_when_is_ticket_in_used_ticket_list_given_unassociated_ticket() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        ParkingTicket unassociatedTicket = new ParkingTicket();
        boolean expectedResult = false;

        // WHEN
        boolean isTicketInUsedTicketList = parkingLot.isTicketInUsedTicketList(unassociatedTicket);

        // THEN
        assertEquals(expectedResult, isTicketInUsedTicketList);
    }

    @Test
    public void should_return_false_when_is_ticket_in_used_ticket_list_given_null_ticket() {
        // GIVEN
        ParkingLot parkingLot = new ParkingLot();
        ParkingTicket nullTicket = null;
        boolean expectedResult = false;

        // WHEN
        boolean isTicketInUsedTicketList = parkingLot.isTicketInUsedTicketList(nullTicket);

        // THEN
        assertEquals(expectedResult, isTicketInUsedTicketList);
    }
}