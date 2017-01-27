package com.abhrainc.core.service;

import java.util.Map;


public interface ProductAbhraService
{

	String updateProductBYCode(Map products);

	String updateStockBYCode(Map map);

	/**
	 * @param pound
	 * @param euro
	 * @return
	 */
	String updateProductPricesByConversion(float pound, float euro);

	/**
	 *
	 */
	void sendOrderTrackingEmail();

	/**
	 *
	 */
	void sendOrderConsignmentStatusEmail();

}
