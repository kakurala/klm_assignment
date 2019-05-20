package com.klm.backend.klmbackend.service;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.klm.backend.klmbackend.pojos.OAuthToken;

@Service
public class OAuthTokenService {

	private static final Logger logger = LoggerFactory.getLogger(OAuthTokenService.class);

	@Autowired
	RestTemplate restTemplate;

	@Value("${oauth.client.id}")
	private String clientId;

	@Value("${oauth.client.secret}")
	private String clientSecret;

	@Value("${oauth.client.scopes}")
	private String scopes;

	@Value("${oauth.client.grant_type}")
	private String grant_type;

	@Value("${oauth.token.url}")
	private String token_url;

	@Value("${oauth.token.path}")
	private String token_url_path;

	public String getToken() {

		logger.info("Requesting Mock API for new token");

		String credentials = clientId + ":" + clientSecret;
		String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic " + encodedCredentials);

		HttpEntity<String> request = new HttpEntity<String>(headers);

		// concat url + query
		String access_token_url = token_url.concat(token_url_path);

		Map<String, String> params = new HashMap<>();
		params.put("grant_type", grant_type);
		params.put("client_secret", clientSecret);
		params.put("client_id", clientId);
		params.put("scopes", scopes);

		UriComponents uriComponents = UriComponentsBuilder.fromUriString(access_token_url).buildAndExpand(params);

		ResponseEntity<OAuthToken> response = restTemplate.exchange(uriComponents.toUri().toString(), 
				HttpMethod.POST,
				request, 
				OAuthToken.class);

		logger.info("new token received = {}", response.getBody().getAccess_token());

		return response.getBody().getAccess_token();
	}
}
