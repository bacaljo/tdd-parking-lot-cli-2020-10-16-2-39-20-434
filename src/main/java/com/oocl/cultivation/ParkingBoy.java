package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;

import java.util.List;

import static java.util.Arrays.asList;

public class ParkingBoy {
    private final List<ParkingLot> parkingLotList;

    public ParkingBoy(ParkingLot... parkingLot) {
        this(asList(parkingLot));
    }

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public ParkingTicket park(Car car) {
        for (ParkingLot parkingLot : parkingLotList) {
            boolean isNotYetFull = parkingLot.getCapacity() != parkingLot.getNumberOfParkedCars();
            if (isNotYetFull) {
                return parkingLot.park(car);
            }
        }

        throw new FullParkingException();
    }

    public Car fetch(ParkingTicket parkingTicket) {
        if (parkingTicket == null) {
            throw new MissingParkingTicketException();
        }

        for (ParkingLot parkingLot : parkingLotList) {
            if (parkingLot.isTicketInUsedTicketList(parkingTicket)) {
                throw new UnrecognizedParkingTicketException();
            }

            Car car = parkingLot.fetch(parkingTicket);
            if (car != null) {
                return car;
            }
        }

        throw new UnrecognizedParkingTicketException();
    }

    public List<ParkingLot> getParkingLotList() {
        return parkingLotList;
    }
}
