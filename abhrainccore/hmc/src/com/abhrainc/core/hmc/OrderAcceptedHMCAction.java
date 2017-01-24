/**
 *
 */
package com.abhrainc.core.hmc;

import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.core.Registry;
import de.hybris.platform.hmc.util.action.ActionEvent;
import de.hybris.platform.hmc.util.action.ActionResult;
import de.hybris.platform.hmc.util.action.ItemAction;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.ordersplitting.jalo.Consignment;
import de.hybris.platform.ordersplitting.jalo.ConsignmentProcess;
import de.hybris.platform.processengine.BusinessProcessService;


/**
 * @author sujan
 *
 */
public class OrderAcceptedHMCAction extends ItemAction
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.hmc.util.action.ItemAction#perform(de.hybris.platform.hmc.util.action.ActionEvent)
	 */
	@Override
	public ActionResult perform(final ActionEvent event) throws JaloBusinessException
	{
		// YTODO Auto-generated method stub
		final Item item = getItem(event);
		if (item instanceof Consignment)
		{
			((Consignment) item).setStatus(EnumerationManager.getInstance().getEnumerationValue(ConsignmentStatus._TYPECODE,
					ConsignmentStatus.ACCEPTED.getCode()));
			for (final ConsignmentProcess process : ((Consignment) item).getConsignmentProcesses())
			{
				getBusinessProcessService().triggerEvent(process.getCode() + "_ConsignmentOrderAccepted");
			}
			return new ActionResult(ActionResult.OK, true, false);
		}
		return new ActionResult(ActionResult.FAILED, false, false);
	}

	protected BusinessProcessService getBusinessProcessService()
	{
		return Registry.getApplicationContext().getBean("businessProcessService", BusinessProcessService.class);
	}
}
