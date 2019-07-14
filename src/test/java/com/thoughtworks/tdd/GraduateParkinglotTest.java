package com.thoughtworks.tdd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thoughtworks.tdd.exception.CarNotExistParkingException;
import com.thoughtworks.tdd.exception.DuplicateCarNumberException;
import com.thoughtworks.tdd.exception.NoLotParkingException;

public class GraduateParkinglotTest {

	private GraduateParkinglot graduateParkinglot;

	@BeforeEach
	void init() {
		graduateParkinglot = new GraduateParkinglot(new Parkinglot(1), new Parkinglot(2), new Parkinglot(3), new Parkinglot(4));
	}

	@Test
	void should_return_ticket_when_parking_given_car_number_enough_lots() {
		Ticket ticket = graduateParkinglot.park(new Car("ZAZA12345"));
		Assertions.assertNotNull(ticket);
		Assertions.assertEquals(0, ticket.getPosition());
	}

	@Test
	void should_not_return_ticket_when_parking_given_car_number_enough_lots_and_duplicate_number() {
		Assertions.assertNotNull(graduateParkinglot.park(new Car("ZAZA12345")));
		Assertions.assertThrows(DuplicateCarNumberException.class, () -> graduateParkinglot.park(new Car("ZAZA12345")));

	}

	@Test
	void should_not_return_ticket_when_parking_given_car_number_no_lots() {
		for (int i = 0; i < 10; i++) {
			Ticket ticket = graduateParkinglot.park(new Car("ZAZA1234" + i));
			Assertions.assertNotNull(ticket);
			int expected = 0;
			switch (i) {
			case 0: {
				expected = 0;
				break;
			}
			case 1:
			case 2: {
				expected = 1;
				break;
			}
			case 3:
			case 4:
			case 5: {
				expected = 2;
				break;
			}
			case 6:
			case 7:
			case 8:
			case 9: {
				expected = 3;
				break;
			}
			default: {
				expected = 0;
				break;
			}
			}
			Assertions.assertEquals(expected, ticket.getPosition());
		}
		Assertions.assertThrows(NoLotParkingException.class, () -> graduateParkinglot.park(new Car("ZAZA123456")));

	}

	@Test
	void should_not_return_ticket_when_parking_given_no_car_number() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> graduateParkinglot.park(new Car(null)));
	}

	@Test
	void should_pick_when_parking_given_right_ticket() {
		Ticket ticket;
		Assertions.assertNotNull(ticket = graduateParkinglot.park(new Car("ZAZA12345")));
		Assertions.assertNotNull(graduateParkinglot.pick(ticket));
	}

	@Test
	void should_not_pick_when_parking_given_wrong_ticket() {
		final Ticket ticket;
		Assertions.assertNotNull(ticket = new GraduateParkinglot(new Parkinglot(1)).park(new Car("ZAZA12345")));
		Assertions.assertThrows(CarNotExistParkingException.class, () -> graduateParkinglot.pick(ticket));
	}

	@Test
	void should_not_pick_when_parking_given_no_ticket() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> graduateParkinglot.pick(null));
	}

}
