package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.InvalidParkingException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.oocl.cultivation.ParkingBoyType.PARKING_BOY;
import static com.oocl.cultivation.ParkingBoyType.SMART_PARKING_BOY;
import static com.oocl.cultivation.ParkingBoyType.SUPER_SMART_PARKING_BOY;
import static com.oocl.cultivation.TestHelper.FIRST_ELEMENT;
import static com.oocl.cultivation.TestHelper.FULL_PARKING_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.INVALID_PARKING_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.MISSING_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.SECOND_ELEMENT;
import static com.oocl.cultivation.TestHelper.THIRD_ELEMENT;
import static com.oocl.cultivation.TestHelper.UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.generateParkingBoy;
import static com.oocl.cultivation.TestHelper.generateParkingLotWithDummyCars;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingBoyTest {

    @Test
    public void should_return_a_parking_ticket_when_park_given_a_car() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));

        // WHEN
        ParkingTicket parkingTicket = parkingBoy.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_return_the_correct_car_when_fetch_given_the_correct_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));
        ParkingTicket parkingTicket = parkingBoy.park(car);

        // WHEN
        Car fetchedCar = parkingBoy.fetch(parkingTicket);

        // THEN
        assertSame(car, fetchedCar);
    }

    @Test
    public void should_return_the_correct_cars_when_fetch_given_two_tickets() {
        // GIVEN
        Car car1 = new Car();
        Car car2 = new Car();

        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));

        ParkingTicket parkingTicket1 = parkingBoy.park(car1);
        ParkingTicket parkingTicket2 = parkingBoy.park(car2);

        // WHEN
        Car fetchedCar1 = parkingBoy.fetch(parkingTicket1);
        Car fetchedCar2 = parkingBoy.fetch(parkingTicket2);

        // THEN
        assertSame(car1, fetchedCar1);
        assertSame(car2, fetchedCar2);
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_unassociated_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));
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
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));
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
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));
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
    public void should_throw_invalid_parking_exception_with_message_when_park_given_an_already_parked_car() {
        // GIVEN
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.park(car);
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot(), parkingLot));

        // WHEN
        Executable executable = () -> {
            parkingBoy.park(car);
        };

        // THEN
        Exception exception = assertThrows(InvalidParkingException.class, executable);
        assertEquals(INVALID_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_park_car_in_first_parking_lot_when_park_given_two_parking_lots_that_are_still_not_full() {
        // GIVEN
        int capacity = 3;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(parkingLot1, parkingLot2));
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

        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3));
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

        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3));

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

        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(parkingLot1, parkingLot2));
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

        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(parkingLot1, parkingLot2));
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

        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(parkingLot1, parkingLot2));
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

        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        // WHEN
        Executable executable = () -> {
            parkingBoy.park(car);
        };

        // THEN
        Exception exception = assertThrows(FullParkingException.class, executable);
        assertEquals(FULL_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_a_smart_parking_boy_and_three_full_parking_lots() {
        // GIVEN
        int capacity = 1;
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingBoy smartParkingBoy = generateParkingBoy(SMART_PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3));
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
    public void should_park_in_the_third_parking_lot_when_park_given_a_smart_parking_boy_with_four_varying_parking_lots() {
        // given
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(2, 2); // Full
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(5, 2); // Not expected; Can only happen if parked by a parking boy
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(20, 10); // Expected, because smart parking boy parks in the lot with the most available positions (10)
        ParkingLot parkingLot4 = generateParkingLotWithDummyCars(8, 3); // Not expected; Can only happen if parked by a super smart parking boy
        ParkingBoy parkingBoy = generateParkingBoy(SMART_PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3, parkingLot4));
        Car car = new Car();

        int expectedParkedCars1 = 2;
        int expectedParkedCars2 = 2;
        int expectedParkedCars3 = 11;
        int expectedParkedCars4 = 3;

        // when
        ParkingTicket parkingTicket = parkingBoy.park(car);

        // then
        assertNotNull(parkingTicket);
        assertEquals(expectedParkedCars1, parkingLot1.getNumberOfParkedCars());
        assertEquals(expectedParkedCars2, parkingLot2.getNumberOfParkedCars());
        assertEquals(expectedParkedCars3, parkingLot3.getNumberOfParkedCars());
        assertEquals(expectedParkedCars4, parkingLot4.getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_a_smart_parking_boy_and_three_parking_lots_where_first_is_full_and_second_and_third_have_the_same_empty_positions() {
        // GIVEN
        int capacity = 2;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 2);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingBoy smartParkingBoy = generateParkingBoy(SMART_PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        int parkingLot1ExpectedSize = 2;
        int parkingLot2ExpectedSize = 2;
        int parkingLot3ExpectedSize = 1;

        // WHEN
        smartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, parkingLot1.getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, parkingLot2.getNumberOfParkedCars());
        assertEquals(parkingLot3ExpectedSize, parkingLot3.getNumberOfParkedCars());
    }

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_a_super_smart_parking_boy_and_three_full_parking_lots() {
        // GIVEN
        int capacity = 1;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingBoy superSmartParkingBoy = generateParkingBoy(SUPER_SMART_PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        // WHEN
        Executable executable = () -> {
            superSmartParkingBoy.park(car);
        };

        // THEN
        Exception exception = assertThrows(FullParkingException.class, executable);
        assertEquals(FULL_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_a_super_smart_parking_boy_and_two_parking_lots_where_second_has_more_larger_available_position_rate() {
        // GIVEN
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(20, 5);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(15, 3);

        ParkingBoy superSmartParkingBoy = generateParkingBoy(SUPER_SMART_PARKING_BOY, asList(parkingLot1, parkingLot2));
        Car car = new Car();

        int parkingLot1ExpectedSize = 5;
        int parkingLot2ExpectedSize = 4;

        // WHEN
        superSmartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, superSmartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, superSmartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_a_super_smart_parking_boy_and_three_parking_lots_where_second_and_third_both_have_the_same_higher_available_position_rate() {
        // GIVEN
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(20, 5);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(15, 3);
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(25, 5);

        ParkingBoy superSmartParkingBoy = generateParkingBoy(SUPER_SMART_PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        int parkingLot1ExpectedSize = 5;
        int parkingLot2ExpectedSize = 4;
        int parkingLot3ExpectedSize = 5;

        // WHEN
        superSmartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, superSmartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, superSmartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot3ExpectedSize, superSmartParkingBoy.getParkingLotList().get(THIRD_ELEMENT).getNumberOfParkedCars());
    }
}
