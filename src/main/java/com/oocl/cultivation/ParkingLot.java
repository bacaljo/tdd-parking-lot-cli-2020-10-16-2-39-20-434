package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private static final int DEFAULT_CAPACITY = 10;

    private final Map<ParkingTicket, Car> parkingTicketCarMap;
    private final List<ParkingTicket> usedParkingTicketList;
    private final int capacity;

    public ParkingLot() {
        this(DEFAULT_CAPACITY);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        parkingTicketCarMap = new HashMap<>();
        usedParkingTicketList = new ArrayList<>();
    }

    public ParkingTicket park(Car car) {
        if (parkingTicketCarMap.size() == capacity) {
            throw new FullParkingException();
        }

        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicketCarMap.put(parkingTicket, car);

        return parkingTicket;
    }

    public Car fetch(ParkingTicket parkingTicket) {
        Car car = parkingTicketCarMap.get(parkingTicket);
        parkingTicketCarMap.remove(parkingTicket, car);
        usedParkingTicketList.add(parkingTicket);

        return car;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfParkedCars() {
        return parkingTicketCarMap.size();
    }

    public boolean isTicketInUsedTicketList(ParkingTicket parkingTicket) {
        return usedParkingTicketList.contains(parkingTicket);
    }

    public int countEmptyPositions() {
        return capacity - getNumberOfParkedCars();
    }

    public double getAvailablePositionRate() {
        return 0;
    }
}
