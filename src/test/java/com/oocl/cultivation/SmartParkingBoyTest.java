package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import com.oocl.cultivation.parkingboy.SmartParkingBoy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SmartParkingBoyTest {

    private static final String UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE = "Unrecognized parking ticket.";
    private static final String MISSING_PARKING_TICKET_EXCEPTION_MESSAGE = "Please provide your parking ticket.";
    private static final String FULL_PARKING_EXCEPTION_MESSAGE = "Not enough position.";

    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;

    @Test
    public void should_return_a_parking_ticket_when_park_given_a_car() {
        // GIVEN
        Car car = new Car();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot());

        // WHEN
        ParkingTicket parkingTicket = smartParkingBoy.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_return_the_correct_car_when_fetch_given_the_correct_ticket() {
        // GIVEN
        Car car = new Car();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot());
        ParkingTicket parkingTicket = smartParkingBoy.park(car);

        // WHEN
        Car fetchedCar = smartParkingBoy.fetch(parkingTicket);

        // THEN
        assertSame(fetchedCar, car);
    }

    @Test
    public void should_return_the_correct_cars_when_fetch_given_two_tickets() {
        // GIVEN
        Car car1 = new Car();
        Car car2 = new Car();

        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot());

        ParkingTicket parkingTicket1 = smartParkingBoy.park(car1);
        ParkingTicket parkingTicket2 = smartParkingBoy.park(car2);

        // WHEN
        Car fetchedCar1 = smartParkingBoy.fetch(parkingTicket1);
        Car fetchedCar2 = smartParkingBoy.fetch(parkingTicket2);

        // THEN
        assertSame(fetchedCar1, car1);
        assertSame(fetchedCar2, car2);
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_unassociated_ticket() {
        // GIVEN
        Car car = new Car();
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot());
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
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot());
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
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot());
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
    public void should_throw_a_full_parking_exception_when_park_given_a_parking_lot_with_capacity_1_and_a_parked_car() {
        // GIVEN
        int capacity = 1;
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(new ParkingLot(capacity));
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
    public void should_park_car_in_first_parking_lot_when_park_given_two_parking_lots_where_first_has_more_empty_positions() {
        // GIVEN
        int parkingLot1ExpectedSize = 2;
        int capacity = 3;

        ParkingLot parkingLot1 = new ParkingLot(capacity);
        ParkingLot parkingLot2 = new ParkingLot(capacity);
        parkingLot1.park(new Car());
        parkingLot2.park(new Car());
        parkingLot2.park(new Car());
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2));
        Car car = new Car();

        // WHEN
        smartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, smartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_two_parking_lots_where_second_has_more_empty_positions() {
        // GIVEN
        int parkingLot2ExpectedSize = 2;
        int capacity = 3;

        ParkingLot parkingLot1 = new ParkingLot(capacity);
        ParkingLot parkingLot2 = new ParkingLot(capacity);
        parkingLot1.park(new Car());
        parkingLot1.park(new Car());
        parkingLot2.park(new Car());
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(asList(parkingLot1, parkingLot2));
        Car car = new Car();

        // WHEN
        smartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot2ExpectedSize, smartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
    }
}
