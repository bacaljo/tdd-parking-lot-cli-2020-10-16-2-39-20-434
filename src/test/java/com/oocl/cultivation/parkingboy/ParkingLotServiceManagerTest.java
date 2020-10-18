package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.ParkingBoyManagementException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.oocl.cultivation.TestHelper.FIRST_ELEMENT;
import static com.oocl.cultivation.TestHelper.FULL_PARKING_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.MISSING_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.PARKING_BOY_MANAGEMENT_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.SECOND_ELEMENT;
import static com.oocl.cultivation.TestHelper.SEQUENTIAL_PARKING_STRATEGY;
import static com.oocl.cultivation.TestHelper.THIRD_ELEMENT;
import static com.oocl.cultivation.TestHelper.UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.generateParkingLotWithDummyCars;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingLotServiceManagerTest {

    @Test
    public void should_have_a_list_of_parking_lot_when_initialized_given_a_list_of_parking_lot() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));

        int expectedParkingLotCount = 1;

        // when
        int parkingLotCount = parkingLotServiceManager.getParkingLotList().size();

        // then
        assertEquals(expectedParkingLotCount, parkingLotCount);
    }

    @Test
    public void should_get_2_parking_boys_when_manage_parking_boys_given_2_parking_boys() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingBoy parkingBoy1 = new ParkingBoy(SEQUENTIAL_PARKING_STRATEGY, asList(new ParkingLot()));
        ParkingBoy parkingBoy2 = new ParkingBoy(SEQUENTIAL_PARKING_STRATEGY, asList(new ParkingLot()));

        int parkingBoysCount = 2;

        // when
        parkingLotServiceManager.manageParkingBoys(asList(parkingBoy1, parkingBoy2));

        // then
        assertEquals(parkingBoysCount, parkingLotServiceManager.getManagementList().size());
    }

    @Test
    public void should_return_ticket_when_order_parking_boy_to_park_given_a_managed_parking_boy_and_car() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingBoy parkingBoy = new ParkingBoy(SEQUENTIAL_PARKING_STRATEGY, asList(new ParkingLot()));
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
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingBoy parkingBoy = new ParkingBoy(SEQUENTIAL_PARKING_STRATEGY, asList(new ParkingLot()));
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
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingBoy parkingBoy = new ParkingBoy(SEQUENTIAL_PARKING_STRATEGY, asList(new ParkingLot()));
        Car car = new Car();
        ParkingTicket parkingTicket = parkingBoy.park(car);
        parkingLotServiceManager.manageParkingBoys(asList(parkingBoy));

        // when
        Car fetchedCar = parkingLotServiceManager.orderParkingBoyToFetch(parkingBoy, parkingTicket);

        // then
        assertSame(car, fetchedCar);
    }

    @Test
    public void should_throw_an_error_when_order_parking_boy_to_fetch_given_an_unmanaged_parking_boy() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingBoy parkingBoy = new ParkingBoy(SEQUENTIAL_PARKING_STRATEGY, asList(new ParkingLot()));
        Car car = new Car();
        ParkingTicket parkingTicket = parkingBoy.park(car);

        // when
        Executable executable = () -> {
            parkingLotServiceManager.orderParkingBoyToFetch(parkingBoy, parkingTicket);
        };

        // then
        Exception exception = assertThrows(ParkingBoyManagementException.class, executable);
        assertEquals(PARKING_BOY_MANAGEMENT_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_return_a_parking_ticket_when_park_given_a_car() {
        // GIVEN
        Car car = new Car();
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));

        // WHEN
        ParkingTicket parkingTicket = parkingLotServiceManager.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }

    @Test
    public void should_return_the_correct_car_when_fetch_given_the_correct_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));
        ParkingTicket parkingTicket = parkingLotServiceManager.park(car);

        // WHEN
        Car fetchedCar = parkingLotServiceManager.fetch(parkingTicket);

        // THEN
        assertSame(car, fetchedCar);
    }

    @Test
    public void should_return_the_correct_cars_when_fetch_given_two_tickets() {
        // GIVEN
        Car car1 = new Car();
        Car car2 = new Car();

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));

        ParkingTicket parkingTicket1 = parkingLotServiceManager.park(car1);
        ParkingTicket parkingTicket2 = parkingLotServiceManager.park(car2);

        // WHEN
        Car fetchedCar1 = parkingLotServiceManager.fetch(parkingTicket1);
        Car fetchedCar2 = parkingLotServiceManager.fetch(parkingTicket2);

        // THEN
        assertSame(car1, fetchedCar1);
        assertSame(car2, fetchedCar2);
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_unassociated_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));
        parkingLotServiceManager.park(car);

        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Executable executable = () -> {
            parkingLotServiceManager.fetch(fakeParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_missing_parking_ticket_exception_when_fetch_given_null_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));
        parkingLotServiceManager.park(car);

        ParkingTicket nullParkingTicket = null;

        // WHEN
        Executable executable = () -> {
            parkingLotServiceManager.fetch(nullParkingTicket);
        };

        // THEN
        Exception exception = assertThrows(MissingParkingTicketException.class, executable);
        assertEquals(MISSING_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_unrecognized_parking_ticket_exception_with_message_when_fetch_given_an_already_used_ticket() {
        // GIVEN
        Car car = new Car();
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));
        ParkingTicket parkingTicket = parkingLotServiceManager.park(car);
        parkingLotServiceManager.fetch(parkingTicket);

        // WHEN
        Executable executable = () -> {
            parkingLotServiceManager.fetch(parkingTicket);
        };

        // THEN
        Exception exception = assertThrows(UnrecognizedParkingTicketException.class, executable);
        assertEquals(UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_a_parking_lot_with_capacity_1_and_a_parked_car() {
        // GIVEN
        int capacity = 1;
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot(capacity)));
        parkingLotServiceManager.park(new Car());
        Car anotherCar = new Car();

        // WHEN
        Executable executable = () -> {
            parkingLotServiceManager.park(anotherCar);
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

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(parkingLot1, parkingLot2));
        Car car = new Car();

        int parkingLot1ExpectedSize = 2;
        int parkingLot2ExpectedSize = 1;

        // WHEN
        parkingLotServiceManager.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, parkingLotServiceManager.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, parkingLotServiceManager.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_three_parking_lots_with_the_first_one_being_full() {
        // GIVEN
        int capacity = 1;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = new ParkingLot(capacity);
        ParkingLot parkingLot3 = new ParkingLot(capacity);

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        int parkingLot1ExpectedSize = 1;
        int parkingLot2ExpectedSize = 1;
        int parkingLot3ExpectedSize = 0;

        // WHEN
        parkingLotServiceManager.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, parkingLotServiceManager.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, parkingLotServiceManager.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot3ExpectedSize, parkingLotServiceManager.getParkingLotList().get(THIRD_ELEMENT).getNumberOfParkedCars());
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

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(parkingLot1, parkingLot2, parkingLot3));

        // WHEN
        Car fetchedCar = parkingLotServiceManager.fetch(parkingTicket);

        // THEN
        assertSame(car, fetchedCar);
    }

    @Test
    public void should_throw_missing_parking_ticket_exception_when_fetch_given_two_parking_lots_and_a_null_ticket() {
        // GIVEN
        int capacity = 2;

        ParkingLot parkingLot1 = new ParkingLot(capacity);
        ParkingLot parkingLot2 = new ParkingLot(capacity);

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(parkingLot1, parkingLot2));
        ParkingTicket nullParkingTicket = null;

        // WHEN
        Executable executable = () -> {
            parkingLotServiceManager.fetch(nullParkingTicket);
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

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(parkingLot1, parkingLot2));
        ParkingTicket fakeParkingTicket = new ParkingTicket();

        // WHEN
        Executable executable = () -> {
            parkingLotServiceManager.fetch(fakeParkingTicket);
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

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(parkingLot1, parkingLot2));
        Car car = new Car();
        ParkingTicket parkingTicket = parkingLotServiceManager.park(car);
        parkingLotServiceManager.fetch(parkingTicket);

        // WHEN
        Executable executable = () -> {
            parkingLotServiceManager.fetch(parkingTicket);
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

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(parkingLot1, parkingLot2, parkingLot3));
        Car car = new Car();

        // WHEN
        Executable executable = () -> {
            parkingLotServiceManager.park(car);
        };

        // THEN
        Exception exception = assertThrows(FullParkingException.class, executable);
        assertEquals(FULL_PARKING_EXCEPTION_MESSAGE, exception.getMessage());
    }
}
