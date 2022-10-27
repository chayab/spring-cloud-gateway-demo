package com.chaya.flightsbe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

record Flight(String name) {}

@RestController
@Slf4j
public class FlightsController {
	@GetMapping("flights")
	public List<Flight> getFlights() {
		return List.of(
				new Flight("WZZ5448"),
				new Flight("EZ7865"),
				new Flight("ElAL9897"),
				new Flight("UAE5463")
		);
	}

	@GetMapping("flights/{airport}")
	public List<Flight> getFlightsByAirports(@PathVariable String airport) {
		return "TLV".equals(airport) ?
				List.of(new Flight("WZZ5448"),
						new Flight("EZ7865")) :
				List.of(new Flight("ElAL9897"),
						new Flight("UAE5463"));

	}
}
