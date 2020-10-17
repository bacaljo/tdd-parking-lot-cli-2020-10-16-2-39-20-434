package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.oocl.cultivation.TestHelper.FIRST_ELEMENT;
import static com.oocl.cultivation.TestHelper.FULL_PARKING_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.MISSING_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.SECOND_ELEMENT;
import static com.oocl.cultivation.TestHelper.THIRD_ELEMENT;
import static com.oocl.cultivation.TestHelper.UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.generateParkingLotWithDummyCars;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingBoyTest {

    @Test
    public void should_return_a_parking_ticket_when_park_given_a_car() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());

        // WHEN
        ParkingTicket parkingTicket = parkingBoy.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_return_the_correct_car_when_fetch_given_the_correct_ticket() {
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
    public void should_return_the_correct_cars_when_fetch_given_two_tickets() {
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
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_unassociated_ticket() {
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
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_missing_parking_ticket_exception_when_fetch_given_null_ticket() {
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
        Exception exception = assertThrows(MissingParkingTicketException.class, executable);
        assertEquals(MISSING_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_an_already_used_ticket() {
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
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_a_parking_lot_with_capacity_1_and_a_parked_car() {
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
        assertEquals(FULL_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_park_car_in_first_parking_lot_when_park_given_two_parking_lots_that_are_still_not_full() {
        // GIVEN
        int capacity = 3;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingBoy parkingBoy = new ParkingBoy(parkingLot1, parkingLot2);
        Car car = new Car();

        int parkingLot1ExpectedSize = 2;
        int parkingLot2ExpectedSize = 1;

        // WHEN
        parkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, parkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, parkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_three_parking_lots_with_the_first_one_being_full() {
        // GIVEN
        int capacity = 1;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = new ParkingLot(capacity);
        ParkingLot parkingLot3 = new ParkingLot(capacity);

        ParkingBoy parkingBoy = new ParkingBoy(parkingLot1, parkingLot2, parkingLot3);
        Car car = new Car();

        int parkingLot1ExpectedSize = 1;
        int parkingLot2ExpectedSize = 1;
        int parkingLot3ExpectedSize = 0;

        // WHEN
        parkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, parkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, parkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot3ExpectedSize, parkingBoy.getParkingLotList().get(THIRD_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_return_car_when_fetch_given_car_is_parked_in_the_third_parking_lot() {
        // GIVEN
        int capacity = 2;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot3 = new ParkingLot(capacity);

        Car car = new Car();
        ParkingTicket parkingTicket = parkingLot3.park(car);

        ParkingBoy parkingBoy = new ParkingBoy(parkingLot1, parkingLot2, parkingLot3);

        // WHEN
        Car fetchedCar = parkingBoy.fetch(parkingTicket);

        // THEN
        assertSame(car, fetchedCar);
    }

    @Test
    public void should_throw_missing_parking_ticket_exception_when_fetch_given_two_parking_lots_and_a_null_ticket() {
        // GIVEN
        int capacity = 2;

        ParkingLot parkingLot1 = new ParkingLot(capacity);
        ParkingLot parkingLot2 = new ParkingLot(capacity);

        ParkingBoy parkingBoy = new ParkingBoy(parkingLot1, parkingLot2);
        ParkingTicket nullParkingTicket = null;

        // WHEN
        Executable executable = () -> {
            parkingBoy.fetch(nullParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(MissingParkingTicketException.class, executable);
        assertEquals(MISSING_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_two_parking_lots_with_cars_and_an_unassociated_ticket() {
        // GIVEN
        int capacity = 2;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingBoy parkingBoy = new ParkingBoy(parkingLot1, parkingLot2);
        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Executable executable = () -> {
            parkingBoy.fetch(fakeParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_two_parking_lots_and_an_already_used_ticket() {
        // GIVEN
        int capacity = 2;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingBoy parkingBoy = new ParkingBoy(parkingLot1, parkingLot2);
        Car car = new Car();
        ParkingTicket parkingTicket = parkingBoy.park(car);
        parkingBoy.fetch(parkingTicket);

        // WHEN
        Executable executable = () -> {
            parkingBoy.fetch(parkingTicket);
        };

        // THEN
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_three_full_parking_lots() {
        // GIVEN
        int capacity = 1;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingBoy parkingBoy = new ParkingBoy(parkingLot1, parkingLot2, parkingLot3);
        Car car = new Car();

        // WHEN
        Executable executable = () -> {
            parkingBoy.park(car);
        };

        // THEN
        Exception exception = assertThrows(FullParkingException.class, executable);
        assertEquals(FULL_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }
}
