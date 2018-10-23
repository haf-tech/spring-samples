package com.haddouti.springeco.samples.provider.stock;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;

public class StockServiceTest {

	@Test
	public void testJsonParserPositive() {

		final String json = "{\"APPL\": {\"price\": 230.069 } }";

		final JsonParser jsonParser = new BasicJsonParser();
		final Map<String, Object> map = jsonParser.parseMap(json);

		final Map<String, Double> companyPrice = (Map<String, Double>) map.get("APPL");
		final Double price = companyPrice.get("price");

		Assert.assertTrue(price > 0);
	}

}
