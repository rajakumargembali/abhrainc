/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.ordersplitting.ConsignmentCreationException;
import de.hybris.platform.ordersplitting.impl.DefaultConsignmentService;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;


/**
 * @author sujan
 *
 */
public class CreateConsignemntForCOD extends DefaultConsignmentService
{
	private ModelService modelService;
	private ConsignmentStatus initialConsignmentStatus = ConsignmentStatus.READY;

	@Override
	public ConsignmentModel createConsignment(final AbstractOrderModel order, final String code,
			final List<AbstractOrderEntryModel> orderEntries) throws ConsignmentCreationException
	{
		final ConsignmentModel cons = getModelService().create(ConsignmentModel.class);
		cons.setStatus(getInitialConsignmentStatus());
		cons.setConsignmentEntries(new HashSet<ConsignmentEntryModel>());
		cons.setCode(code);
		cons.setOrder(order);

		for (final AbstractOrderEntryModel orderEntry : orderEntries)
		{
			final ConsignmentEntryModel entry = getModelService().create(ConsignmentEntryModel.class);
			entry.setOrderEntry(orderEntry);
			entry.setQuantity(orderEntry.getQuantity());
			entry.setConsignment(cons);
			cons.getConsignmentEntries().add(entry);
		}

		return cons;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	@Override
	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	protected ConsignmentStatus getInitialConsignmentStatus()
	{
		return initialConsignmentStatus;
	}

	// Optional Configuration
	public void setInitialConsignmentStatus(final ConsignmentStatus initialConsignmentStatus)
	{
		this.initialConsignmentStatus = initialConsignmentStatus;
	}
}
