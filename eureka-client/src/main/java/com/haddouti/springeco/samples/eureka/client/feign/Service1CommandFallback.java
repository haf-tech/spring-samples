package com.haddouti.springeco.samples.eureka.client.feign;

import org.springframework.stereotype.Component;

import com.haddouti.springeco.samples.eureka.client.feign.Service1Command;

/**
 * Fallback implementation
 *
 * This fallback implementation will be used in the case of the original call to
 * the service {@link Service1Command} failed.
 */
@Component
public class Service1CommandFallback implements Service1Command {

	@Override
	public String callService1() {
		return new String("ServiceApp1 request failed!");
	}

}
