package com.haddouti.springeco.samples.provider.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.haddouti.springeco.samples.provider.RestUtil;

/**
 * {@link StockService} implementation using <b>IEX Trading</b> backend system.
 *
 */
@Service
public class IEXtradingStockService implements StockService {

	public static final String STOCK_BACKEND_ID = "IEX";

	private static final Logger LOG = LoggerFactory.getLogger(IEXtradingStockService.class);

	@Autowired
	private RestTemplate restTemplate;

	private final String endpoint = "https://api.iextrading.com/1.0/stock/%s/price";

	@Override
	public Double getStockPrice(final String stockCompanyId) {

		final String url = String.format(endpoint, stockCompanyId);
		final Double response = RestUtil.callEndpoint(restTemplate, url, Double.class);

		if (response != null) {
			return response;
		} else {
			LOG.warn("Stock price determination failed.");
		}

		return null;
	}

	@Override
	public String getStockId() {
		return STOCK_BACKEND_ID;
	}

}
