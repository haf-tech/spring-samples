package com.haddouti.springeco.samples.provider;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * REST util class
 *
 */
public class RestUtil {

	private static final Logger LOG = LoggerFactory.getLogger(RestUtil.class);

	/**
	 * Generic method to handle requests.
	 * 
	 * @param restTemplate
	 *            Spring {@link RestTemplate}
	 * @param url
	 *            Complete URL
	 * @param clazz
	 *            Return type
	 * @return Response in the given return type or null
	 */
	public static <T> T callEndpoint(final RestTemplate restTemplate, final String url, final Class<T> clazz) {

		addInterceptor(restTemplate);

		try {
			final T response = restTemplate.getForObject(url, clazz);
			return response;
		} catch (final HttpClientErrorException e) {
			LOG.error("Request failed.", e);
		}
		return null;
	}

	/**
	 * Generic method to handle requests.
	 * 
	 * @param restTemplate
	 *            Spring {@link RestTemplate}
	 * @param url
	 *            Complete URL
	 * @param clazz
	 *            Return type
	 * @return Response in the given return type or null
	 */
	public static <T> T callEndpointForEntity(final RestTemplate restTemplate, final String url, final Class<T> clazz) {

		addInterceptor(restTemplate);

		try {
			final ResponseEntity<T> response = restTemplate.getForEntity(url, clazz);
			return response.getBody();
		} catch (final HttpClientErrorException e) {
			LOG.error("Request failed.", e);
		}
		return null;
	}

	private static void addInterceptor(final RestTemplate restTemplate) {
		final List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

		if (LOG.isDebugEnabled()) {
			interceptors.add(new RequestResponseLogInterceptor());

			restTemplate.setInterceptors(interceptors);
		}
	}

	private static final class RequestResponseLogInterceptor implements ClientHttpRequestInterceptor {
		private final Logger log = LoggerFactory.getLogger(this.getClass());

		@Override
		public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
			logRequest(request, body);
			final ClientHttpResponse response = execution.execute(request, body);
			logResponse(response);
			return response;
		}

		private void logRequest(final HttpRequest request, final byte[] body) throws IOException {
			log.info("===========================request begin================================================");
			log.info("URI         : {}", request.getURI());
			log.info("Method      : {}", request.getMethod());
			log.info("Headers     : {}", request.getHeaders());
			log.info("Request body: {}", new String(body, "UTF-8"));
			log.info("==========================request end================================================");
		}

		private void logResponse(final ClientHttpResponse response) throws IOException {
			log.info("============================response begin==========================================");
			log.info("Status code  : {}", response.getStatusCode());
			log.info("Status text  : {}", response.getStatusText());
			log.info("Headers      : {}", response.getHeaders());
			// we consume here the response, afterwards is it not possible to
			// re-read the body if we use default RestTemplate. Consider to use
			// BufferingClientHttpRequestFactory or similar
			log.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
			log.info("=======================response end=================================================");
		}
	}
}
