package com.haddouti.springeco.samples.eureka.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.haddouti.springeco.samples.eureka.client.feign.Service1Command;

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
public class EurekaServiceClient1Application {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Service1Command service1;

	public static void main(final String[] args) {
		SpringApplication.run(EurekaServiceClient1Application.class, args);
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
		return "RestTemplateClient=" + restTemplate.getForEntity("http://se-serviceapp1/s", String.class).getBody();
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

	@RequestMapping("/lookup/{serviceId}")
	public List<ServiceInstance> generalServiceDiscovery(@PathVariable final String serviceId) {
		return discoveryClient.getInstances(serviceId);
	}

}
