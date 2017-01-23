package com.abhrainc.core.jalo;

import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.promotions.jalo.OrderPromotion;
import de.hybris.platform.promotions.jalo.PromotionPriceRow;
import de.hybris.platform.util.PartOfHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Generated class for type {@link com.abhrainc.core.jalo.OrderThresholdPercentageDiscount
 * OrderThresholdPercentageDiscount}.
 */
@SuppressWarnings(
{ "deprecation", "unused", "cast", "PMD" })
public abstract class GeneratedOrderThresholdPercentageDiscount extends OrderPromotion
{
	/** Qualifier of the <code>OrderThresholdPercentageDiscount.thresholdTotals</code> attribute **/
	public static final String THRESHOLDTOTALS = "thresholdTotals";
	/** Qualifier of the <code>OrderThresholdPercentageDiscount.qualifyingCount</code> attribute **/
	public static final String QUALIFYINGCOUNT = "qualifyingCount";
	/** Qualifier of the <code>OrderThresholdPercentageDiscount.percentageDiscount</code> attribute **/
	public static final String PERCENTAGEDISCOUNT = "percentageDiscount";
	/** Qualifier of the <code>OrderThresholdPercentageDiscount.messageFired</code> attribute **/
	public static final String MESSAGEFIRED = "messageFired";
	/** Qualifier of the <code>OrderThresholdPercentageDiscount.messageCouldHaveFired</code> attribute **/
	public static final String MESSAGECOULDHAVEFIRED = "messageCouldHaveFired";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(OrderPromotion.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(THRESHOLDTOTALS, AttributeMode.INITIAL);
		tmp.put(QUALIFYINGCOUNT, AttributeMode.INITIAL);
		tmp.put(PERCENTAGEDISCOUNT, AttributeMode.INITIAL);
		tmp.put(MESSAGEFIRED, AttributeMode.INITIAL);
		tmp.put(MESSAGECOULDHAVEFIRED, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}

	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}

	public String getMessageCouldHaveFired(final SessionContext ctx)
	{
		if (ctx == null || ctx.getLanguage() == null)
		{
			throw new JaloInvalidParameterException(
					"GeneratedOrderThresholdPercentageDiscount.getMessageCouldHaveFired requires a session language", 0);
		}
		return (String) getLocalizedProperty(ctx, MESSAGECOULDHAVEFIRED);
	}

	public String getMessageCouldHaveFired()
	{
		return getMessageCouldHaveFired(getSession().getSessionContext());
	}

	public Map<Language, String> getAllMessageCouldHaveFired(final SessionContext ctx)
	{
		return (Map<Language, String>) getAllLocalizedProperties(ctx, MESSAGECOULDHAVEFIRED,
				C2LManager.getInstance().getAllLanguages());
	}

	public Map<Language, String> getAllMessageCouldHaveFired()
	{
		return getAllMessageCouldHaveFired(getSession().getSessionContext());
	}

	public void setMessageCouldHaveFired(final SessionContext ctx, final String value)
	{
		if (ctx == null || ctx.getLanguage() == null)
		{
			throw new JaloInvalidParameterException(
					"GeneratedOrderThresholdPercentageDiscount.setMessageCouldHaveFired requires a session language", 0);
		}
		setLocalizedProperty(ctx, MESSAGECOULDHAVEFIRED, value);
	}

	public void setMessageCouldHaveFired(final String value)
	{
		setMessageCouldHaveFired(getSession().getSessionContext(), value);
	}

	public void setAllMessageCouldHaveFired(final SessionContext ctx, final Map<Language, String> value)
	{
		setAllLocalizedProperties(ctx, MESSAGECOULDHAVEFIRED, value);
	}

	public void setAllMessageCouldHaveFired(final Map<Language, String> value)
	{
		setAllMessageCouldHaveFired(getSession().getSessionContext(), value);
	}

	public String getMessageFired(final SessionContext ctx)
	{
		if (ctx == null || ctx.getLanguage() == null)
		{
			throw new JaloInvalidParameterException(
					"GeneratedOrderThresholdPercentageDiscount.getMessageFired requires a session language", 0);
		}
		return (String) getLocalizedProperty(ctx, MESSAGEFIRED);
	}

	public String getMessageFired()
	{
		return getMessageFired(getSession().getSessionContext());
	}

	public Map<Language, String> getAllMessageFired(final SessionContext ctx)
	{
		return (Map<Language, String>) getAllLocalizedProperties(ctx, MESSAGEFIRED, C2LManager.getInstance().getAllLanguages());
	}

	public Map<Language, String> getAllMessageFired()
	{
		return getAllMessageFired(getSession().getSessionContext());
	}

	public void setMessageFired(final SessionContext ctx, final String value)
	{
		if (ctx == null || ctx.getLanguage() == null)
		{
			throw new JaloInvalidParameterException(
					"GeneratedOrderThresholdPercentageDiscount.setMessageFired requires a session language", 0);
		}
		setLocalizedProperty(ctx, MESSAGEFIRED, value);
	}

	public void setMessageFired(final String value)
	{
		setMessageFired(getSession().getSessionContext(), value);
	}

	public void setAllMessageFired(final SessionContext ctx, final Map<Language, String> value)
	{
		setAllLocalizedProperties(ctx, MESSAGEFIRED, value);
	}

	public void setAllMessageFired(final Map<Language, String> value)
	{
		setAllMessageFired(getSession().getSessionContext(), value);
	}

	public Double getPercentageDiscount(final SessionContext ctx)
	{
		return (Double) getProperty(ctx, PERCENTAGEDISCOUNT);
	}

	public Double getPercentageDiscount()
	{
		return getPercentageDiscount(getSession().getSessionContext());
	}

	public double getPercentageDiscountAsPrimitive(final SessionContext ctx)
	{
		final Double value = getPercentageDiscount(ctx);
		return value != null ? value.doubleValue() : 0.0d;
	}

	public double getPercentageDiscountAsPrimitive()
	{
		return getPercentageDiscountAsPrimitive(getSession().getSessionContext());
	}

	public void setPercentageDiscount(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PERCENTAGEDISCOUNT, value);
	}

	public void setPercentageDiscount(final Double value)
	{
		setPercentageDiscount(getSession().getSessionContext(), value);
	}

	public void setPercentageDiscount(final SessionContext ctx, final double value)
	{
		setPercentageDiscount(ctx, Double.valueOf(value));
	}

	public void setPercentageDiscount(final double value)
	{
		setPercentageDiscount(getSession().getSessionContext(), value);
	}

	public Integer getQualifyingCount(final SessionContext ctx)
	{
		return (Integer) getProperty(ctx, QUALIFYINGCOUNT);
	}

	public Integer getQualifyingCount()
	{
		return getQualifyingCount(getSession().getSessionContext());
	}

	public int getQualifyingCountAsPrimitive(final SessionContext ctx)
	{
		final Integer value = getQualifyingCount(ctx);
		return value != null ? value.intValue() : 0;
	}

	public int getQualifyingCountAsPrimitive()
	{
		return getQualifyingCountAsPrimitive(getSession().getSessionContext());
	}

	public void setQualifyingCount(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, QUALIFYINGCOUNT, value);
	}

	public void setQualifyingCount(final Integer value)
	{
		setQualifyingCount(getSession().getSessionContext(), value);
	}

	public void setQualifyingCount(final SessionContext ctx, final int value)
	{
		setQualifyingCount(ctx, Integer.valueOf(value));
	}

	public void setQualifyingCount(final int value)
	{
		setQualifyingCount(getSession().getSessionContext(), value);
	}

	public Collection<PromotionPriceRow> getThresholdTotals(final SessionContext ctx)
	{
		final Collection<PromotionPriceRow> coll = (Collection<PromotionPriceRow>) getProperty(ctx, THRESHOLDTOTALS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}

	public Collection<PromotionPriceRow> getThresholdTotals()
	{
		return getThresholdTotals(getSession().getSessionContext());
	}

	public void setThresholdTotals(final SessionContext ctx, final Collection<PromotionPriceRow> value)
	{
		new PartOfHandler<Collection<PromotionPriceRow>>()
		{
			@Override
			protected Collection<PromotionPriceRow> doGetValue(final SessionContext ctx)
			{
				return getThresholdTotals(ctx);
			}

			@Override
			protected void doSetValue(final SessionContext ctx, final Collection<PromotionPriceRow> _value)
			{
				final Collection<PromotionPriceRow> value = _value;
				setProperty(ctx, THRESHOLDTOTALS, value == null || !value.isEmpty() ? value : null);
			}
		}.setValue(ctx, value);
	}

	public void setThresholdTotals(final Collection<PromotionPriceRow> value)
	{
		setThresholdTotals(getSession().getSessionContext(), value);
	}

}
