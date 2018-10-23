package com.haddouti.springeco.samples.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SimpleDataProviderApplication {

	// @Value("${build.version}")
	private String buildVersion;

	public static void main(final String[] args) {

		SpringApplication.run(SimpleDataProviderApplication.class, args);
	}

	@Bean
	public Docket configSwagger() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.regex(".*data/.*"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Data Provider").description("Multiple data interfaces")
				.contact(new Contact("Hafid Haddouti", "https://github.com/haf-tech/spring-samples", "code@haddouti.com")).license("MIT").licenseUrl("")
				.version(buildVersion).build();
	}

	@Bean
	public RestTemplate restTemplate() {
		// Use a buffered client otherwise we lose the response in case of
		// response debugging
		final ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		final RestTemplate restTemplate = new RestTemplate(factory);

		return restTemplate;
	}
}
