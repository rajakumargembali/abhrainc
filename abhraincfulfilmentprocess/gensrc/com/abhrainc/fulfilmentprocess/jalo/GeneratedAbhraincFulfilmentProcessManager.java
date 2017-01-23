package com.abhrainc.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.ordersplitting.jalo.ConsignmentProcess;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.abhrainc.fulfilmentprocess.constants.AbhraincFulfilmentProcessConstants;


/**
 * Generated class for type <code>AbhraincFulfilmentProcessManager</code>.
 */
@SuppressWarnings(
{ "deprecation", "unused", "cast", "PMD" })
public abstract class GeneratedAbhraincFulfilmentProcessManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("done", AttributeMode.INITIAL);
		tmp.put("waitingForConsignment", AttributeMode.INITIAL);
		tmp.put("warehouseConsignmentState", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.ordersplitting.jalo.ConsignmentProcess", Collections.unmodifiableMap(tmp));
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}

	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		final Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}

	public Boolean isDone(final SessionContext ctx, final ConsignmentProcess item)
	{
		return (Boolean) item.getProperty(ctx, AbhraincFulfilmentProcessConstants.Attributes.ConsignmentProcess.DONE);
	}

	public Boolean isDone(final ConsignmentProcess item)
	{
		return isDone(getSession().getSessionContext(), item);
	}

	public boolean isDoneAsPrimitive(final SessionContext ctx, final ConsignmentProcess item)
	{
		final Boolean value = isDone(ctx, item);
		return value != null ? value.booleanValue() : false;
	}

	public boolean isDoneAsPrimitive(final ConsignmentProcess item)
	{
		return isDoneAsPrimitive(getSession().getSessionContext(), item);
	}

	public void setDone(final SessionContext ctx, final ConsignmentProcess item, final Boolean value)
	{
		item.setProperty(ctx, AbhraincFulfilmentProcessConstants.Attributes.ConsignmentProcess.DONE, value);
	}

	public void setDone(final ConsignmentProcess item, final Boolean value)
	{
		setDone(getSession().getSessionContext(), item, value);
	}

	public void setDone(final SessionContext ctx, final ConsignmentProcess item, final boolean value)
	{
		setDone(ctx, item, Boolean.valueOf(value));
	}

	public void setDone(final ConsignmentProcess item, final boolean value)
	{
		setDone(getSession().getSessionContext(), item, value);
	}

	@Override
	public String getName()
	{
		return AbhraincFulfilmentProcessConstants.EXTENSIONNAME;
	}

	public Boolean isWaitingForConsignment(final SessionContext ctx, final ConsignmentProcess item)
	{
		return (Boolean) item.getProperty(ctx,
				AbhraincFulfilmentProcessConstants.Attributes.ConsignmentProcess.WAITINGFORCONSIGNMENT);
	}

	public Boolean isWaitingForConsignment(final ConsignmentProcess item)
	{
		return isWaitingForConsignment(getSession().getSessionContext(), item);
	}

	public boolean isWaitingForConsignmentAsPrimitive(final SessionContext ctx, final ConsignmentProcess item)
	{
		final Boolean value = isWaitingForConsignment(ctx, item);
		return value != null ? value.booleanValue() : false;
	}

	public boolean isWaitingForConsignmentAsPrimitive(final ConsignmentProcess item)
	{
		return isWaitingForConsignmentAsPrimitive(getSession().getSessionContext(), item);
	}

	public void setWaitingForConsignment(final SessionContext ctx, final ConsignmentProcess item, final Boolean value)
	{
		item.setProperty(ctx, AbhraincFulfilmentProcessConstants.Attributes.ConsignmentProcess.WAITINGFORCONSIGNMENT, value);
	}

	public void setWaitingForConsignment(final ConsignmentProcess item, final Boolean value)
	{
		setWaitingForConsignment(getSession().getSessionContext(), item, value);
	}

	public void setWaitingForConsignment(final SessionContext ctx, final ConsignmentProcess item, final boolean value)
	{
		setWaitingForConsignment(ctx, item, Boolean.valueOf(value));
	}

	public void setWaitingForConsignment(final ConsignmentProcess item, final boolean value)
	{
		setWaitingForConsignment(getSession().getSessionContext(), item, value);
	}

	public EnumerationValue getWarehouseConsignmentState(final SessionContext ctx, final ConsignmentProcess item)
	{
		return (EnumerationValue) item.getProperty(ctx,
				AbhraincFulfilmentProcessConstants.Attributes.ConsignmentProcess.WAREHOUSECONSIGNMENTSTATE);
	}

	public EnumerationValue getWarehouseConsignmentState(final ConsignmentProcess item)
	{
		return getWarehouseConsignmentState(getSession().getSessionContext(), item);
	}

	public void setWarehouseConsignmentState(final SessionContext ctx, final ConsignmentProcess item, final EnumerationValue value)
	{
		item.setProperty(ctx, AbhraincFulfilmentProcessConstants.Attributes.ConsignmentProcess.WAREHOUSECONSIGNMENTSTATE, value);
	}

	public void setWarehouseConsignmentState(final ConsignmentProcess item, final EnumerationValue value)
	{
		setWarehouseConsignmentState(getSession().getSessionContext(), item, value);
	}

}
