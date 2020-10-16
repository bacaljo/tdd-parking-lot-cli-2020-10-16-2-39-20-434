package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.oocl.cultivation.TestConstants.FIRST_ELEMENT;
import static com.oocl.cultivation.TestConstants.FULL_PARKING_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestConstants.MISSING_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestConstants.UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SuperSuperSmartParkingBoyTest {

    @Test
    public void should_return_a_parking_ticket_when_park_given_a_car() {
        // GIVEN
        Car car = new Car();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot());

        // WHEN
        ParkingTicket parkingTicket = superSmartParkingBoy.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_return_the_correct_car_when_fetch_given_the_correct_ticket() {
        // GIVEN
        Car car = new Car();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot());
        ParkingTicket parkingTicket = superSmartParkingBoy.park(car);

        // WHEN
        Car fetchedCar = superSmartParkingBoy.fetch(parkingTicket);

        // THEN
        assertSame(fetchedCar, car);
    }

    @Test
    public void should_return_the_correct_cars_when_fetch_given_two_tickets() {
        // GIVEN
        Car car1 = new Car();
        Car car2 = new Car();

        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot());

        ParkingTicket parkingTicket1 = superSmartParkingBoy.park(car1);
        ParkingTicket parkingTicket2 = superSmartParkingBoy.park(car2);

        // WHEN
        Car fetchedCar1 = superSmartParkingBoy.fetch(parkingTicket1);
        Car fetchedCar2 = superSmartParkingBoy.fetch(parkingTicket2);

        // THEN
        assertSame(fetchedCar1, car1);
        assertSame(fetchedCar2, car2);
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_unassociated_ticket() {
        // GIVEN
        Car car = new Car();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot());
        superSmartParkingBoy.park(car);

        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Executable executable = () -> {
            superSmartParkingBoy.fetch(fakeParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_missing_parking_ticket_exception_when_fetch_given_null_ticket() {
        // GIVEN
        Car car = new Car();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot());
        superSmartParkingBoy.park(car);

        ParkingTicket nullParkingTicket = null;

        // WHEN
        Executable executable = () -> {
            superSmartParkingBoy.fetch(nullParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(MissingParkingTicketException.class, executable);
        assertEquals(MISSING_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_an_already_used_ticket() {
        // GIVEN
        Car car = new Car();
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot());
        ParkingTicket parkingTicket = superSmartParkingBoy.park(car);
        superSmartParkingBoy.fetch(parkingTicket);

        // WHEN
        Executable executable = () -> {
            superSmartParkingBoy.fetch(parkingTicket);
        };

        // THEN
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_a_parking_lot_with_capacity_1_and_a_parked_car() {
        // GIVEN
        int capacity = 1;
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(new ParkingLot(capacity));
        superSmartParkingBoy.park(new Car());
        Car anotherCar = new Car();

        // WHEN
        Executable executable = () -> {
            superSmartParkingBoy.park(anotherCar);
        };

        // THEN
        Exception exception = assertThrows(FullParkingException.class, executable);
        assertEquals(FULL_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_park_car_in_first_parking_lot_when_park_given_two_parking_lots_where_first_has_more_larger_available_position_rate() {
        // GIVEN
        int parkingLot1ExpectedSize = 4;
        int capacity1 = 15;
        int capacity2 = 20;

        ParkingLot parkingLot1 = new ParkingLot(capacity1);
        ParkingLot parkingLot2 = new ParkingLot(capacity2);

        parkingLot1.park(new Car());
        parkingLot1.park(new Car());
        parkingLot1.park(new Car());

        parkingLot2.park(new Car());
        parkingLot2.park(new Car());
        parkingLot2.park(new Car());
        parkingLot2.park(new Car());
        parkingLot2.park(new Car());

        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(asList(parkingLot1, parkingLot2));
        Car car = new Car();

        // WHEN
        superSmartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, superSmartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
    }
}
