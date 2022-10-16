package com.chaya.crewbe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

record FlightPilot(String flightName, String pilot) {}

@RestController
@Slf4j
public class CrewController {

	@GetMapping("crew")
	public Flux<FlightPilot> getFlightsCrew() {
		return Flux.just(
				new FlightPilot("WZZ5448", "Amelia Earhart"),
				new FlightPilot("EZ7865", "Orville Wright"),
				new FlightPilot("ElAL9897", "Henri Giraud"),
				new FlightPilot("UAE5463", "Maverick")
		).doFirst(() -> log.info("Returning list of crew"));
	}
}

