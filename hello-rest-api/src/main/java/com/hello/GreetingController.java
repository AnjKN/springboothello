package com.hello;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.microsoft.applicationinsights.TelemetryClient;

@RestController
@RequestMapping("/user")

public class GreetingController {

	private static final Logger logger = LogManager.getLogger(GreetingController.class);
	
	@Autowired
	TelemetryClient telemetryClient;

	@GetMapping("/greeting")
	public String greetings() {
		
		telemetryClient.trackEvent("URI /greeting is triggered");
		
        logger.info("Info from app");      
      
		return "Hello World!";
	}

	@GetMapping("/data")
	public String users() {
		try {
			telemetryClient.trackEvent("getting users");
			if (true) {
				throw new SQLException("this is an SQL exception");
			}			

			return "userdata";
		} catch (Exception e) {
			// send exception information
			
			telemetryClient.trackException(e);
			 logger.error( e.getMessage());   
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}