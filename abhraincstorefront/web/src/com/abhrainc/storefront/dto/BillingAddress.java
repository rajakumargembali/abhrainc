/**
 *
 */
package com.abhrainc.storefront.dto;

import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.AddressData;


/**
 * @author sujan
 *
 */
public class BillingAddress extends CCPaymentInfoData
{
	private AddressData billingAddress;

	@Override
	public void setBillingAddress(final AddressData billingAddress)
	{
		this.billingAddress = billingAddress;
	}



	@Override
	public AddressData getBillingAddress()
	{
		return billingAddress;
	}

}
