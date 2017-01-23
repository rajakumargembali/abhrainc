/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.abhrainc.storefront.controllers.pages.checkout.steps;

import java.math.BigDecimal;
import java.util.List;

import com.abhrainc.storefront.controllers.ControllerConstants;


@Controller
@RequestMapping(value = "/checkout/multi/delivery-method")
public class DeliveryMethodCheckoutStepController extends AbstractCheckoutStepController
{
	private static final String DELIVERY_METHOD = "delivery-method";

	@RequestMapping(value = "/choose", method = RequestMethod.GET)
	@RequireHardLogIn
	@Override
	@PreValidateCheckoutStep(checkoutStep = DELIVERY_METHOD)
	public String enterStep(final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		// Try to set default delivery mode
		getCheckoutFacade().setDeliveryModeIfAvailable();




		final CartData cartData = getCheckoutFacade().getCheckoutCart();
		boolean showhomedelievry = true;

		for (final OrderEntryData orderEntryData : cartData.getEntries())
		{
			boolean producteligible = false;
			for (final CategoryData categoryData : orderEntryData.getProduct().getCategories())
			{

				if ((categoryData.getCode().equalsIgnoreCase("Refrigerator"))
						|| (categoryData.getCode().equalsIgnoreCase("Television")))
				{

					producteligible = true;
				}

			}
			if (!producteligible)
			{
				showhomedelievry = false;
			}
		}
		final List<DeliveryModeData> deliveryMethods = (List<DeliveryModeData>) getCheckoutFacade().getSupportedDeliveryModes();
		if (!showhomedelievry)
		{
			deliveryMethods.remove(2);
		}
		else
		{

			BigDecimal value = null;
			String price = "$0.00";


			if (cartData.getTotalPrice().getValue().intValue() <= 50)
			{
				value = new BigDecimal(5);
				price = "$5.00";
			}
			else if (cartData.getTotalPrice().getValue().intValue() > 50 && cartData.getTotalPrice().getValue().intValue() <= 100)
			{
				value = new BigDecimal(2);
				price = "$2.00";
			}
			else
			{
				value = new BigDecimal(0);
				price = "$0.00";
			}
			final PriceData pd = cartData.getDeliveryCost();
			deliveryMethods.get(2).setName(deliveryMethods.get(2).getName().replaceAll("$0.00", price));
			pd.setValue(value);
			getCartFacade().getSessionCart().setDeliveryCost(pd);
			pd.setFormattedValue(price);
			cartData.setDeliveryCost(pd);
		}


		model.addAttribute("cartData", cartData);
		model.addAttribute("deliveryMethods", deliveryMethods);

		this.prepareDataForPage(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getResourceBreadcrumbBuilder().getBreadcrumbs("checkout.multi.deliveryMethod.breadcrumb"));
		model.addAttribute("metaRobots", "noindex,nofollow");
		setCheckoutStepLinksForModel(model, getCheckoutStep());

		return ControllerConstants.Views.Pages.MultiStepCheckout.ChooseDeliveryMethodPage;
	}

	/**
	 * This method gets called when the "Use Selected Delivery Method" button is clicked. It sets the selected delivery
	 * mode on the checkout facade and reloads the page highlighting the selected delivery Mode.
	 *
	 * @param selectedDeliveryMethod
	 *           - the id of the delivery mode.
	 * @return - a URL to the page to load.
	 */
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@RequireHardLogIn
	public String doSelectDeliveryMode(@RequestParam("delivery_method") final String selectedDeliveryMethod)
	{
		if (StringUtils.isNotEmpty(selectedDeliveryMethod))
		{
			getCheckoutFacade().setDeliveryMode(selectedDeliveryMethod);
		}

		return getCheckoutStep().nextStep();
	}

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	@RequireHardLogIn
	@Override
	public String back(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().previousStep();
	}

	@RequestMapping(value = "/next", method = RequestMethod.GET)
	@RequireHardLogIn
	@Override
	public String next(final RedirectAttributes redirectAttributes)
	{
		return getCheckoutStep().nextStep();
	}

	protected CheckoutStep getCheckoutStep()
	{
		return getCheckoutStep(DELIVERY_METHOD);
	}
}
