package com.abhrainc.core.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.order.Order;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.ordersplitting.jalo.Consignment;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.abhrainc.core.constants.AbhraincCoreConstants;


/**
 * Generated class for type <code>AbhraincCoreManager</code>.
 */
@SuppressWarnings(
{ "deprecation", "unused", "cast", "PMD" })
public abstract class GeneratedAbhraincCoreManager extends Extension
{
	protected static final Map<String, Map<String, AttributeMode>> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, Map<String, AttributeMode>> ttmp = new HashMap();
		Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put("expectedDeliveryDate", AttributeMode.INITIAL);
		tmp.put("orderExpectedDeliveryDate", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.order.Order", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("trackingEmailSent", AttributeMode.INITIAL);
		tmp.put("isDeliveryEmailSent", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.ordersplitting.jalo.Consignment", Collections.unmodifiableMap(tmp));
		tmp = new HashMap<String, AttributeMode>();
		tmp.put("isEmailActivated", AttributeMode.INITIAL);
		ttmp.put("de.hybris.platform.jalo.user.User", Collections.unmodifiableMap(tmp));
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

	public ApparelProduct createApparelProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			final ComposedType type = getTenant().getJaloConnection().getTypeManager()
					.getComposedType(AbhraincCoreConstants.TC.APPARELPRODUCT);
			return (ApparelProduct) type.newInstance(ctx, attributeValues);
		}
		catch (final JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ? (RuntimeException) cause
					: new JaloSystemException(cause, cause.getMessage(), e.getErrorCode()));
		}
		catch (final JaloBusinessException e)
		{
			throw new JaloSystemException(e, "error creating ApparelProduct : " + e.getMessage(), 0);
		}
	}

	public ApparelProduct createApparelProduct(final Map attributeValues)
	{
		return createApparelProduct(getSession().getSessionContext(), attributeValues);
	}

	public ApparelSizeVariantProduct createApparelSizeVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			final ComposedType type = getTenant().getJaloConnection().getTypeManager()
					.getComposedType(AbhraincCoreConstants.TC.APPARELSIZEVARIANTPRODUCT);
			return (ApparelSizeVariantProduct) type.newInstance(ctx, attributeValues);
		}
		catch (final JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ? (RuntimeException) cause
					: new JaloSystemException(cause, cause.getMessage(), e.getErrorCode()));
		}
		catch (final JaloBusinessException e)
		{
			throw new JaloSystemException(e, "error creating ApparelSizeVariantProduct : " + e.getMessage(), 0);
		}
	}

	public ApparelSizeVariantProduct createApparelSizeVariantProduct(final Map attributeValues)
	{
		return createApparelSizeVariantProduct(getSession().getSessionContext(), attributeValues);
	}

	public ApparelStyleVariantProduct createApparelStyleVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			final ComposedType type = getTenant().getJaloConnection().getTypeManager()
					.getComposedType(AbhraincCoreConstants.TC.APPARELSTYLEVARIANTPRODUCT);
			return (ApparelStyleVariantProduct) type.newInstance(ctx, attributeValues);
		}
		catch (final JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ? (RuntimeException) cause
					: new JaloSystemException(cause, cause.getMessage(), e.getErrorCode()));
		}
		catch (final JaloBusinessException e)
		{
			throw new JaloSystemException(e, "error creating ApparelStyleVariantProduct : " + e.getMessage(), 0);
		}
	}

	public ApparelStyleVariantProduct createApparelStyleVariantProduct(final Map attributeValues)
	{
		return createApparelStyleVariantProduct(getSession().getSessionContext(), attributeValues);
	}

	public ElectronicsColorVariantProduct createElectronicsColorVariantProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			final ComposedType type = getTenant().getJaloConnection().getTypeManager()
					.getComposedType(AbhraincCoreConstants.TC.ELECTRONICSCOLORVARIANTPRODUCT);
			return (ElectronicsColorVariantProduct) type.newInstance(ctx, attributeValues);
		}
		catch (final JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ? (RuntimeException) cause
					: new JaloSystemException(cause, cause.getMessage(), e.getErrorCode()));
		}
		catch (final JaloBusinessException e)
		{
			throw new JaloSystemException(e, "error creating ElectronicsColorVariantProduct : " + e.getMessage(), 0);
		}
	}

	public ElectronicsColorVariantProduct createElectronicsColorVariantProduct(final Map attributeValues)
	{
		return createElectronicsColorVariantProduct(getSession().getSessionContext(), attributeValues);
	}

	public GoogleProductSearch createGoogleProductSearch(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			final ComposedType type = getTenant().getJaloConnection().getTypeManager()
					.getComposedType(AbhraincCoreConstants.TC.GOOGLEPRODUCTSEARCH);
			return (GoogleProductSearch) type.newInstance(ctx, attributeValues);
		}
		catch (final JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ? (RuntimeException) cause
					: new JaloSystemException(cause, cause.getMessage(), e.getErrorCode()));
		}
		catch (final JaloBusinessException e)
		{
			throw new JaloSystemException(e, "error creating GoogleProductSearch : " + e.getMessage(), 0);
		}
	}

	public GoogleProductSearch createGoogleProductSearch(final Map attributeValues)
	{
		return createGoogleProductSearch(getSession().getSessionContext(), attributeValues);
	}

	public OrderThresholdPercentageDiscount createOrderThresholdPercentageDiscount(final SessionContext ctx,
			final Map attributeValues)
	{
		try
		{
			final ComposedType type = getTenant().getJaloConnection().getTypeManager()
					.getComposedType(AbhraincCoreConstants.TC.ORDERTHRESHOLDPERCENTAGEDISCOUNT);
			return (OrderThresholdPercentageDiscount) type.newInstance(ctx, attributeValues);
		}
		catch (final JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ? (RuntimeException) cause
					: new JaloSystemException(cause, cause.getMessage(), e.getErrorCode()));
		}
		catch (final JaloBusinessException e)
		{
			throw new JaloSystemException(e, "error creating OrderThresholdPercentageDiscount : " + e.getMessage(), 0);
		}
	}

	public OrderThresholdPercentageDiscount createOrderThresholdPercentageDiscount(final Map attributeValues)
	{
		return createOrderThresholdPercentageDiscount(getSession().getSessionContext(), attributeValues);
	}


	public Date getExpectedDeliveryDate(final SessionContext ctx, final Order item)
	{
		return (Date) item.getProperty(ctx, AbhraincCoreConstants.Attributes.Order.EXPECTEDDELIVERYDATE);
	}


	public Date getExpectedDeliveryDate(final Order item)
	{
		return getExpectedDeliveryDate(getSession().getSessionContext(), item);
	}

	public void setExpectedDeliveryDate(final SessionContext ctx, final Order item, final Date value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.Order.EXPECTEDDELIVERYDATE, value);
	}


	public void setExpectedDeliveryDate(final Order item, final Date value)
	{
		setExpectedDeliveryDate(getSession().getSessionContext(), item, value);
	}

	@Override
	public String getName()
	{
		return AbhraincCoreConstants.EXTENSIONNAME;
	}


	public Boolean isIsDeliveryEmailSent(final SessionContext ctx, final Consignment item)
	{
		return (Boolean) item.getProperty(ctx, AbhraincCoreConstants.Attributes.Consignment.ISDELIVERYEMAILSENT);
	}

	public Boolean isIsDeliveryEmailSent(final Consignment item)
	{
		return isIsDeliveryEmailSent(getSession().getSessionContext(), item);
	}

	public boolean isIsDeliveryEmailSentAsPrimitive(final SessionContext ctx, final Consignment item)
	{
		final Boolean value = isIsDeliveryEmailSent(ctx, item);
		return value != null ? value.booleanValue() : false;
	}

	public boolean isIsDeliveryEmailSentAsPrimitive(final Consignment item)
	{
		return isIsDeliveryEmailSentAsPrimitive(getSession().getSessionContext(), item);
	}

	public void setIsDeliveryEmailSent(final SessionContext ctx, final Consignment item, final Boolean value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.Consignment.ISDELIVERYEMAILSENT, value);
	}

	public void setIsDeliveryEmailSent(final Consignment item, final Boolean value)
	{
		setIsDeliveryEmailSent(getSession().getSessionContext(), item, value);
	}

	public void setIsDeliveryEmailSent(final SessionContext ctx, final Consignment item, final boolean value)
	{
		setIsDeliveryEmailSent(ctx, item, Boolean.valueOf(value));
	}

	public void setIsDeliveryEmailSent(final Consignment item, final boolean value)
	{
		setIsDeliveryEmailSent(getSession().getSessionContext(), item, value);
	}


	public Boolean isIsDeliveryEmailSent(final SessionContext ctx, final Consignment item)
	{
		return (Boolean) item.getProperty(ctx, AbhraincCoreConstants.Attributes.Consignment.ISDELIVERYEMAILSENT);
	}


	public Boolean isIsDeliveryEmailSent(final Consignment item)
	{
		return isIsDeliveryEmailSent(getSession().getSessionContext(), item);
	}

	public boolean isIsDeliveryEmailSentAsPrimitive(final SessionContext ctx, final Consignment item)
	{
		final Boolean value = isIsDeliveryEmailSent(ctx, item);
		return value != null ? value.booleanValue() : false;
	}

	public boolean isIsDeliveryEmailSentAsPrimitive(final Consignment item)
	{
		return isIsDeliveryEmailSentAsPrimitive(getSession().getSessionContext(), item);
	}

	public void setIsDeliveryEmailSent(final SessionContext ctx, final Consignment item, final Boolean value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.Consignment.ISDELIVERYEMAILSENT, value);
	}

	public void setIsDeliveryEmailSent(final Consignment item, final Boolean value)
	{
		setIsDeliveryEmailSent(getSession().getSessionContext(), item, value);
	}

	public void setIsDeliveryEmailSent(final SessionContext ctx, final Consignment item, final boolean value)
	{
		setIsDeliveryEmailSent(ctx, item, Boolean.valueOf(value));
	}

	public void setIsDeliveryEmailSent(final Consignment item, final boolean value)
	{
		setIsDeliveryEmailSent(getSession().getSessionContext(), item, value);
	}

	public Boolean isIsEmailActivated(final SessionContext ctx, final User item)
	{
		return (Boolean) item.getProperty(ctx, AbhraincCoreConstants.Attributes.User.ISEMAILACTIVATED);
	}

	public Boolean isIsEmailActivated(final User item)
	{
		return isIsEmailActivated(getSession().getSessionContext(), item);
	}

	public boolean isIsEmailActivatedAsPrimitive(final SessionContext ctx, final User item)
	{
		final Boolean value = isIsEmailActivated(ctx, item);
		return value != null ? value.booleanValue() : false;
	}

	public boolean isIsEmailActivatedAsPrimitive(final User item)
	{
		return isIsEmailActivatedAsPrimitive(getSession().getSessionContext(), item);
	}

	public void setIsEmailActivated(final SessionContext ctx, final User item, final Boolean value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.User.ISEMAILACTIVATED, value);
	}

	public void setIsEmailActivated(final User item, final Boolean value)
	{
		setIsEmailActivated(getSession().getSessionContext(), item, value);
	}

	public void setIsEmailActivated(final SessionContext ctx, final User item, final boolean value)
	{
		setIsEmailActivated(ctx, item, Boolean.valueOf(value));
	}

	public void setIsEmailActivated(final User item, final boolean value)
	{
		setIsEmailActivated(getSession().getSessionContext(), item, value);
	}

	public Date getOrderExpectedDeliveryDate(final SessionContext ctx, final Order item)
	{
		return (Date) item.getProperty(ctx, AbhraincCoreConstants.Attributes.Order.ORDEREXPECTEDDELIVERYDATE);
	}

	public Date getOrderExpectedDeliveryDate(final Order item)
	{
		return getOrderExpectedDeliveryDate(getSession().getSessionContext(), item);
	}

	public void setOrderExpectedDeliveryDate(final SessionContext ctx, final Order item, final Date value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.Order.ORDEREXPECTEDDELIVERYDATE, value);
	}

	public void setOrderExpectedDeliveryDate(final Order item, final Date value)
	{
		setOrderExpectedDeliveryDate(getSession().getSessionContext(), item, value);
	}

	public Boolean isTrackingEmailSent(final SessionContext ctx, final Consignment item)
	{
		return (Boolean) item.getProperty(ctx, AbhraincCoreConstants.Attributes.Consignment.TRACKINGEMAILSENT);
	}

	public Boolean isTrackingEmailSent(final Consignment item)
	{
		return isTrackingEmailSent(getSession().getSessionContext(), item);
	}

	public boolean isTrackingEmailSentAsPrimitive(final SessionContext ctx, final Consignment item)
	{
		final Boolean value = isTrackingEmailSent(ctx, item);
		return value != null ? value.booleanValue() : false;
	}

	public boolean isTrackingEmailSentAsPrimitive(final Consignment item)
	{
		return isTrackingEmailSentAsPrimitive(getSession().getSessionContext(), item);
	}

	public void setTrackingEmailSent(final SessionContext ctx, final Consignment item, final Boolean value)
	{
		item.setProperty(ctx, AbhraincCoreConstants.Attributes.Consignment.TRACKINGEMAILSENT, value);
	}

	public void setTrackingEmailSent(final Consignment item, final Boolean value)
	{
		setTrackingEmailSent(getSession().getSessionContext(), item, value);
	}

	public void setTrackingEmailSent(final SessionContext ctx, final Consignment item, final boolean value)
	{
		setTrackingEmailSent(ctx, item, Boolean.valueOf(value));
	}

	public void setTrackingEmailSent(final Consignment item, final boolean value)
	{
		setTrackingEmailSent(getSession().getSessionContext(), item, value);
	}

}
