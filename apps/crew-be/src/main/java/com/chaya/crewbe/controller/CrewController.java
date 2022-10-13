package com.chaya.crewbe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

record FlightCrew(String flightName, String crewMember) {}

@RestController
@Slf4j
public class CrewController {

	@GetMapping("crew")
	public Flux<FlightCrew> getFlightsCrew() {
		return Flux.just(
				new FlightCrew("WZZ5448", "Amelia Earhart"),
				new FlightCrew("EZ7865", "Orville Wright"),
				new FlightCrew("ElAL9897", "Henri Giraud"),
				new FlightCrew("UAE5463", "Maverick")
		).doFirst(() -> log.info("Returning list of crew"));
	}
}

