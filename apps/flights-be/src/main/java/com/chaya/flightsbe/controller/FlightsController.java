package com.chaya.flightsbe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

record Flight(String name) {}

@RestController
@Slf4j
public class FlightsController {
	@GetMapping("flights")
	public Flux<Flight> getFlights() {
		return Flux.just(
				new Flight("WZZ5448"),
				new Flight("EZ7865"),
				new Flight("ElAL9897"),
				new Flight("UAE5463")
		).doFirst(() -> log.info("Returning list of Flights departing today"));
	}
}
