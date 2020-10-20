package com.oocl.cultivation.parkingboy;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import com.oocl.cultivation.exception.FullParkingException;
import com.oocl.cultivation.exception.MissingCarException;
import com.oocl.cultivation.exception.MissingParkingTicketException;
import com.oocl.cultivation.exception.UnrecognizedParkingTicketException;
import com.oocl.cultivation.strategy.parking.SequentialParkingStrategy;

import java.util.ArrayList;
import java.util.List;

import static java.util.function.Predicate.not;

public class ParkingLotServiceManager extends ParkingBoy {
    private final List<ParkingBoy> managementList;

    public ParkingLotServiceManager(List<ParkingLot> parkingLot) {
        super(new SequentialParkingStrategy(), parkingLot);
        managementList = new ArrayList<>();
    }

    public void enlistParkingBoys(List<ParkingBoy> parkingBoyList) {
        managementList.addAll(parkingBoyList);
    }

    public List<ParkingBoy> getManagementList() {
        return managementList;
    }

    public ParkingTicket delegatePark(Car car) {
        if (car == null) {
            throw new MissingCarException();
        }

        return managementList.stream()
                .filter(parkingBoy -> parkingBoy.getParkingLotList()
                        .stream()
                        .anyMatch(not(ParkingLot::isFull)))
                .findFirst()
                .orElseThrow(FullParkingException::new)
                .park(car);
    }

    public Car delegateFetch(ParkingTicket parkingTicket) {
        if (parkingTicket == null) {
            throw new MissingParkingTicketException();
        }

        return managementList.stream()
                .filter(parkingBoy -> parkingBoy.getParkingLotList().stream()
                        .anyMatch(parkingLot -> parkingLot.containsParkingTicket(parkingTicket)))
                .findFirst()
                .orElseThrow(UnrecognizedParkingTicketException::new)
                .fetch(parkingTicket);
    }
}
