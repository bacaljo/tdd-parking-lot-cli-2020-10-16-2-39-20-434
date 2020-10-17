package com.oocl.cultivation;

import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import com.oocl.cultivation.strategy.ParkingStrategy;

import java.util.List;

public class ParkingBoy {
    protected final List<ParkingLot> parkingLotList;
    private final ParkingStrategy parkingStrategy;

    public ParkingBoy(ParkingStrategy parkingStrategy, List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
        this.parkingStrategy = parkingStrategy;
    }

    public ParkingTicket park(Car car) {
        return parkingStrategy.park(car, parkingLotList);
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
