package com.thoughtworks.tdd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.tdd.exception.CarNotExistParkingException;
import com.thoughtworks.tdd.exception.DuplicateCarNumberException;
import com.thoughtworks.tdd.exception.NoLotParkingException;

public class SmartParkinglot {

	private final List<Parkinglot> parkinglots;

	private final Map<Ticket, Integer> positionsMap;

	public SmartParkinglot(Parkinglot... parkingLot) {
		parkinglots = new ArrayList<Parkinglot>();
		positionsMap = new HashMap<Ticket, Integer>();
		if (parkingLot != null) {
			for (Parkinglot pl : parkingLot) {
				if (pl != null) {
					parkinglots.add(pl);
				}
			}
		}
	}

	public Ticket park(Car car) throws IllegalArgumentException, DuplicateCarNumberException, NoLotParkingException {
		if (containCar(car)) {
			throw new DuplicateCarNumberException();
		}
		Parkinglot parkingLot = parkinglots.stream().reduce((pl1, pl2) -> {
			if (pl1.residualCapacity() >= pl2.residualCapacity()) {
				return pl1;
			} else {
				return pl2;
			}
		}).orElse(null);
		if (parkingLot != null) {
			int index = parkinglots.indexOf(parkingLot);
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
		if (position == null || parkinglots.size() < position + 1) {
			throw new CarNotExistParkingException();
		}
		Car car = parkinglots.get(position).pick(ticket);
		return car;

	}

	private boolean containCar(Car car) {
		for (Parkinglot parkingLot : parkinglots) {
			if (parkingLot.containCar(car)) {
				return true;
			}
		}
		return false;
	}

}
