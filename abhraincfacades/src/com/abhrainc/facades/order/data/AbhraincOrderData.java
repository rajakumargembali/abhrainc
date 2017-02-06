package com.abhrainc.facades.order.data;

import de.hybris.platform.commercefacades.order.data.OrderData;

import java.util.Date;


public class AbhraincOrderData extends OrderData
{
	private Date expectedDeliveryDate;

	public AbhraincOrderData()
	{
		//default constructor
	}

	public void setExpectedDeliveryDate(final Date expectedDeliveryDate)
	{
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public Date getExpectedDeliveryDate()
	{
		return expectedDeliveryDate;
	}

}