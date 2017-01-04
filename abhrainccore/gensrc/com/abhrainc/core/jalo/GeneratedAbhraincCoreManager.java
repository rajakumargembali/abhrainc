/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
<<<<<<< HEAD
 * --- Generated at Jan 4, 2017 3:08:38 PM                      ---
=======
 * --- Generated at 3 Jan, 2017 4:37:52 PM                      ---
>>>>>>> sujan
 * ----------------------------------------------------------------
 */
package com.abhrainc.core.jalo;

import com.abhrainc.core.constants.AbhraincCoreConstants;
import com.abhrainc.core.jalo.ApparelProduct;
import com.abhrainc.core.jalo.ApparelSizeVariantProduct;
import com.abhrainc.core.jalo.ApparelStyleVariantProduct;
import com.abhrainc.core.jalo.ElectronicsColorVariantProduct;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.order.AbstractOrder;
import de.hybris.platform.jalo.order.Order;
<<<<<<< HEAD
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import java.util.Collections;
import java.util.Date;
=======
import de.hybris.platform.jalo.security.Principal;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.jalo.user.User;
import java.util.Collections;
>>>>>>> sujan
import java.util.HashMap;
import java.util.Map;

/**
 * Generated class for type <code>AbhraincCoreManager</code>.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAbhraincCoreManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
<<<<<<< HEAD
		tmp.put("expectedDeliveryDate", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.Order", Collections.unmodifiableMap(tmp));
=======
		tmp.put("AbhraincOrderStatus", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.Order", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isEmailActivated", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.User", Collections.unmodifiableMap(tmp));
>>>>>>> sujan
		DEFAULT_INITIAL_ATTRIBUTES = ttmp;
	}
	@Override
	public Map<String, AttributeMode> getDefaultAttributeModes(final Class<? extends Item> itemClass)
	{
		Map<String, AttributeMode> ret = new HashMap<>();
		final Map<String, AttributeMode> attr = DEFAULT_INITIAL_ATTRIBUTES.get(itemClass.getName());
		if (attr != null)
		{
			ret.putAll(attr);
		}
		return ret;
	}
	
<<<<<<< HEAD
=======
	/**
	 * <i>Generated method</i> - Getter of the <code>Order.AbhraincOrderStatus</code> attribute.
	 * @return the AbhraincOrderStatus - AbhraincOrderStatus
	 */
	public EnumerationValue getAbhraincOrderStatus(final SessionContext ctx, final Order item)
	{
		return (EnumerationValue)item.getProperty( ctx, AbhraincCoreConstants.Attributes.Order.ABHRAINCORDERSTATUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Order.AbhraincOrderStatus</code> attribute.
	 * @return the AbhraincOrderStatus - AbhraincOrderStatus
	 */
	public EnumerationValue getAbhraincOrderStatus(final Order item)
	{
		return getAbhraincOrderStatus( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Order.AbhraincOrderStatus</code> attribute. 
	 * @param value the AbhraincOrderStatus - AbhraincOrderStatus
	 */
	public void setAbhraincOrderStatus(final SessionContext ctx, final Order item, final EnumerationValue value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.Order.ABHRAINCORDERSTATUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Order.AbhraincOrderStatus</code> attribute. 
	 * @param value the AbhraincOrderStatus - AbhraincOrderStatus
	 */
	public void setAbhraincOrderStatus(final Order item, final EnumerationValue value)
	{
		setAbhraincOrderStatus( getSession().getSessionContext(), item, value );
	}
	
	public AbhraincOrder createAbhraincOrder(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AbhraincCoreConstants.TC.ABHRAINCORDER );
			return (AbhraincOrder)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AbhraincOrder : "+e.getMessage(), 0 );
		}
	}
	
	public AbhraincOrder createAbhraincOrder(final Map attributeValues)
	{
		return createAbhraincOrder( getSession().getSessionContext(), attributeValues );
	}
	
>>>>>>> sujan
	public ApparelProduct createApparelProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AbhraincCoreConstants.TC.APPARELPRODUCT );
			return (ApparelProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelProduct createApparelProduct(final Map attributeValues)
	{
		return createApparelProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ApparelSizeVariantProduct createApparelSizeVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AbhraincCoreConstants.TC.APPARELSIZEVARIANTPRODUCT );
			return (ApparelSizeVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelSizeVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelSizeVariantProduct createApparelSizeVariantProduct(final Map attributeValues)
	{
		return createApparelSizeVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ApparelStyleVariantProduct createApparelStyleVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AbhraincCoreConstants.TC.APPARELSTYLEVARIANTPRODUCT );
			return (ApparelStyleVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ApparelStyleVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ApparelStyleVariantProduct createApparelStyleVariantProduct(final Map attributeValues)
	{
		return createApparelStyleVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public ElectronicsColorVariantProduct createElectronicsColorVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( AbhraincCoreConstants.TC.ELECTRONICSCOLORVARIANTPRODUCT );
			return (ElectronicsColorVariantProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ElectronicsColorVariantProduct : "+e.getMessage(), 0 );
		}
	}
	
	public ElectronicsColorVariantProduct createElectronicsColorVariantProduct(final Map attributeValues)
	{
		return createElectronicsColorVariantProduct( getSession().getSessionContext(), attributeValues );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Order.expectedDeliveryDate</code> attribute.
	 * @return the expectedDeliveryDate - Used to display the Expected Delivery Date
	 */
	public Date getExpectedDeliveryDate(final SessionContext ctx, final Order item)
	{
		return (Date)item.getProperty( ctx, AbhraincCoreConstants.Attributes.Order.EXPECTEDDELIVERYDATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Order.expectedDeliveryDate</code> attribute.
	 * @return the expectedDeliveryDate - Used to display the Expected Delivery Date
	 */
	public Date getExpectedDeliveryDate(final Order item)
	{
		return getExpectedDeliveryDate( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Order.expectedDeliveryDate</code> attribute. 
	 * @param value the expectedDeliveryDate - Used to display the Expected Delivery Date
	 */
	public void setExpectedDeliveryDate(final SessionContext ctx, final Order item, final Date value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.Order.EXPECTEDDELIVERYDATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Order.expectedDeliveryDate</code> attribute. 
	 * @param value the expectedDeliveryDate - Used to display the Expected Delivery Date
	 */
	public void setExpectedDeliveryDate(final Order item, final Date value)
	{
		setExpectedDeliveryDate( getSession().getSessionContext(), item, value );
	}
	
	@Override
	public String getName()
	{
		return AbhraincCoreConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.isEmailActivated</code> attribute.
	 * @return the isEmailActivated - UserEmailSTATUS
	 */
	public Boolean isIsEmailActivated(final SessionContext ctx, final User item)
	{
		return (Boolean)item.getProperty( ctx, AbhraincCoreConstants.Attributes.User.ISEMAILACTIVATED);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.isEmailActivated</code> attribute.
	 * @return the isEmailActivated - UserEmailSTATUS
	 */
	public Boolean isIsEmailActivated(final User item)
	{
		return isIsEmailActivated( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.isEmailActivated</code> attribute. 
	 * @return the isEmailActivated - UserEmailSTATUS
	 */
	public boolean isIsEmailActivatedAsPrimitive(final SessionContext ctx, final User item)
	{
		Boolean value = isIsEmailActivated( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>User.isEmailActivated</code> attribute. 
	 * @return the isEmailActivated - UserEmailSTATUS
	 */
	public boolean isIsEmailActivatedAsPrimitive(final User item)
	{
		return isIsEmailActivatedAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.isEmailActivated</code> attribute. 
	 * @param value the isEmailActivated - UserEmailSTATUS
	 */
	public void setIsEmailActivated(final SessionContext ctx, final User item, final Boolean value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.User.ISEMAILACTIVATED,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.isEmailActivated</code> attribute. 
	 * @param value the isEmailActivated - UserEmailSTATUS
	 */
	public void setIsEmailActivated(final User item, final Boolean value)
	{
		setIsEmailActivated( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.isEmailActivated</code> attribute. 
	 * @param value the isEmailActivated - UserEmailSTATUS
	 */
	public void setIsEmailActivated(final SessionContext ctx, final User item, final boolean value)
	{
		setIsEmailActivated( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>User.isEmailActivated</code> attribute. 
	 * @param value the isEmailActivated - UserEmailSTATUS
	 */
	public void setIsEmailActivated(final User item, final boolean value)
	{
		setIsEmailActivated( getSession().getSessionContext(), item, value );
	}
	
}
