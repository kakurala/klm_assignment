package com.klm.backend.klmbackend.pojos;

import lombok.Getter;
import lombok.Setter;

public class Fare {

	@Setter @Getter private double amount;
	@Setter @Getter private Currency currency;
	@Setter @Getter private String origin;
	@Setter @Getter private String destination;

	@Setter @Getter private Location sourceLocation;
	@Setter @Getter private Location destLocation;

}
