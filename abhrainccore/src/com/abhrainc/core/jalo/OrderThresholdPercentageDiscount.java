package com.abhrainc.core.jalo;

import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.promotions.jalo.PromotionResult;
import de.hybris.platform.promotions.result.PromotionEvaluationContext;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;


public class OrderThresholdPercentageDiscount extends GeneratedOrderThresholdPercentageDiscount
{
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger(OrderThresholdPercentageDiscount.class.getName());

	@Override
	public List<PromotionResult> evaluate(final SessionContext ctx, final PromotionEvaluationContext promoContext)
	{

		return null;
	}

	@Override
	public String getResultDescription(final SessionContext ctx, final PromotionResult promotionResult, final Locale locale)
	{

		return "";
	}
}
