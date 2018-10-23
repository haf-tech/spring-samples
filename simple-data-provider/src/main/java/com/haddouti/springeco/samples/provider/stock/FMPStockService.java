package com.haddouti.springeco.samples.provider.stock;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.haddouti.springeco.samples.provider.RestUtil;

/**
 * {@link StockService} implementation using <b>Financial Modeling Prep</b>
 * backend system.
 *
 */
@Service
public class FMPStockService implements StockService {

	private static final String STOCK_BACKEND_ID = "FMP";

	private static final Logger LOG = LoggerFactory.getLogger(FMPStockService.class);

	@Autowired
	private RestTemplate restTemplate;

	private final String url = "https://financialmodelingprep.com/api/company/price/";

	@Override
	public Double getStockPrice(final String stockCompanyId) {

		final String response = RestUtil.callEndpointForEntity(restTemplate, url + stockCompanyId, String.class);

		if (StringUtils.isNoneBlank(response)) {
			final Double price = retrievePriceFromResponse(response, stockCompanyId);

			return price;
		}

		return null;
	}

	@Override
	public String getStockId() {
		return STOCK_BACKEND_ID;
	}

	private Double retrievePriceFromResponse(final String response, final String stockCompanyId) {
		final JsonParser jsonParser = new BasicJsonParser();

		try {
			final Map<String, Object> stockResponse = jsonParser.parseMap(StringUtils.remove(response, "<pre>"));

			final Map<String, Double> responseForCompany = (Map<String, Double>) stockResponse.get(stockCompanyId);
			if (responseForCompany != null) {
				return responseForCompany.get("price");
			}

		} catch (final JsonParseException e) {
			LOG.warn("Response is not valid. JSON is not parseable.", e);
		}
		return null;
	}

}
