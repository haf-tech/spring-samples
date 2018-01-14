package com.haddouti.springeco.samples.eureka.client;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.haddouti.springeco.samples.eureka.client.feign.Service1Command;
import com.haddouti.springeco.samples.eureka.client.feign.Service1CommandFallback;

/**
 * Integration test - testing connection to Service 1 without service discovery.
 *
 * Testing the connection to the Service 1 using a (feign) client and the link
 * to service endpoints is made up with the service instance ID, like in the
 * productive code. Only in the test profile we have now the information to the
 * concrete server. This fits the requirement, that the logic do not contains a
 * close coupling to a network information for any endpoint; with still using
 * symbolic names (service instance name).
 *
 * Hystrix is active to check against the fallback string. Otherwise use
 * "feign.hystrix.enabled=false" in SpringBootTest and other check if service is
 * available (like host not reachable exception or similar).
 *
 */
@RunWith(SpringRunner.class)
// @SpringBootTest(properties = "feign.hystrix.enabled=false")
@SpringBootTest
@DirtiesContext
public class Service1Test {

	@Autowired
	private Service1Command service1;

	@Test
	public void testService1Call() {

		Assert.assertFalse("Service fallback occurred.",
				StringUtils.equalsIgnoreCase(service1.callService1(), new Service1CommandFallback().callService1()));
	}
}
