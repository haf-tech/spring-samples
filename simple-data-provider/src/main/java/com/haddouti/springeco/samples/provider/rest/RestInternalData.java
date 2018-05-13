package com.haddouti.springeco.samples.provider.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "data/internal")
public class RestInternalData {

	@GetMapping(path = "echo/{val}")
	public String echo(@PathVariable final String val) {
		return String.format("Echo: < %s >.", val);
	}

	@GetMapping(path = "timestamp/{val}")
	public String timestamp(@PathVariable final String val) {
		return String.format("%s: < %s >", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), val);
	}
}
