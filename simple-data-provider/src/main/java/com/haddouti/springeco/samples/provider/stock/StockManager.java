package com.haddouti.springeco.samples.provider.stock;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A stock service implementation to provide general finance stock information.
 *
 * This service implementation distinguish between available finance stock
 * backend systems, but this is not the focus if this implementation.
 */
@Service
public class StockManager implements StockService {

	private static final Logger LOG = LoggerFactory.getLogger(StockManager.class);

	private static final String STOCK_BACKEND_ID = "DEFAULT";

	@Autowired
	private List<StockService> stockServices;

	@Value("${config.stock.backend.id}")
	private String selectedStockBackendId;

	public String retrieveStockPrice(final String stockCompanyId) {

		return String.format("%s: %f", retrieveStockService().getStockId(), retrieveStockService().getStockPrice(stockCompanyId));
	}

	private StockService retrieveStockService() {

		StockService currentStockService = this;
		for (final StockService s : stockServices) {

			if (StringUtils.equals(selectedStockBackendId, s.getStockId())) {

				currentStockService = s;
				break;
			}
		}

		LOG.info("Configured stockBackendId {} and retrieved StockService: {}", selectedStockBackendId, currentStockService.getStockId());

		return currentStockService;
	}

	@Override
	public String getStockId() {
		return STOCK_BACKEND_ID;
	}

	@Override
	public Double getStockPrice(final String stockCompanyId) {
		return null;
	}
}
