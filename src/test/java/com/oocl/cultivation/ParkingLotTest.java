package com.oocl.cultivation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkingLotTest {
    @Test
    public void should_have_a_default_capacity_of_10_when_parking_lot_is_initialized_given_no_capacity() {
        // GIVEN
        ParkingLot parkingLot;
        int defaultCapacity = 10;

        // WHEN
        parkingLot = new ParkingLot();

        // THEN
        assertEquals(defaultCapacity, parkingLot.getCapacity());
    }
}