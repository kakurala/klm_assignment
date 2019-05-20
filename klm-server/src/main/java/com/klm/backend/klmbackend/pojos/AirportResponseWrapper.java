package com.klm.backend.klmbackend.pojos;

import lombok.Value;

@Value
public class AirportResponseWrapper {

	private EmbeddedWrapper _embedded;
	private Page page;

}
