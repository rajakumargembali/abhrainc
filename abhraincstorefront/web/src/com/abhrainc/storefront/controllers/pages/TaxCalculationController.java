/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.order.impl.DefaultCalculationService;
import de.hybris.platform.order.strategies.calculation.FindDeliveryCostStrategy;
import de.hybris.platform.order.strategies.calculation.FindDiscountValuesStrategy;
import de.hybris.platform.order.strategies.calculation.FindPaymentCostStrategy;
import de.hybris.platform.order.strategies.calculation.FindPriceStrategy;
import de.hybris.platform.order.strategies.calculation.FindTaxValuesStrategy;
import de.hybris.platform.order.strategies.calculation.OrderRequiresCalculationStrategy;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.util.DiscountValue;
import de.hybris.platform.util.TaxValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;


/**
 * @author sujan
 *
 */
public class TaxCalculationController extends DefaultCalculationService
{
	//by sujan

	private static final Logger LOG = Logger.getLogger(DefaultCalculationService.class);
	private List<FindTaxValuesStrategy> findTaxesStrategies;
	private List<FindDiscountValuesStrategy> findDiscountsStrategies;
	private FindPriceStrategy findPriceStrategy;
	private FindDeliveryCostStrategy findDeliveryCostStrategy;
	private FindPaymentCostStrategy findPaymentCostStrategy;
	private OrderRequiresCalculationStrategy orderRequiresCalculationStrategy;

	private CommonI18NService commonI18NService;
	private final boolean taxFreeEntrySupport = false;

	@Override
	public void calculateTotals(final AbstractOrderEntryModel entry, final boolean recalculate)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(entry))
		{
			final AbstractOrderModel order = entry.getOrder();
			final CurrencyModel curr = order.getCurrency();
			final int digits = curr.getDigits().intValue();
			final double totalPriceWithoutDiscount = commonI18NService.roundCurrency(entry.getBasePrice().doubleValue()
					* entry.getQuantity().longValue(), digits);
			final double quantity = entry.getQuantity().doubleValue();
			/*
			 * apply discounts (will be rounded each) convert absolute discount values in case their currency doesn't match
			 * the order currency
			 */
			//YTODO : use CalculatinService methods to apply discounts
			final List appliedDiscounts = DiscountValue.apply(quantity, totalPriceWithoutDiscount, digits,
					convertDiscountValues(order, entry.getDiscountValues()), curr.getIsocode());
			entry.setDiscountValues(appliedDiscounts);
			double totalPrice = totalPriceWithoutDiscount;
			for (final Iterator it = appliedDiscounts.iterator(); it.hasNext();)
			{
				totalPrice -= ((DiscountValue) it.next()).getAppliedValue();
			}
			// set total price
			entry.setTotalPrice(Double.valueOf(totalPrice));
			// apply tax values too
			//YTODO : use CalculatinService methods to apply taxes
			calculateTotalTaxValues(entry);
			getModelService().save(entry);
			setCalculatedStatus(entry);
		}
	}

	protected void calculateTotalTaxValues(final AbstractOrderEntryModel entry)
	{
		final AbstractOrderModel order = entry.getOrder();
		final double quantity = entry.getQuantity().doubleValue();
		final double totalPrice = entry.getTotalPrice().doubleValue();
		final CurrencyModel curr = order.getCurrency();
		final int digits = curr.getDigits().intValue();

		entry.setTaxValues(TaxValue.apply(quantity, totalPrice, digits, entry.getTaxValues(), order.getNet().booleanValue(),
				curr.getIsocode()));
	}

	private void setCalculatedStatus(final AbstractOrderEntryModel entry)
	{
		entry.setCalculated(Boolean.TRUE);
		getModelService().save(entry);
	}
/*	@Override
	protected void calculateTotals(final AbstractOrderModel order, final boolean recalculate,
			final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap) throws CalculationException
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			final CurrencyModel curr = order.getCurrency();
			final int digits = curr.getDigits().intValue();
			// subtotal
			final double subtotal = order.getSubtotal().doubleValue();
			// discounts

			final double totalDiscounts = calculateDiscountValues(order, recalculate);
			final double roundedTotalDiscounts = commonI18NService.roundCurrency(totalDiscounts, digits);
			order.setTotalDiscounts(Double.valueOf(roundedTotalDiscounts));
			// set total
			final double total = subtotal + order.getPaymentCost().doubleValue() + order.getDeliveryCost().doubleValue()
					- roundedTotalDiscounts;
			final double totalRounded = commonI18NService.roundCurrency(total, digits);
			order.setTotalPrice(Double.valueOf(totalRounded));
			// taxes
			final double totalTaxes = calculateTotalTaxValues(//
					order, recalculate, //
					digits, //
					getTaxCorrectionFactor(taxValueMap, subtotal, total, order), //
					taxValueMap);//
			final double totalRoundedTaxes = commonI18NService.roundCurrency(totalTaxes, digits);
			order.setTotalTax(Double.valueOf(totalRoundedTaxes));
			getModelService().save(order);
			setCalculatedStatus(order);
		}
	}


	private void setCalculatedStatus(final AbstractOrderModel order)
	{
		order.setCalculated(Boolean.TRUE);
		getModelService().save(order);
		final List<AbstractOrderEntryModel> entries = order.getEntries();
		if (entries != null)
		{
			for (final AbstractOrderEntryModel entry : entries)
			{
				entry.setCalculated(Boolean.TRUE);
			}
			getModelService().saveAll(entries);
		}
	}

	@Override
	protected double calculateTotalTaxValues(final AbstractOrderModel order, final boolean recalculate, final int digits,
			final double taxAdjustmentFactor, final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap)
	{
		if (recalculate || orderRequiresCalculationStrategy.requiresCalculation(order))
		{
			final CurrencyModel curr = order.getCurrency();
			final String iso = curr.getIsocode();
			//Do we still need it?
			//removeAllTotalTaxValues();
			final boolean net = order.getNet().booleanValue();
			double totalTaxes = 0.0;
			// compute absolute taxes if necessary
			if (MapUtils.isNotEmpty(taxValueMap))
			{
				// adjust total taxes if additional costs or discounts are present
				//	create tax values which contains applied values too
				final Collection orderTaxValues = new ArrayList<TaxValue>(taxValueMap.size());
				for (final Map.Entry<TaxValue, Map<Set<TaxValue>, Double>> taxValueEntry : taxValueMap.entrySet())
				{
					// e.g. VAT_FULL 19%
					final TaxValue unappliedTaxValue = taxValueEntry.getKey();
					// e.g. { (VAT_FULL 19%, CUSTOM 2%) -> 10EUR, (VAT_FULL) -> 20EUR }
					// or, in case of absolute taxes one single entry
					// { (ABS_1 4,50EUR) -> 2 (pieces) }
					final Map<Set<TaxValue>, Double> taxGroups = taxValueEntry.getValue();

					final TaxValue appliedTaxValue;

					if (unappliedTaxValue.isAbsolute())
					{
						// absolute tax entries ALWAYS map to a single-entry map -> we'll use a shortcut here:
						final double quantitySum = taxGroups.entrySet().iterator().next().getValue().doubleValue();
						appliedTaxValue = calculateAbsoluteTotalTaxValue(curr, iso, digits, net, unappliedTaxValue, quantitySum);
					}
					else if (net)
					{
						appliedTaxValue = applyNetMixedRate(unappliedTaxValue, taxGroups, digits, taxAdjustmentFactor);
					}
					else
					{
						appliedTaxValue = applyGrossMixedRate(unappliedTaxValue, taxGroups, digits, taxAdjustmentFactor);
					}
					totalTaxes += appliedTaxValue.getAppliedValue();
					orderTaxValues.add(appliedTaxValue);
				}
				order.setTotalTaxValues(orderTaxValues);
			}
			return totalTaxes;
		}
		else
		{
			return order.getTotalTax().doubleValue();
		}
	}

	private TaxValue applyGrossMixedRate(final TaxValue unappliedTaxValue, final Map<Set<TaxValue>, Double> taxGroups,
			final int digits, final double taxAdjustmentFactor)
	{
		if (unappliedTaxValue.isAbsolute())
		{
			throw new IllegalStateException("AbstractOrder.applyGrossMixedRate(..) cannot be called for absolute tax value!");
		}
		final double singleTaxRate = unappliedTaxValue.getValue();
		double appliedTaxValueNotRounded = 0.0;
		for (final Map.Entry<Set<TaxValue>, Double> taxGroupEntry : taxGroups.entrySet())
		{
			final double groupTaxesRate = TaxValue.sumRelativeTaxValues(taxGroupEntry.getKey());
			final double taxGroupPrice = taxGroupEntry.getValue().doubleValue();

			appliedTaxValueNotRounded += (taxGroupPrice * singleTaxRate) / (100.0 + groupTaxesRate);
		}

		//adjust taxes according to global discounts / costs
		appliedTaxValueNotRounded = appliedTaxValueNotRounded * taxAdjustmentFactor;

		return new TaxValue(//
				unappliedTaxValue.getCode(), //
				unappliedTaxValue.getValue(), //
				false, //
				// always round (even if digits are 0) since relative taxes result in unwanted precision !!!
				commonI18NService.roundCurrency(appliedTaxValueNotRounded, Math.max(digits, 0)), //
				null //
		);
	}

	private TaxValue applyNetMixedRate(final TaxValue unappliedTaxValue, final Map<Set<TaxValue>, Double> taxGroups,
			final int digits, final double taxAdjustmentFactor)
	{
		if (unappliedTaxValue.isAbsolute())
		{
			throw new IllegalStateException("cannot applyGrossMixedRate(..) cannot be called on absolute tax value!");
		}

		// In NET mode we don't care for tax groups since they're only needed to calculated *incldued* taxes!
		// Here we just sum up all group totals...
		double entriesTotalPrice = 0.0;
		for (final Map.Entry<Set<TaxValue>, Double> taxGroupEntry : taxGroups.entrySet())
		{
			entriesTotalPrice += taxGroupEntry.getValue().doubleValue();
		}
		// and apply them in one go:
		return unappliedTaxValue.apply(1.0, entriesTotalPrice * taxAdjustmentFactor, digits, true, null);
	}

	private double getTaxCorrectionFactor(final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap, final double subtotal,
			final double total, final AbstractOrderModel order) throws CalculationException
	{
		// default: adjust taxes relative to total-subtotal ratio
		double factor = subtotal != 0.0 ? (total / subtotal) : 1.0;

		if (mustHandleTaxFreeEntries(taxValueMap, subtotal, order))
		{
			final double taxFreeSubTotal = getTaxFreeSubTotal(order);

			final double taxedTotal = total - taxFreeSubTotal;
			final double taxedSubTotal = subtotal - taxFreeSubTotal;

			// illegal state: taxed subtotal is <= 0 -> cannot calculate with that
			if (taxedSubTotal <= 0)
			{
				throw new CalculationException("illegal taxed subtotal " + taxedSubTotal + ", must be > 0");
			}
			// illegal state: taxed total is <= 0 -> no sense in having negative taxes (factor would become negative!)
			if (taxedTotal <= 0)
			{
				throw new CalculationException("illegal taxed total " + taxedTotal + ", must be > 0");
			}
			factor = taxedSubTotal != 0.0 ? (taxedTotal / taxedSubTotal) : 1.0;
		}
		return factor;
	}

	private boolean mustHandleTaxFreeEntries(final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap, final double subtotal,
			final AbstractOrderModel order)
	{
		return MapUtils.isNotEmpty(taxValueMap) // got taxes at all
				&& taxFreeEntrySupport // mode is enabled
				&& !isAllEntriesTaxed(taxValueMap, subtotal, order); // check sums whether some entries are contributing to tax map
	}

	private boolean isAllEntriesTaxed(final Map<TaxValue, Map<Set<TaxValue>, Double>> taxValueMap, final double subtotal,
			final AbstractOrderModel order)
	{
		double sum = 0.0;
		final Set<Set<TaxValue>> consumedTaxGroups = new HashSet<Set<TaxValue>>();
		for (final Map.Entry<TaxValue, Map<Set<TaxValue>, Double>> taxEntry : taxValueMap.entrySet())
		{
			for (final Map.Entry<Set<TaxValue>, Double> taxGroupEntry : taxEntry.getValue().entrySet())
			{
				if (consumedTaxGroups.add(taxGroupEntry.getKey())) // avoid duplicate occurrences of the same tax group
				{
					sum += taxGroupEntry.getValue().doubleValue();
				}
			}
		}
		// delta ( 2 digits ) == 10 ^-3 == 0.001
		final double allowedDelta = Math.pow(10, -1 * (order.getCurrency().getDigits().intValue() + 1));
		return Math.abs(subtotal - sum) <= allowedDelta;
	}

	private double getTaxFreeSubTotal(final AbstractOrderModel order)
	{
		double sum = 0;
		for (final AbstractOrderEntryModel e : order.getEntries())
		{
			if (CollectionUtils.isEmpty(e.getTaxValues()))
			{
				sum += e.getTotalPrice().doubleValue();
			}
		}
		return sum;
	}

*/
}
