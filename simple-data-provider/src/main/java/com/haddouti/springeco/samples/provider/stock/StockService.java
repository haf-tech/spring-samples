package com.haddouti.springeco.samples.provider.stock;

/**
 * Generic stock service providing general stock operations.
 *
 */
public interface StockService {

	/**
	 * Delivers the Stock Backend System ID.
	 * 
	 * @return Backend ID
	 */
	public String getStockId();

	/**
	 * Delivers the stock price of the given company.
	 * 
	 * @param stockCompanyId
	 *            Company ID/symbole
	 * @return Price or null if not known
	 */
	public Double getStockPrice(String stockCompanyId);
}
