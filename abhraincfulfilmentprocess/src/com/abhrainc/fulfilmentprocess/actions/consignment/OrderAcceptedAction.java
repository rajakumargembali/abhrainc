/**
 *
 */
package com.abhrainc.fulfilmentprocess.actions.consignment;

import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.task.RetryLaterException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author sujan
 *
 */
public class OrderAcceptedAction extends AbstractProceduralAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(OrderAcceptedAction.class);

	private EventService eventService;

	protected OrderAcceptedEvent getEvent(final ConsignmentProcessModel process)
	{
		return new OrderAcceptedEvent(process);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.processengine.action.AbstractProceduralAction#executeAction(de.hybris.platform.processengine.
	 * model.BusinessProcessModel)
	 */
	@Override
	public void executeAction(final ConsignmentProcessModel process) throws RetryLaterException, Exception
	{
		// YTODO Auto-generated method stub
		getEventService().publishEvent(getEvent(process));
		if (LOG.isInfoEnabled())
		{
			LOG.info("Process: " + process.getCode() + " in step " + getClass());
		}
	}

	protected EventService getEventService()
	{
		return eventService;
	}

	@Required
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}


}
