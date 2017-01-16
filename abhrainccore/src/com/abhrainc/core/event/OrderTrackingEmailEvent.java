/**
 *
 */
package com.abhrainc.core.event;

/**
 * @author sujan
 *
 */
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;

import java.util.List;


/**
 * @author Shobhit
 *
 */
public class OrderTrackingEmailEvent<T extends BaseSiteModel> extends AbstractEvent
{
	private final List<ConsignmentModel> consignments;

	public OrderTrackingEmailEvent(final List<ConsignmentModel> consignments)
	{
		this.consignments = consignments;
	}

	/**
	 * @return the consignments
	 */
	public List<ConsignmentModel> getConsignments()
	{
		return consignments;
	}
}
