/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 15 Jan, 2017 7:00:48 PM                     ---
 * ----------------------------------------------------------------
 */
package com.abhrainc.core.jalo;

import com.abhrainc.core.constants.AbhraincCoreConstants;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.order.Order;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type {@link com.abhrainc.core.jalo.OrderTrackingEmailProcess OrderTrackingEmailProcess}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedOrderTrackingEmailProcess extends Order
{
	/** Qualifier of the <code>OrderTrackingEmailProcess.AbhraincOrderStatus</code> attribute **/
	public static final String ABHRAINCORDERSTATUS = "AbhraincOrderStatus";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(Order.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(ABHRAINCORDERSTATUS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingEmailProcess.AbhraincOrderStatus</code> attribute.
	 * @return the AbhraincOrderStatus - AbhraincOrderStatus
	 */
	public EnumerationValue getAbhraincOrderStatus(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, ABHRAINCORDERSTATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>OrderTrackingEmailProcess.AbhraincOrderStatus</code> attribute.
	 * @return the AbhraincOrderStatus - AbhraincOrderStatus
	 */
	public EnumerationValue getAbhraincOrderStatus()
	{
		return getAbhraincOrderStatus( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingEmailProcess.AbhraincOrderStatus</code> attribute. 
	 * @param value the AbhraincOrderStatus - AbhraincOrderStatus
	 */
	public void setAbhraincOrderStatus(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, ABHRAINCORDERSTATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>OrderTrackingEmailProcess.AbhraincOrderStatus</code> attribute. 
	 * @param value the AbhraincOrderStatus - AbhraincOrderStatus
	 */
	public void setAbhraincOrderStatus(final EnumerationValue value)
	{
		setAbhraincOrderStatus( getSession().getSessionContext(), value );
	}
	
}
