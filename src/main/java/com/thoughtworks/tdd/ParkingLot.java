package com.thoughtworks.tdd;

import com.thoughtworks.tdd.exception.CarNotExistParkingException;
import com.thoughtworks.tdd.exception.DuplicateCarNumberException;
import com.thoughtworks.tdd.exception.NoLotParkingException;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {

	private final int capacity;

	private Map<String, Car> lotsMap = new HashMap<>();

	public ParkingLot(int capacity) {
		if (capacity < 0) {
			capacity = 0;
		}
		this.capacity = capacity;
	}

	public Ticket park(Car car) throws IllegalArgumentException, DuplicateCarNumberException, NoLotParkingException {
		if (car.getNumber() == null) {
			throw new IllegalArgumentException();
		}
		if (containCar(car)) {
			throw new DuplicateCarNumberException();
		}
		if (lotsMap.size() < this.capacity) {
			lotsMap.put(car.getNumber(), car);
			return new Ticket(car.getNumber());
		} else {
			throw new NoLotParkingException();
		}
	}

	public Car pick(Ticket ticket) throws IllegalArgumentException, CarNotExistParkingException {
		if (ticket != null) {
			Car car = lotsMap.get(ticket.getCarNumber());
			if (car != null && lotsMap.remove(ticket.getCarNumber(), car)) {
				return car;
			} else {
				throw new CarNotExistParkingException();
			}
		}
		throw new IllegalArgumentException();
	}

	public boolean containCar(Car car) {
		return car == null ? false : lotsMap.containsKey(car.getNumber());
	}

	public int residualCapacity() {
		return this.capacity - this.lotsMap.size();
	}

	public double vacancyRate() {
		if (this.capacity > 0) {
			return (this.capacity - this.lotsMap.size()) / (double) this.capacity;
		} else {
			return 0;
		}
	}

}
