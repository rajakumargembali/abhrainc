/**
 *
 */
package com.abhrainc.fulfilmentprocess.actions.consignment;

import de.hybris.platform.orderprocessing.events.ConsignmentProcessingEvent;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;


/**
 * @author sujan
 *
 */
public class OrderDispatchedEvent extends ConsignmentProcessingEvent
{

	/**
	 * @param process
	 */
	public OrderDispatchedEvent(final ConsignmentProcessModel process)
	{
		super(process);
		// YTODO Auto-generated constructor stub
	}

}
