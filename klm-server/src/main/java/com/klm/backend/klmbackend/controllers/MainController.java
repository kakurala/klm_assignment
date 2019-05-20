package com.klm.backend.klmbackend.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klm.backend.klmbackend.exceptions.AirportServiceException;
import com.klm.backend.klmbackend.pojos.AirportResponseWrapper;
import com.klm.backend.klmbackend.pojos.Fare;
import com.klm.backend.klmbackend.service.AirportService;

@CrossOrigin
@RestController
public class MainController {

	private static final Logger logger = LoggerFactory
			.getLogger(MainController.class);

	@Autowired
	AirportService airportService;

	@RequestMapping(value = "/airports", method = GET, params = "term")
	public AirportResponseWrapper getAirportsList(
			@RequestParam(value = "lang", defaultValue = "en") String lang,
			@RequestParam("term") String term) throws AirportServiceException {

		logger.info("/airports endpoint");

		return airportService.getAirportList(term, lang);
	}

	@RequestMapping(value = "/fares/{origin}/{destination}", method = GET)
	public Fare getFareDetails(@PathVariable("origin") String origin,
			@PathVariable("destination") String destination,
			@RequestParam(value = "currency", defaultValue = "EUR") String currency)
			throws AirportServiceException {

		logger.info("/fares endoint");

		return airportService.getFareDetails(origin, destination, currency);
	}
}
