package com.oocl.cultivation;

import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;

import java.util.List;

import static java.util.function.Predicate.not;

public class ParkingBoy {
    protected final List<ParkingLot> parkingLotList;

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public ParkingTicket park(Car car) {
        ParkingLot parkingLot = parkingLotList.stream()
                .filter(not(ParkingLot::isFull))
                .findFirst()
                .orElseThrow(FullParkingException::new);

        return parkingLot.park(car);
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
