package com.klm.backend.klmbackend.pojos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;


public class ErrorResponse {

	@Getter @Setter private Date timestamp;
	@Getter @Setter private String message;
	@Getter @Setter private String desc;
	@Getter @Setter private Integer code;

	public ErrorResponse(Date timestamp, String message, String desc, Integer code) {
		this.setTimestamp(timestamp);
		this.setMessage(message);
		this.setDesc(desc);
		this.setCode(code);
	}
	
}
