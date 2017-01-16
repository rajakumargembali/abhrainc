/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * ----------------------------------------------------------------
 */
package com.abhrainc.core.jalo;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.abhrainc.core.constants.GeneratedAbhraincCoreConstants.Attributes.Order;
import com.sun.org.omg.CORBA.AttributeMode;


/**
 * Generated class for type {@link com.abhrainc.core.jalo.AbhraincOrder AbhraincOrder}.
 */
@SuppressWarnings(
{ "deprecation", "unused", "cast", "PMD" })
public abstract class GeneratedAbhraincOrder extends Order
{
	/** Qualifier of the <code>AbhraincOrder.expectedDeliveryDate</code> attribute **/
	public static final String EXPECTEDDELIVERYDATE = "expectedDeliveryDate";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(Order.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(EXPECTEDDELIVERYDATE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}

	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}

	/**
	 * <i>Generated method</i> - Getter of the <code>AbhraincOrder.expectedDeliveryDate</code> attribute.
	 * 
	 * @return the expectedDeliveryDate - Used to display the Expected Delivery Date
	 */
	public Date getExpectedDeliveryDate(final SessionContext ctx)
	{
		return (Date) getProperty(ctx, EXPECTEDDELIVERYDATE);
	}

	/**
	 * <i>Generated method</i> - Getter of the <code>AbhraincOrder.expectedDeliveryDate</code> attribute.
	 * 
	 * @return the expectedDeliveryDate - Used to display the Expected Delivery Date
	 */
	public Date getExpectedDeliveryDate()
	{
		return getExpectedDeliveryDate(getSession().getSessionContext());
	}

	/**
	 * <i>Generated method</i> - Setter of the <code>AbhraincOrder.expectedDeliveryDate</code> attribute.
	 * 
	 * @param value
	 *           the expectedDeliveryDate - Used to display the Expected Delivery Date
	 */
	public void setExpectedDeliveryDate(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, EXPECTEDDELIVERYDATE, value);
	}

	/**
	 * <i>Generated method</i> - Setter of the <code>AbhraincOrder.expectedDeliveryDate</code> attribute.
	 * 
	 * @param value
	 *           the expectedDeliveryDate - Used to display the Expected Delivery Date
	 */
	public void setExpectedDeliveryDate(final Date value)
	{
		setExpectedDeliveryDate(getSession().getSessionContext(), value);
	}

}
