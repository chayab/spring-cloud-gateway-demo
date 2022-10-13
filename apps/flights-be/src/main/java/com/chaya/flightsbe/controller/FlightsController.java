package com.chaya.flightsbe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

record Flight(String name, String destination) {}

@RestController
@Slf4j
public class FlightsController {
	@GetMapping("flights")
	public Flux<Flight> getFlights() {
		return Flux.just(
				new Flight("WZZ5448", "Russia"),
				new Flight("EZ7865", "North Carolina"),
				new Flight("ElAL9897", "Mont Blan"),
				new Flight("UAE5463", "San Diego")
		).doFirst(() -> log.info("Returning list of Flights departing today"));
	}
}
