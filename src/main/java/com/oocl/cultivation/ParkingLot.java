package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private static final int DEFAULT_CAPACITY = 10;

    private final Map<ParkingTicket, Car> parkingTicketCarMap;
    private final int capacity;

    public ParkingLot() {
        parkingTicketCarMap = new HashMap<>();
        capacity = DEFAULT_CAPACITY;
    }

    public ParkingLot(int capacity) {
        this.capacity = 0;
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
        return capacity;
    }
}
