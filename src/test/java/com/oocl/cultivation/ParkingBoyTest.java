package com.oocl.cultivation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParkingBoyTest {
    @Test
    public void should_return_a_parking_ticket_when_parking_boy_park_given_a_car() {
        // GIVEN
        Car car = new Car();
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());

        // WHEN
        ParkingTicket parkingTicket = parkingBoy.park(car);

        // THEN
        assertNotNull(parkingTicket);
    }
}
