/**
 *
 */
package com.abhrainc.core.order.strategies.calculation.impl;


import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.delivery.DeliveryMode;
import de.hybris.platform.order.strategies.calculation.impl.DefaultFindDeliveryCostStrategy;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.PriceValue;

import org.apache.log4j.Logger;


/**
 * @author anumu
 *
 */
public class AbhraincDeliveryCostStrategyCalculation extends DefaultFindDeliveryCostStrategy
{

	private static final Logger LOG = Logger.getLogger(DefaultFindDeliveryCostStrategy.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.order.strategies.calculation.impl.DefaultFindDeliveryCostStrategy#getDeliveryCost(de.hybris.
	 * platform.core.model.order.AbstractOrderModel)
	 */
	@Override
	public PriceValue getDeliveryCost(final AbstractOrderModel order)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("order", order);
		try
		{

			getModelService().save(order);
			final AbstractOrder orderItem = getModelService().getSource(order);
			final DeliveryMode dMode = orderItem.getDeliveryMode();

			if (dMode.getName().contains("Home Delivery"))
			{

				double price = 0d;

				if (orderItem.getTotalPrice().intValue() <= 50)
				{
					price = 5d;
				}
				else if (orderItem.getTotalPrice().intValue() > 50 && orderItem.getTotalPrice().intValue() <= 100)
				{
					price = 2d;
				}
				else
				{
					price = 0d;
				}

				final PriceValue pv = new PriceValue("USD", price, false);

				return pv;
			}


			return dMode.getCost(orderItem);
		}
		catch (final Exception e)
		{
			LOG.warn("Could not find deliveryCost for order [" + order.getCode() + "] due to : " + e.getMessage() + "... skipping!");
			return new PriceValue(order.getCurrency().getIsocode(), 0.0, order.getNet().booleanValue());
		}
	}

}
