package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingLotEmployee;
import com.oocl.cultivation.exception.FullParkingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.oocl.cultivation.TestHelper.FIRST_ELEMENT;
import static com.oocl.cultivation.TestHelper.FULL_PARKING_EXCEPTION_MESSAGE;
import static com.oocl.cultivation.TestHelper.SECOND_ELEMENT;
import static com.oocl.cultivation.TestHelper.THIRD_ELEMENT;
import static com.oocl.cultivation.TestHelper.generateParkingLotWithDummyCars;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SuperSmartParkingBoyTest {

    @Test
    public void should_throw_a_full_parking_exception_when_park_given_three_full_parking_lots() {
        // GIVEN
        int capacity = 1;

        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(capacity, 1);
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(capacity, 1);

        ParkingLotEmployee superSmartParkingBoy = new SuperSmartParkingBoy(asList(parkingLot1, parkingLot2, parkingLot3));
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
    public void should_throw_a_full_parking_exception_when_park_given_a_parking_lot_with_capacity_1_and_a_parked_car() {
        // GIVEN
        int capacity = 1;
        ParkingLotEmployee superSmartParkingBoy = new SuperSmartParkingBoy(asList(new ParkingLot(capacity)));
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
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(15, 3);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(20, 5);

        ParkingLotEmployee superSmartParkingBoy = new SuperSmartParkingBoy(asList(parkingLot1, parkingLot2));
        Car car = new Car();

        int parkingLot1ExpectedSize = 4;
        int parkingLot2ExpectedSize = 5;

        // WHEN
        superSmartParkingBoy.park(car);

        // THEN
        assertEquals(parkingLot1ExpectedSize, superSmartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(parkingLot2ExpectedSize, superSmartParkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_park_car_in_second_parking_lot_when_park_given_two_parking_lots_where_second_has_more_larger_available_position_rate() {
        // GIVEN
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(20, 5);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(15, 3);

        ParkingLotEmployee superSmartParkingBoy = new SuperSmartParkingBoy(asList(parkingLot1, parkingLot2));
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
    public void should_park_car_in_second_parking_lot_when_park_given_three_parking_lots_where_second_and_third_both_have_the_same_higher_available_position_rate() {
        // GIVEN
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(20, 5);
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(15, 3);
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(25, 5);

        ParkingLotEmployee superSmartParkingBoy = new SuperSmartParkingBoy(asList(parkingLot1, parkingLot2, parkingLot3));
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
