package com.oocl.cultivation.parkinglotemployee;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingLotEmployee;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.InvalidParkingException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.oocl.cultivation.TestHelper.FIRST_ELEMENT;
import static com.oocl.cultivation.TestHelper.FULL_PARKING_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.INVALID_PARKING_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.MISSING_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.SECOND_ELEMENT;
import static com.oocl.cultivation.TestHelper.THIRD_ELEMENT;
import static com.oocl.cultivation.TestHelper.UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.generateParkingLotWithDummyCars;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SmartParkingBoyTest {

    @Test
    public void should_return_a_parking_ticket_when_park_given_a_car() {
        // GIVEN
        Car car = new Car();
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(new ParkingLot()));

        // WHEN
        ParkingTicket parkingTicket = smartParkingBoy.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_return_the_correct_car_when_fetch_given_the_correct_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(new ParkingLot()));
        ParkingTicket parkingTicket = smartParkingBoy.park(car);

        // WHEN
        Car fetchedCar = smartParkingBoy.fetch(parkingTicket);

        // THEN
        assertSame(car, fetchedCar);
    }

    @Test
    public void should_return_the_correct_cars_when_fetch_given_two_tickets() {
        // GIVEN
        Car car1 = new Car();
        Car car2 = new Car();

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(new ParkingLot()));

        ParkingTicket parkingTicket1 = smartParkingBoy.park(car1);
        ParkingTicket parkingTicket2 = smartParkingBoy.park(car2);

        // WHEN
        Car fetchedCar1 = smartParkingBoy.fetch(parkingTicket1);
        Car fetchedCar2 = smartParkingBoy.fetch(parkingTicket2);

        // THEN
        assertSame(car1, fetchedCar1);
        assertSame(car2, fetchedCar2);
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_unassociated_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(new ParkingLot()));
        smartParkingBoy.park(car);

        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.fetch(fakeParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_missing_parking_ticket_exception_when_fetch_given_null_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(new ParkingLot()));
        smartParkingBoy.park(car);

        ParkingTicket nullParkingTicket = null;

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.fetch(nullParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(MissingParkingTicketException.class, executable);
        assertEquals(MISSING_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_an_already_used_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(new ParkingLot()));
        ParkingTicket parkingTicket = smartParkingBoy.park(car);
        smartParkingBoy.fetch(parkingTicket);

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.fetch(parkingTicket);
        };

        // THEN
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_invalid_parking_exception_with_message_when_fetch_given_an_already_parked_car() {
        // GIVEN
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.park(car);
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(new ParkingLot(), parkingLot));

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.park(car);
        };

        // THEN
        Exception exception = assertThrows(InvalidParkingException.class, executable);
        assertEquals(INVALID_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_a_parking_lot_with_capacity_1_and_a_parked_car() {
        // GIVEN
        int capacity = 1;
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(new ParkingLot(capacity)));
        smartParkingBoy.park(new Car());
        Car anotherCar = new Car();

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.park(anotherCar);
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

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2));
        Car car = new Car();

        int parkingLot1ExpectedSize = 2;
        int parkingLot2ExpectedSize = 1;

        // WHEN
        smartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, smartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, smartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_three_parking_lots_with_the_first_one_being_full() {
        // GIVEN
        int capacity = 1;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = new ParkingLot(capacity);
        ParkingLot parkingLot3 = new ParkingLot(capacity);

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        int parkingLot1ExpectedSize = 1;
        int parkingLot2ExpectedSize = 1;
        int parkingLot3ExpectedSize = 0;

        // WHEN
        smartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, smartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, smartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot3ExpectedSize, smartParkingBoy.getParkingLotList().get(THIRD_ELEMENT).getNumberOfParkedCars());
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

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2, parkingLot3));

        // WHEN
        Car fetchedCar = smartParkingBoy.fetch(parkingTicket);

        // THEN
        assertSame(car, fetchedCar);
    }

    @Test
    public void should_throw_missing_parking_ticket_exception_when_fetch_given_two_parking_lots_and_a_null_ticket() {
        // GIVEN
        int capacity = 2;

        ParkingLot parkingLot1 = new ParkingLot(capacity);
        ParkingLot parkingLot2 = new ParkingLot(capacity);

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2));
        ParkingTicket nullParkingTicket = null;

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.fetch(nullParkingTicket);
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

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2));
        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.fetch(fakeParkingTicket);
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

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2));
        Car car = new Car();
        ParkingTicket parkingTicket = smartParkingBoy.park(car);
        smartParkingBoy.fetch(parkingTicket);

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.fetch(parkingTicket);
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

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        // WHEN
        Executable executable = () -> {
            smartParkingBoy.park(car);
        };

        // THEN
        Exception exception = assertThrows(FullParkingException.class, executable);
        assertEquals(FULL_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_park_car_in_first_parking_lot_when_park_given_two_parking_lots_where_first_has_more_empty_positions() {
        // GIVEN
        int capacity = 3;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 2);
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2));
        Car car = new Car();

        int parkingLot1ExpectedSize = 2;
        int parkingLot2ExpectedSize = 2;

        // WHEN
        smartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, smartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, smartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_two_parking_lots_where_second_has_more_empty_positions() {
        // GIVEN
        int capacity = 3;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 2);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2));
        Car car = new Car();

        int parkingLot1ExpectedSize = 2;
        int parkingLot2ExpectedSize = 2;

        // WHEN
        smartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, smartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, smartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_three_parking_lots_where_first_is_full_and_second_and_third_have_the_same_empty_positions() {
        // GIVEN
        int capacity = 2;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 2);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingLotEmployee smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        int parkingLot1ExpectedSize = 2;
        int parkingLot2ExpectedSize = 2;
        int parkingLot3ExpectedSize = 1;

        // WHEN
        smartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, smartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, smartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot3ExpectedSize, smartParkingBoy.getParkingLotList().get(THIRD_ELEMENT).getNumberOfParkedCars());
    }
}
