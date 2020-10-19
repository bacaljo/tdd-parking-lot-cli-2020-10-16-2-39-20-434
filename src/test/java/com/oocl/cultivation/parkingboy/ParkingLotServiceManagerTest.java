package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.Test;

import static com.oocl.cultivation.ParkingBoyType.PARKING_BOY;
import static com.oocl.cultivation.ParkingBoyType.SMART_PARKING_BOY;
import static com.oocl.cultivation.ParkingBoyType.SUPER_SMART_PARKING_BOY;
import static com.oocl.cultivation.TestHelper.FIRST_ELEMENT;
import static com.oocl.cultivation.TestHelper.SECOND_ELEMENT;
import static com.oocl.cultivation.TestHelper.generateParkingBoy;
import static com.oocl.cultivation.TestHelper.generateParkingLotWithDummyCars;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

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
    public void should_get_2_parking_boys_when_enlist_parking_boys_given_2_parking_boys() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingBoy parkingBoy1 = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));
        ParkingBoy parkingBoy2 = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));

        int parkingBoysCount = 2;

        // when
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy1, parkingBoy2));

        // then
        assertEquals(parkingBoysCount, parkingLotServiceManager.getManagementList().size());
    }

    @Test
    public void should_park_in_second_parking_lot_and_return_ticket_when_delegate_park_given_a_managed_parking_boy_with_four_varying_parking_lots() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(2, 2); // Full
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(5, 2); // Expected, because parking boy parks sequentially
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(20, 10); // Not expected; Can only happen if parked by a smart parking boy
        ParkingLot parkingLot4 = generateParkingLotWithDummyCars(8, 3); // Not expected; Can only happen if parked by a super smart parking boy
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3, parkingLot4));
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy));
        Car car = new Car();

        int expectedParkedCars1 = 2;
        int expectedParkedCars2 = 3;
        int expectedParkedCars3 = 10;
        int expectedParkedCars4 = 3;

        // when
        ParkingTicket parkingTicket = parkingLotServiceManager.delegatePark(car);

        // then
        assertNotNull(parkingTicket);
        assertEquals(expectedParkedCars1, parkingLot1.getNumberOfParkedCars());
        assertEquals(expectedParkedCars2, parkingLot2.getNumberOfParkedCars());
        assertEquals(expectedParkedCars3, parkingLot3.getNumberOfParkedCars());
        assertEquals(expectedParkedCars4, parkingLot4.getNumberOfParkedCars());
    }

    @Test
    public void should_park_in_third_parking_lot_and_return_ticket_when_delegate_park_given_a_managed_smart_parking_boy_with_four_varying_parking_lots() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(2, 2); // Full
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(5, 2); // Not expected; Can only happen if parked by a parking boy
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(20, 10); // Expected, because smart parking boy parks in the lot with the most available positions (10)
        ParkingLot parkingLot4 = generateParkingLotWithDummyCars(8, 3); // Not expected; Can only happen if parked by a super smart parking boy
        ParkingBoy parkingBoy = generateParkingBoy(SMART_PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3, parkingLot4));
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy));
        Car car = new Car();

        int expectedParkedCars1 = 2;
        int expectedParkedCars2 = 2;
        int expectedParkedCars3 = 11;
        int expectedParkedCars4 = 3;

        // when
        ParkingTicket parkingTicket = parkingLotServiceManager.delegatePark(car);

        // then
        assertNotNull(parkingTicket);
        assertEquals(expectedParkedCars1, parkingLot1.getNumberOfParkedCars());
        assertEquals(expectedParkedCars2, parkingLot2.getNumberOfParkedCars());
        assertEquals(expectedParkedCars3, parkingLot3.getNumberOfParkedCars());
        assertEquals(expectedParkedCars4, parkingLot4.getNumberOfParkedCars());
    }

    @Test
    public void should_park_in_fourth_parking_lot_and_return_ticket_when_delegate_park_given_a_managed_super_smart_parking_boy_with_four_varying_parking_lots() {
        // given
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(null);
        ParkingLot parkingLot1 = generateParkingLotWithDummyCars(2, 2); // Full
        ParkingLot parkingLot2 = generateParkingLotWithDummyCars(5, 2); // Not expected; Can only happen if parked by a parking boy
        ParkingLot parkingLot3 = generateParkingLotWithDummyCars(20, 10); // Not expected; Can only happen if parked by a smart parking boy
        ParkingLot parkingLot4 = generateParkingLotWithDummyCars(8, 3); // Expected, because super smart parking boy parks in the lot with the largest available rate (.63)
        ParkingBoy parkingBoy = generateParkingBoy(SUPER_SMART_PARKING_BOY, asList(parkingLot1, parkingLot2, parkingLot3, parkingLot4));
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy));
        Car car = new Car();

        int expectedParkedCars1 = 2;
        int expectedParkedCars2 = 2;
        int expectedParkedCars3 = 10;
        int expectedParkedCars4 = 4;

        // when
        ParkingTicket parkingTicket = parkingLotServiceManager.delegatePark(car);

        // then
        assertNotNull(parkingTicket);
        assertEquals(expectedParkedCars1, parkingLot1.getNumberOfParkedCars());
        assertEquals(expectedParkedCars2, parkingLot2.getNumberOfParkedCars());
        assertEquals(expectedParkedCars3, parkingLot3.getNumberOfParkedCars());
        assertEquals(expectedParkedCars4, parkingLot4.getNumberOfParkedCars());
    }

    @Test
    public void should_return_car_when_delegate_fetch_given_a_managed_parking_boy_and_an_associated_ticket() {
        // given
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(new ParkingLot()));
        Car car = new Car();
        ParkingTicket parkingTicket = parkingBoy.park(car);
        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy));

        // when
        Car fetchedCar = parkingLotServiceManager.delegateFetch(parkingTicket);

        // then
        assertSame(fetchedCar, car);
    }

    @Test
    public void should_park_in_second_parking_given_delegate_park_when_parking_lot_of_first_parking_boy_is_already_full() {
        // given
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(
                generateParkingLotWithDummyCars(1, 1),
                new ParkingLot()));
        ParkingBoy smartParkingBoy = generateParkingBoy(SMART_PARKING_BOY, asList(new ParkingLot()));

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy, smartParkingBoy));

        Car car = new Car();

        int expectedParkedCars1 = 1;
        int expectedParkedCars2 = 1;

        int expectedParkedCars3 = 0;

        // when
        ParkingTicket parkingTicket = parkingLotServiceManager.delegatePark(car);

        // then
        assertNotNull(parkingTicket);

        assertEquals(expectedParkedCars1, parkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(expectedParkedCars2, parkingBoy.getParkingLotList().get(SECOND_ELEMENT).getNumberOfParkedCars());

        assertEquals(expectedParkedCars3, smartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
    }

    @Test
    public void should_delegate_parking_task_to_second_parking_boy_given_delegate_park_when_parking_lot_of_first_parking_boy_is_already_full() {
        // given
        ParkingBoy parkingBoy = generateParkingBoy(PARKING_BOY, asList(generateParkingLotWithDummyCars(1, 1)));
        ParkingBoy smartParkingBoy = generateParkingBoy(SMART_PARKING_BOY, asList(new ParkingLot()));
        ParkingBoy superSmartParkingBoy = generateParkingBoy(SUPER_SMART_PARKING_BOY, asList(new ParkingLot()));

        ParkingLotServiceManager parkingLotServiceManager = new ParkingLotServiceManager(asList(new ParkingLot()));
        parkingLotServiceManager.enlistParkingBoys(asList(parkingBoy, smartParkingBoy, superSmartParkingBoy));

        Car car = new Car();

        int expectedParkedCars1 = 1;
        int expectedParkedCars2 = 1;
        int expectedParkedCars3 = 0;

        // when
        ParkingTicket parkingTicket = parkingLotServiceManager.delegatePark(car);

        // then
        assertNotNull(parkingTicket);
        assertEquals(expectedParkedCars1, parkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(expectedParkedCars2, smartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
        assertEquals(expectedParkedCars3, superSmartParkingBoy.getParkingLotList().get(FIRST_ELEMENT).getNumberOfParkedCars());
    }
}
