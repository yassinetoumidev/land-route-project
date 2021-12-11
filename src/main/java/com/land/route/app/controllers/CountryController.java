package com.land.route.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.land.route.app.exceptions.CountryNotFoundException;
import com.land.route.app.exceptions.CrossNotFoundException;
import com.land.route.app.model.Route;
import com.land.route.app.services.CountryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/routing")
public class CountryController {
	@Autowired
	private CountryService countryService;
	@GetMapping("/{origin}/{destination}")
	public Route getRoutes(@PathVariable String origin , @PathVariable String destination) throws CrossNotFoundException, CountryNotFoundException {
		return countryService.getRoute(origin , destination);
	}
	
	
	
	 @ExceptionHandler(CrossNotFoundException.class)
	    protected ResponseEntity<Object> handleCrossNotFoundException(CrossNotFoundException e, WebRequest request) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	 
	 @ExceptionHandler(CountryNotFoundException.class)
	    protected ResponseEntity<Object> handleCountryNotFoundException(CountryNotFoundException e, WebRequest request) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
}
