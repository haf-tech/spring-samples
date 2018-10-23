package com.haddouti.springeco.samples.eureka.client.feign;

import org.springframework.stereotype.Component;

/**
 * Fallback implementation
 *
 * This fallback implementation will be used in the case of the original call to
 * the service {@link Service1Command} failed.
 */
@Component
public class Service1CommandFallback implements Service1Command, Service1StockCommand {

	@Override
	public String callService1() {
		return new String("ServiceApp1 request failed!");
	}

	@Override
	public String callService1(final String companyId) {
		return new String("ServiceApp1 request failed, for companyId: " + companyId);
	}

}
