package com.oocl.cultivation.factory;

import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingBoyType;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.strategy.parking.SequentialParkingStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParkingBoyFactoryTest {
    @Test
    public void should_return_a_parking_boy_with_sequential_parking_strategy_and_a_list_of_parking_lots_with_3_elements_when_get_parking_boy_given_parking_boy_and_a_list_of_parking_lots_with_3_elements() {
        // given
        ParkingBoyFactory parkingBoyFactory = new ParkingBoyFactory();
        List<ParkingLot> parkingLotList = asList(
                new ParkingLot(), new ParkingLot(), new ParkingLot());
        ParkingBoyType parkingBoyType = ParkingBoyType.PARKING_BOY;

        int expectedParkingLotCount = 3;

        // when
        ParkingBoy parkingBoy = parkingBoyFactory.getParkingBoy(parkingBoyType, parkingLotList);

        // then
        assertTrue(parkingBoy.getParkingStrategy() instanceof SequentialParkingStrategy);
        assertEquals(expectedParkingLotCount, parkingBoy.getParkingLotList().size());
    }
}