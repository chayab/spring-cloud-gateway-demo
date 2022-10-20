package com.chaya.crewbe.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

record Pilot(String name) {}

@Slf4j
@RestController
@RequestMapping("crew")
public class CrewController {

	@GetMapping("pilots")
	public List<Pilot> getPilots() {
		return List.of(
				new Pilot("Amelia Earhart"),
				new Pilot("Orville Wright"),
				new Pilot("Lydia Litvyak"),
				new Pilot("Maverick")
		);
	}
}

