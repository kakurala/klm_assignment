package com.klm.backend.klmbackend.pojos;

import lombok.Value;

@Value
public class OAuthToken {

	private String access_token;
	private String token_type;
	private Integer expires_in;
	private String scope;
	
}
