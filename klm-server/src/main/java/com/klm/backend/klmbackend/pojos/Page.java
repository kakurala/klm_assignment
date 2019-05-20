package com.klm.backend.klmbackend.pojos;

import lombok.Value;

@Value
public class Page {

	private Integer size;
	private Integer totalElements;
	private Integer totalPages;
	private Integer number;
	
}
