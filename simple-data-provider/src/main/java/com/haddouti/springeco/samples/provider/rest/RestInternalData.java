package com.haddouti.springeco.samples.provider.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "data/internal")
@RestController
@RequestMapping(path = "data/internal")
public class RestInternalData {

	@ApiOperation(value = "echo/{val}", nickname = "InternalEcho", notes = "Echo the given value.")
	@GetMapping(path = "echo/{val}")
	public String echo(@PathVariable final String val) {
		return String.format("Echo: < %s >.", val);
	}

	@ApiOperation(value = "timestamp/{val}", nickname = "InternalTimestamp", notes = "Echo the given value with the current timestamp.")
	@GetMapping(path = "timestamp/{val}")
	public String timestamp(@PathVariable final String val) {
		return String.format("%s: < %s >", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), val);
	}
}
