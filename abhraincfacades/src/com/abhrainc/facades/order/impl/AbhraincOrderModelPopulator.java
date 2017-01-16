package com.abhrainc.facades.order.impl;

import de.hybris.platform.core.model.order.OrderModel;

import com.abhrainc.core.model.AbhraincOrderModel;


public class AbhraincOrderModelPopulator
{
	protected AbhraincOrderModel populator(AbhraincOrderModel target, final OrderModel source)
	{
		target = (AbhraincOrderModel) source;
		//target.setExpectedDeliveryDate(value);

		return target;
	}
}