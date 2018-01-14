package com.haddouti.springeco.samples.eureka.client.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign Client to the service, using discovery over Eureka regarding the given
 * name. With Hystrix fallback.
 *
 */
@FeignClient(name = "${se.sc.service1.name}", fallback = Service1CommandFallback.class)
public interface Service1Command {

	@RequestMapping(method = RequestMethod.GET, value = "/s")
	public String callService1();
}
