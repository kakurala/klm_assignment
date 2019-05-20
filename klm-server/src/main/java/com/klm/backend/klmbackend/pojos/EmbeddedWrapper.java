package com.klm.backend.klmbackend.pojos;

import java.util.List;

import lombok.Value;

@Value
public class EmbeddedWrapper {

	private List<Location> locations;
	
}
