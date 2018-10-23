package com.haddouti.springeco.samples.eureka.registry;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		// Spring Security expects a CSRF token in every request
		// Eureka clients will not genereate valid token, for this reason ignore
		// eureka endpoints
		http.csrf().ignoringAntMatchers("/eureka/**");
		super.configure(http);
	}
}
