package com.klm.backend.klmbackend.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.klm.backend.klmbackend.exceptions.AirportServiceException;
import com.klm.backend.klmbackend.pojos.AirportResponseWrapper;
import com.klm.backend.klmbackend.pojos.Fare;
import com.klm.backend.klmbackend.pojos.Location;

@Service
public class AirportServiceImpl implements AirportService {

	private static final Logger logger = LoggerFactory.getLogger(AirportServiceImpl.class);

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

	@Value("${api.airport.endpoint}")
	private String airports_end_point;

	@Value("${api.fares.endpoint}")
	private String fares_end_point;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private AirportHelperAsyncService asyncRestCalls;

	@Autowired
	OAuthTokenService tokenService;

	public AirportResponseWrapper getAirportList(String term, String lang) throws AirportServiceException {

		logger.info("getting airport list for search term = {}", term);

		Map<String, String> params = new HashMap<>();
		params.put("term", term);
		params.put("lang", lang);
		params.put("access_token", this.tokenService.getToken());

		UriComponents uriComponents = UriComponentsBuilder.fromUriString(airports_end_point).buildAndExpand(params);

		ResponseEntity<AirportResponseWrapper> airportResponseEntity = restTemplate.exchange(
				uriComponents.toUri().toString(), HttpMethod.GET, this.buildHttpEntity(), AirportResponseWrapper.class,
				params);

		return airportResponseEntity.getBody();
	}

	public Fare getFareDetails(String origin, String destination, String currency) throws AirportServiceException {

		logger.info("Fetching fare details from Mock rest API for origin = {}, dest = {}, currency = {}", origin,
				destination, currency);

		Map<String, String> params = new HashMap<>();
		params.put("destination", destination);
		params.put("origin", origin);
		params.put("access_token", this.tokenService.getToken());

		UriComponents uriComponents = UriComponentsBuilder.fromUriString(fares_end_point).buildAndExpand(params);

		ResponseEntity<Fare> fareResponse = restTemplate.exchange(uriComponents.toUri().toString(), HttpMethod.GET,
				this.buildHttpEntity(), Fare.class, params);

		if (fareResponse.getStatusCode().value() == HttpStatus.OK.value()) {
			// make two calls in parallel two fetch Airport details of both
			// origin
			// and destination
			CompletableFuture<Location> source = this.asyncRestCalls.getSingleAirportDetails(origin);
			CompletableFuture<Location> dest = this.asyncRestCalls.getSingleAirportDetails(destination);

			Fare fareBean = fareResponse.getBody();
			try {
				fareBean.setSourceLocation(source.get());
				fareBean.setDestLocation(dest.get());
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Error occurred while calling Single Airport Endpoint : {}", e.getMessage());

				throw new AirportServiceException(AirportServiceConstants.GENERIC_ERROR_E1003,
						AirportServiceConstants.SERVER_ERR_MSG);
			}
			return fareBean;
		} else {
			// If status code is not 2XX (and not 4xx, 5xx handled by custom
			// error handler) then return exception, instead of
			// returning null
			throw new AirportServiceException(AirportServiceConstants.GENERIC_ERROR_E1003,
					AirportServiceConstants.NO_DATA_FOUND_MSG);
		}
	}

	private HttpEntity<String> buildHttpEntity() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		return new HttpEntity<String>(headers);
	}
}
