/**
 *
 */
package com.abhrainc.fulfilmentprocess.actions.consignment;

import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.action.AbstractAction;
import de.hybris.platform.task.RetryLaterException;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * @author sujan
 *
 */
public class OrderDispatchedMessageAction extends AbstractAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(OrderDispatchedMessageAction.class);

	public enum Transition
	{
		OK, CANCEL, ERROR;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.processengine.spring.Action#execute(de.hybris.platform.processengine.model.
	 * BusinessProcessModel)
	 */
	@Override
	public String execute(final ConsignmentProcessModel process) throws RetryLaterException, Exception
	{
		final ConsignmentModel consignment = process.getConsignment();
		if (consignment != null)
		{
			consignment.setStatus(ConsignmentStatus.DISPATCHED);
			getModelService().save(consignment);
			return Transition.OK.toString();
		}
		LOG.error("Process has no consignment");
		return Transition.ERROR.toString();

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.processengine.spring.Action#getTransitions()
	 */
	@Override
	public Set<String> getTransitions()
	{
		// YTODO Auto-generated method stub
		return Transition.getStringValues();
	}

}
