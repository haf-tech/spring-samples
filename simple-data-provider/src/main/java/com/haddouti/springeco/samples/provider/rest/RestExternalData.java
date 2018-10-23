package com.haddouti.springeco.samples.provider.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.haddouti.springeco.samples.provider.RestUtil;
import com.haddouti.springeco.samples.provider.stock.StockManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "data/externa")
@RestController
@RequestMapping(path = "data/external")
public class RestExternalData {

	private static final String urlHeaders = "http://httpbin.org/headers";
	private static final String urlIp = "http://httpbin.org/ip";

	private final RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private StockManager stockManager;

	@ApiOperation(value = "headers", nickname = "ExternalHeaders", notes = "Delivers the header from the request.")
	@GetMapping(path = "headers", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String headers() {

		return RestUtil.callEndpoint(restTemplate, urlHeaders, String.class);
	}

	@ApiOperation(value = "ip", nickname = "ExternalIp", notes = "Delivers the IP.")
	@GetMapping(path = "ip", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String ip() {

		return RestUtil.callEndpoint(restTemplate, urlIp, String.class);
	}

	@ApiOperation(value = "stockPrice/{stockCompanyId}", nickname = "ExternalStockPrice", notes = "Delivers the Stock Price for the given Company Stock ID.")
	@GetMapping(path = "stockPrice/{stockCompanyId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getStockPrice(@PathVariable final String stockCompanyId) {

		return stockManager.retrieveStockPrice(stockCompanyId);
	}

}
