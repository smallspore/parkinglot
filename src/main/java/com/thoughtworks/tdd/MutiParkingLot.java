package com.thoughtworks.tdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.tdd.exception.CarNotExistParkingException;
import com.thoughtworks.tdd.exception.DuplicateCarNumberException;
import com.thoughtworks.tdd.exception.NoLotParkingException;

public class MutiParkingLot {

	private final List<ParkingLot> parkingLots;

	private final Map<Ticket, Integer> positionsMap;

	public MutiParkingLot(ParkingLot... parkingLot) {
		parkingLots = new ArrayList<ParkingLot>();
		positionsMap = new HashMap<Ticket, Integer>();

		if (parkingLot != null) {
			for (ParkingLot pl : parkingLot) {
				if (pl != null) {
					parkingLots.add(pl);
				}
			}
		}
	}

	public Ticket park(Car car) throws IllegalArgumentException, DuplicateCarNumberException, NoLotParkingException {
		if (containCar(car)) {
			throw new DuplicateCarNumberException();
		}
		ParkingLot parkingLot = parkingLots.stream().reduce((pl1, pl2) -> {
			if (pl1.residualCapacity() >= pl2.residualCapacity()) {
				return pl1;
			} else {
				return pl2;
			}
		}).orElse(null);
		if (parkingLot != null) {
			int index = parkingLots.indexOf(parkingLot);
			try {
				Ticket ticket = parkingLot.park(car);
				ticket.setPosition(index);
				positionsMap.put(ticket, index);
				return ticket;
			} catch (NoLotParkingException e) {
				throw e;
			}
		}
		throw new NoLotParkingException();
	}

	public Car pick(Ticket ticket) throws IllegalArgumentException, CarNotExistParkingException {
		if (ticket == null) {
			throw new IllegalArgumentException();
		}
		Integer position = positionsMap.remove(ticket);
		if (position == null || parkingLots.size() < position + 1) {
			throw new CarNotExistParkingException();
		}
		Car car = parkingLots.get(position).pick(ticket);
		return car;
	}

	private boolean containCar(Car car) {
		for (ParkingLot parkingLot : parkingLots) {
			if (parkingLot.containCar(car)) {
				return true;
			}
		}
		return false;
	}

}
