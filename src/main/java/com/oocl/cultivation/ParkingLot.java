package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private static final int DEFAULT_CAPACITY = 10;

    private final Map<ParkingTicket, Car> parkingTicketCarMap;
    private final int capacity;

    public ParkingLot() {
        this(DEFAULT_CAPACITY);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        parkingTicketCarMap = new HashMap<>();
    }

    public ParkingTicket park(Car car) {
        validateForFullParking();
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicketCarMap.put(parkingTicket, car);

        return parkingTicket;
    }

    private void validateForFullParking() {
        if (isFull()) {
            throw new FullParkingException();
        }
    }

    public Car fetch(ParkingTicket parkingTicket) {
        Car car = parkingTicketCarMap.get(parkingTicket);
        parkingTicketCarMap.remove(parkingTicket, car);

        return car;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfParkedCars() {
        return parkingTicketCarMap.size();
    }

    public int countEmptyPositions() {
        return capacity - getNumberOfParkedCars();
    }

    public double getAvailablePositionRate() {
        return (double) countEmptyPositions() / capacity;
    }

    public boolean isFull() {
        return getNumberOfParkedCars() == capacity;
    }

    public boolean containsParkedCar(Car car) {
        return parkingTicketCarMap.containsValue(car);
    }

    public boolean containsParkingTicket(ParkingTicket parkingTicket) {
        return parkingTicketCarMap.containsKey(parkingTicket);
    }
}
