package com.klm.backend.klmbackend.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.klm.backend.klmbackend.pojos.Location;

@Service
public class AirportHelperAsyncService {

	private static final Logger logger = LoggerFactory.getLogger(AirportHelperAsyncService.class);

	@Autowired
	OAuthTokenService tokenService;

	@Value("${api.airport.single}")
	private String airports_end_point;

	@Autowired
	RestTemplate restTemplate;

	@Async
	public CompletableFuture<Location> getSingleAirportDetails(String code) {

		logger.info(" in getSingleAirportDetails for {}", code);

		Map<String, String> params = new HashMap<>();
		params.put("code", code);
		params.put("access_token", this.tokenService.getToken());

		UriComponents uriComponents = UriComponentsBuilder.fromUriString(airports_end_point).buildAndExpand(params);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> request = new HttpEntity<String>(headers);

		ResponseEntity<Location> singleAirportResponseEntity = restTemplate.exchange(uriComponents.toUri().toString(),
				HttpMethod.GET, request, Location.class, params);

		logger.info(" API call for {} is Success; Response = {}", code, singleAirportResponseEntity.getBody());

		return CompletableFuture.completedFuture((singleAirportResponseEntity.getBody()));
	}
}
