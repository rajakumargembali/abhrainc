/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.abhrainc.fulfilmentprocess.actions.consignment;

import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractAction;


public class AllowShipmentAction extends AbstractAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(AllowShipmentAction.class);

	private Process2WarehouseAdapter process2WarehouseAdapter;

	public enum Transition
	{
		DELIVERY, CANCEL, ERROR;

		public static Set<String> getStringValues()
		{
			final Set<String> res = new HashSet<String>();

			for (final Transition transition : Transition.values())
			{
				res.add(transition.toString());
			}
			return res;
		}
	}

	@Override
	public String execute(final ConsignmentProcessModel process)
	{
		final ConsignmentModel consignment = process.getConsignment();
		if (consignment != null)
		{
			try
			{
				// Check if the Order is Cancelled
				if (OrderStatus.CANCELLED.equals(consignment.getOrder().getStatus())
						|| OrderStatus.CANCELLING.equals(consignment.getOrder().getStatus()))
				{
					return Transition.CANCEL.toString();
				}
				else
				{
					getProcess2WarehouseAdapter().shipConsignment(process.getConsignment());
					return getTransitionForConsignment(consignment);
				}
			}
			catch (final Exception e)
			{
				if (LOG.isDebugEnabled())
				{
					LOG.debug(e);
				}
				return Transition.ERROR.toString();
			}
		}
		return Transition.ERROR.toString();
	}

	protected String getTransitionForConsignment(final ConsignmentModel consignment)
	{
		if (consignment.getDeliveryMode() instanceof PickUpDeliveryModeModel)
		{
			return Transition.PICKUP.toString();
		}
		else if (consignment.getStatus().getCode().equals(ConsignmentStatus.DELIVERED))
		{
			return Transition.DELIVERY.toString();
		}
		return Transition.ERROR.toString();
	}

	protected Process2WarehouseAdapter getProcess2WarehouseAdapter()
	{
		return process2WarehouseAdapter;
	}

	@Required
	public void setProcess2WarehouseAdapter(final Process2WarehouseAdapter process2WarehouseAdapter)
	{
		this.process2WarehouseAdapter = process2WarehouseAdapter;
	}

	@Override
	public Set<String> getTransitions()
	{
		return Transition.getStringValues();
	}
}
