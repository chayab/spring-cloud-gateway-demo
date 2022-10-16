package com.chaya.gateway.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

record Flight(String name, String destination) {}
record FlightCrew(String flightName, String crewMember) {}
record FlightWithPilot(String flightId, String destination, String pilotName){}

@Slf4j
@RestController
@RequestMapping("/aggregate")
@RequiredArgsConstructor
public class AggregateController {

	@Value("${app.servers.flights}")
	String flightsUrl;
	@Value("${app.servers.crew}")
	String crewUrl;
	private final ObjectMapper objectMapper;


	@GetMapping("/flights/details")
	public Flux<FlightWithPilot> getFlightsWithPilot(ProxyExchange<byte[]> proxy) {
		Mono<ResponseEntity<byte[]>> flightsMono = proxy.uri(flightsUrl).get();
		Mono<ResponseEntity<byte[]>> crewMono = proxy.uri(crewUrl).get();

		return unionResponses(Mono.zip(flightsMono, crewMono))
				.flatMapMany(Flux::fromIterable);
	}

	private Mono<List<FlightWithPilot>> unionResponses(Mono<Tuple2<ResponseEntity<byte[]>, ResponseEntity<byte[]>>> zipMono) {
		return zipMono.map(result -> {
			try {
				List<Flight> flights = objectMapper.readValue(result.getT1().getBody(), new TypeReference<>() {});
				Map<String, FlightCrew> pilotsByFlightName =
						objectMapper.readValue(result.getT2().getBody(), new TypeReference<List<FlightCrew>>() {
								})
								.stream().collect(Collectors.toMap(FlightCrew::flightName, Function.identity()));
				return flights.stream().map(flight -> new FlightWithPilot(flight.name(), flight.destination(), pilotsByFlightName.get(flight.name()).crewMember()))
						.collect(toList());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
