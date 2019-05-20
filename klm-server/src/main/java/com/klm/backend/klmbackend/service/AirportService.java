package com.klm.backend.klmbackend.service;

import com.klm.backend.klmbackend.exceptions.AirportServiceException;
import com.klm.backend.klmbackend.pojos.AirportResponseWrapper;
import com.klm.backend.klmbackend.pojos.Fare;

public interface AirportService {

	public AirportResponseWrapper getAirportList(String term, String lang) throws AirportServiceException;

	public Fare getFareDetails(String origin, String destination, String currency) throws AirportServiceException;

}
