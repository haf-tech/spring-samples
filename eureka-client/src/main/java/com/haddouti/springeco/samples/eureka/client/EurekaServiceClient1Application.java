package com.haddouti.springeco.samples.eureka.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.haddouti.springeco.samples.eureka.client.feign.Service1Command;
import com.haddouti.springeco.samples.eureka.client.feign.Service1StockCommand;

// Eureka
@EnableDiscoveryClient
// Hystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
// 3rd Client
@EnableFeignClients
// General Spring
@SpringBootApplication
@RestController
@Configuration
public class EurekaServiceClient1Application extends WebSecurityConfigurerAdapter {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Service1Command service1;

	@Autowired
	private Service1StockCommand service1Stock;

	public static void main(final String[] args) {
		new SpringApplicationBuilder(EurekaServiceClient1Application.class).web(WebApplicationType.SERVLET).run(args);
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		// Security enable flag is deactivated in Spring Boot 2
		// This is the workaround for the new Spring Security constraints -
		// helpful for Hystrix and Dashboard
		web.ignoring().antMatchers("/actuator/**");
	}

	/* ********* Simplicity - RestController integrated *********** */
	/**
	 * Reinit {@link RestTemplate} with loadbalancer capabilities using Eureka
	 * to retrieve the right connections.
	 *
	 * @return balanced {@link RestTemplate} object
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/c")
	public String home() {
		return "RestTemplateClient=" + restTemplate.getForEntity("http://SpringSamples-ServiceEndpoint-01/s", String.class).getBody();
	}

	/**
	 * Uses the Service implementation with FeignClient+Hystrix. The link from
	 * this client to the right service (endpoints) works with the service name
	 * which will be discovered with Eureka.
	 *
	 * @return
	 */
	@RequestMapping("/c2")
	public String service1WithFeign() {
		return "FeignClient=" + service1.callService1();
	}

	@RequestMapping("/sp/{companyId}")
	public String service1WithFeign(@PathVariable final String companyId) {
		return "FeignClient=" + service1Stock.callService1(companyId);
	}

	@RequestMapping("/lookup/{serviceId}")
	public List<ServiceInstance> generalServiceDiscovery(@PathVariable final String serviceId) {
		return discoveryClient.getInstances(serviceId);
	}

}
