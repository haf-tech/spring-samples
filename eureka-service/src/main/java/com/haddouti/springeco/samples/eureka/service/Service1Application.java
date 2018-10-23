package com.haddouti.springeco.samples.eureka.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class Service1Application extends WebSecurityConfigurerAdapter {

	@Value("${config.security.enabled:false}")
	private boolean isSecurityEnabled;

	public static void main(final String[] args) {
		SpringApplication.run(Service1Application.class, args);
	}

	@RequestMapping("/s")
	public String home() {
		return "Service App: " + System.currentTimeMillis();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		if (!isSecurityEnabled) {
			// Security enable flag is deactivated in Spring Boot 2
			// This is the workaround for the new Spring Security constraints
			web.ignoring().antMatchers("/**");
		}
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		if (isSecurityEnabled) {
			http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
		}
	}

}
