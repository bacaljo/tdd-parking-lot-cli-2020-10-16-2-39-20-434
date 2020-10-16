package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final Map<ParkingTicket, Car> parkingTicketCarMap;

    public ParkingLot() {
        parkingTicketCarMap = new HashMap<>();
    }

    public ParkingTicket park(Car car) {
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicketCarMap.put(parkingTicket, car);

        return parkingTicket;
    }

    public Car fetch(ParkingTicket parkingTicket) {
        Car car = parkingTicketCarMap.get(parkingTicket);
        parkingTicketCarMap.remove(parkingTicket, car);

        return car;
    }

    public int getCapacity() {
        return 0;
    }
}
