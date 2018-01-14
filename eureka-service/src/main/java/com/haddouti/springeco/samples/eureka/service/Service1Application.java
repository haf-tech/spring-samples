package com.haddouti.springeco.samples.eureka.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class Service1Application {

    public static void main(final String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }

    @RequestMapping("/s")
    public String home() {
        return "Service App: " + System.currentTimeMillis();
    }

}
