package com.thoughtworks.tdd;

public class Ticket {

	private final String carNumber;

	private int position;

	public Ticket(String carNumber) {
		this.carNumber = carNumber;
	}

	public Ticket(String carNumber, int position) {
		this.carNumber = carNumber;
		this.position = position;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
