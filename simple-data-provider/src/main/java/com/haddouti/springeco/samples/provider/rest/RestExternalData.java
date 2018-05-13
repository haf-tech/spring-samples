package com.haddouti.springeco.samples.provider.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "data/externa")
@RestController
@RequestMapping(path = "data/external")
public class RestExternalData {

	private static final String urlHeaders = "http://httpbin.org/headers";
	private static final String urlIp = "http://httpbin.org/ip";

	private final RestTemplate restTemplate = new RestTemplate();

	@ApiOperation(value = "headers", nickname = "ExternalHeaders", notes = "Delivers the header from the request.")
	@GetMapping(path = "headers", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String headers() {

		return callEndpoint(urlHeaders, String.class);
	}

	@ApiOperation(value = "ip", nickname = "ExternalIp", notes = "Delivers the IP.")
	@GetMapping(path = "ip", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String ip() {

		return callEndpoint(urlIp, String.class);
	}

	private <T> T callEndpoint(final String url, final Class<T> clazz) {

		final T response = restTemplate.getForObject(url, clazz);
		return response;
	}
}
