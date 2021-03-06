package com.oocl.cultivation;

import com.oocl.cultivation.factory.ParkingBoyFactory;

import java.util.List;

public final class TestHelper {
    public static final String UNRECOGNIZED_PARKING_TICKET_EXCEPTION_MESSAGE = "Unrecognized parking ticket.";
    public static final String MISSING_PARKING_TICKET_EXCEPTION_MESSAGE = "Please provide your parking ticket.";
    public static final String FULL_PARKING_EXCEPTION_MESSAGE = "Not enough position.";
    public static final String INVALID_PARKING_EXCEPTION_MESSAGE = "Car is already parked.";
    public static final String MISSING_CAR_EXCEPTION_MESSAGE = "There is no car to park.";
    public static final int FIRST_ELEMENT = 0;
    public static final int SECOND_ELEMENT = 1;
    public static final int THIRD_ELEMENT = 2;

    private TestHelper() {
    }

    public static ParkingLot generateParkingLotWithDummyCars(int capacity, int numberOfCars) {
        ParkingLot parkingLot = new ParkingLot(capacity);
        for (int i = 0; i < numberOfCars; i++) {
            parkingLot.park(new Car());
        }

        return parkingLot;
    }

    public static ParkingBoy generateParkingBoy(ParkingBoyType parkingBoyType, List<ParkingLot> parkingLotList) {
        return new ParkingBoyFactory()
                .getParkingBoy(parkingBoyType, parkingLotList);
    }
}
