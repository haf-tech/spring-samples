package com.haddouti.springeco.samples.provider.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "data/external")
public class RestExternalData {

	private static final String urlHeaders = "http://httpbin.org/headers";
	private static final String urlIp = "http://httpbin.org/ip";

	private final RestTemplate restTemplate = new RestTemplate();

	@GetMapping(path = "headers", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String headers() {

		return callEndpoint(urlHeaders, String.class);
	}

	@GetMapping(path = "ip", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String ip() {

		return callEndpoint(urlIp, String.class);
	}

	private <T> T callEndpoint(final String url, final Class<T> clazz) {

		final T response = restTemplate.getForObject(url, clazz);
		return response;
	}
}
