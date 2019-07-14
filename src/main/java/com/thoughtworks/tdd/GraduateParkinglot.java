package com.thoughtworks.tdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.tdd.exception.CarNotExistParkingException;
import com.thoughtworks.tdd.exception.DuplicateCarNumberException;
import com.thoughtworks.tdd.exception.NoLotParkingException;

public class GraduateParkinglot {

	private final List<Parkinglot> parkingLots;

	private final Map<Ticket, Integer> positionsMap;

	public GraduateParkinglot(Parkinglot... parkingLot) {
		parkingLots = new ArrayList<Parkinglot>();
		positionsMap = new HashMap<Ticket, Integer>();
		if (parkingLot != null) {
			for (Parkinglot pl : parkingLot) {
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
		for (int i = 0; i < parkingLots.size(); i++) {
			try {
				Ticket ticket = parkingLots.get(i).park(car);
				ticket.setPosition(i);
				positionsMap.put(ticket, i);
				return ticket;
			} catch (NoLotParkingException e) {
				if (i < parkingLots.size() - 1) {
					continue;
				}
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
		for (Parkinglot parkingLot : parkingLots) {
			if (parkingLot.containCar(car)) {
				return true;
			}
		}
		return false;

	}

}
