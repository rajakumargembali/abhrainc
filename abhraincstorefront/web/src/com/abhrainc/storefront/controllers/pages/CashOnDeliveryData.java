/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.commercefacades.order.data.CartData;


/**
 * @author sujan
 *
 */
public class CashOnDeliveryData extends CartData
{

	public boolean isCashOnDelivery;

	/**
	 * @return the isCashOnDelivery
	 */
	public boolean isCashOnDelivery()
	{
		return isCashOnDelivery;
	}

	/**
	 * @param isCashOnDelivery
	 *           the isCashOnDelivery to set
	 */
	public void setCashOnDelivery(final boolean isCashOnDelivery)
	{
		this.isCashOnDelivery = isCashOnDelivery;
	}


}
