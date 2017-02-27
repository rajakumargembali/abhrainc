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

import de.hybris.platform.acceleratorservices.enums.CheckoutPciOptionEnum;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.PlaceOrderForm;
import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.ordersplitting.ConsignmentCreationException;
import de.hybris.platform.ordersplitting.WarehouseService;
import de.hybris.platform.ordersplitting.model.ConsignmentEntryModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.payment.AdapterException;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abhrainc.facades.service.AbhraIncFacadeService;
import com.abhrainc.storefront.controllers.ControllerConstants;


@Controller
@RequestMapping(value = "/checkout/multi/summary")
public class SummaryCheckoutStepController extends AbstractCheckoutStepController
{
	private static final Logger LOGGER = Logger.getLogger(SummaryCheckoutStepController.class);

	private static final String SUMMARY = "summary";

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private ModelService modelService;
	private final ConsignmentStatus initialConsignmentStatus = ConsignmentStatus.READY;


	@Autowired
	BusinessProcessService businessProcessService;

	/**
	 * @return the warehouseService
	 */
	public WarehouseService getWarehouseService()
	{
		return warehouseService;
	}


	/**
	 * @param warehouseService
	 *           the warehouseService to set
	 */
	public void setWarehouseService(final WarehouseService warehouseService)
	{
		this.warehouseService = warehouseService;
	}


	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}


	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


	/**
	 * @return the initialConsignmentStatus
	 */
	public ConsignmentStatus getInitialConsignmentStatus()
	{
		return initialConsignmentStatus;
	}

	@Autowired
	AbhraIncFacadeService abhraIncFacadeService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	@RequireHardLogIn
	@Override
	@PreValidateCheckoutStep(checkoutStep = SUMMARY)
	public String enterStep(final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException, // NOSONAR
			CommerceCartModificationException
	{
		final CartData cartData = getCheckoutFacade().getCheckoutCart();
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				final String productCode = entry.getProduct().getCode();
				final ProductData product = getProductFacade().getProductForCodeAndOptions(productCode,
						Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
				entry.setProduct(product);
			}
		}

		model.addAttribute("cartData", cartData);
		model.addAttribute("allItems", cartData.getEntries());
		model.addAttribute("deliveryAddress", cartData.getDeliveryAddress());
		model.addAttribute("deliveryMode", cartData.getDeliveryMode());
		model.addAttribute("paymentInfo", cartData.getPaymentInfo());

		// Only request the security code if the SubscriptionPciOption is set to Default.
		final boolean requestSecurityCode = CheckoutPciOptionEnum.DEFAULT
				.equals(getCheckoutFlowFacade().getSubscriptionPciOption());
		model.addAttribute("requestSecurityCode", Boolean.valueOf(requestSecurityCode));

		model.addAttribute(new PlaceOrderForm());

		storeCmsPageInModel(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getResourceBreadcrumbBuilder().getBreadcrumbs("checkout.multi.summary.breadcrumb"));
		model.addAttribute("metaRobots", "noindex,nofollow");
		setCheckoutStepLinksForModel(model, getCheckoutStep());
		return ControllerConstants.Views.Pages.MultiStepCheckout.CheckoutSummaryPage;
	}


	@RequestMapping(value = "/placeOrder")
	@RequireHardLogIn
	public String placeOrder(@ModelAttribute("placeOrderForm") final PlaceOrderForm placeOrderForm, final Model model,
			final HttpServletRequest request, final RedirectAttributes redirectModel) throws CMSItemNotFoundException, // NOSONAR
			InvalidCartException, CommerceCartModificationException
	{
		placeOrderForm.setTermsCheck(true);
		final CartModel cartmodel = abhraIncFacadeService.getCartDetails(getCartFacade().getSessionCart().getCode());
		if (validateOrderForm(placeOrderForm, model))
		{
			return enterStep(model, redirectModel);
		}

		//Validate the cart
		if (validateCart(redirectModel))
		{
			// Invalid cart. Bounce back to the cart page.
			return REDIRECT_PREFIX + "/cart";
		}

		// authorize, if failure occurs don't allow to place the order
		boolean isPaymentUthorized = false;
		try
		{
			if (getCheckoutFacade().getCheckoutCart().getPaymentInfo() == null)
			{
				isPaymentUthorized = false;
			}
			else
			{
				isPaymentUthorized = true;
			}

		}
		catch (final AdapterException ae)
		{
			// handle a case where a wrong paymentProvider configurations on the store see getCommerceCheckoutService().getPaymentProvider()
			LOGGER.error(ae.getMessage(), ae);
		}



		if (cartmodel.getIsCashOnDelivery())
		{
			isPaymentUthorized = true;
		}
		if (!isPaymentUthorized)
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.authorization.failed");
			return enterStep(model, redirectModel);
		}
		final OrderData orderData;
		try
		{
			orderData = getCheckoutFacade().placeOrder();
		}
		catch (final Exception e)
		{
			LOGGER.error("Failed to place Order", e);
			GlobalMessages.addErrorMessage(model, "checkout.placeOrder.failed");
			return enterStep(model, redirectModel);
		}

		final AbstractOrderModel orderModel = abhraIncFacadeService.getAbstractorderDetails(orderData);
		final AbstractOrderEntryModel orderEntryModel = abhraIncFacadeService.getorderEntryDetails(orderModel);
		final List<AbstractOrderEntryModel> orderEntries = new ArrayList<>();
		orderEntries.add(orderEntryModel);
		final OrderModel order = abhraIncFacadeService.getOrderDetails(orderData);
		try
		{
			final OrderProcessModel orderProcessModel = businessProcessService.createProcess(
					"orderConfirmationEmailProcess-" + order.getCode() + "-" + System.currentTimeMillis(),
					"orderConfirmationEmailProcess");
			orderProcessModel.setOrder(order);
			final ConsignmentModel consignmentModel = createConsignment(orderModel, orderData.getCode(), orderEntries);
			final List<EmailAddressModel> addressModels = new ArrayList<>();
			final EmailAddressModel addressModel = new EmailAddressModel();
			addressModel.setEmailAddress(consignmentModel.getOrder().getUser().getUid());
			addressModels.add(addressModel);
			final EmailMessageModel emailMessageModel = new EmailMessageModel();
			emailMessageModel.setToAddresses(addressModels);
			businessProcessService.startProcess(orderProcessModel);

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return redirectToOrderConfirmationPage(orderData);
	}

	ConsignmentModel createConsignment(final AbstractOrderModel order, final String code,
			final List<AbstractOrderEntryModel> orderEntries) throws ConsignmentCreationException
	{
		final ConsignmentModel cons = getModelService().create(ConsignmentModel.class);
		cons.setStatus(getInitialConsignmentStatus());
		cons.setConsignmentEntries(new HashSet<ConsignmentEntryModel>());
		cons.setCode(code);
		cons.setOrder(order);

		for (final AbstractOrderEntryModel orderEntry : orderEntries)
		{
			final ConsignmentEntryModel entry = getModelService().create(ConsignmentEntryModel.class);
			entry.setOrderEntry(orderEntry);
			entry.setQuantity(orderEntry.getQuantity());
			entry.setConsignment(cons);
			cons.getConsignmentEntries().add(entry);
		}
		cons.setShippingAddress(order.getDeliveryAddress());
		cons.setWarehouse(getWarehouseService().getWarehouses(order.getEntries()).iterator().next());
		getModelService().save(cons);
		return cons;
	}

	/**
	 * Validates the order form before to filter out invalid order states
	 *
	 * @param placeOrderForm
	 *           The spring form of the order being submitted
	 * @param model
	 *           A spring Model
	 * @return True if the order form is invalid and false if everything is valid.
	 */
	protected boolean validateOrderForm(final PlaceOrderForm placeOrderForm, final Model model)
	{
		final String securityCode = placeOrderForm.getSecurityCode();
		boolean invalid = false;

		if (getCheckoutFlowFacade().hasNoDeliveryAddress())
		{
			GlobalMessages.addErrorMessage(model, "checkout.deliveryAddress.notSelected");
			invalid = true;
		}

		if (getCheckoutFlowFacade().hasNoDeliveryMode())
		{
			GlobalMessages.addErrorMessage(model, "checkout.deliveryMethod.notSelected");
			invalid = true;
		}

		final CartModel cartmodel = abhraIncFacadeService.getCartDetails(getCartFacade().getSessionCart().getCode());
		if (cartmodel.getIsCashOnDelivery())
		{
			invalid = false;
		}
		else
		{
			if (getCheckoutFlowFacade().hasNoPaymentInfo())
			{
				GlobalMessages.addErrorMessage(model, "checkout.paymentMethod.notSelected");
				invalid = true;
			}
			else
			{
				// Only require the Security Code to be entered on the summary page if the SubscriptionPciOption is set to Default.
				if (CheckoutPciOptionEnum.DEFAULT.equals(getCheckoutFlowFacade().getSubscriptionPciOption())
						&& StringUtils.isBlank(securityCode))
				{
					GlobalMessages.addErrorMessage(model, "checkout.paymentMethod.noSecurityCode");
					invalid = true;
				}
			}
		}

		if (!placeOrderForm.isTermsCheck())
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.terms.not.accepted");
			invalid = true;
			return invalid;
		}
		final CartData cartData = getCheckoutFacade().getCheckoutCart();

		if (!getCheckoutFacade().containsTaxValues())
		{
			LOGGER.error(String.format(
					"Cart %s does not have any tax values, which means the tax cacluation was not properly done, placement of order can't continue",
					cartData.getCode()));
			GlobalMessages.addErrorMessage(model, "checkout.error.tax.missing");
			invalid = true;
		}

		if (!cartData.isCalculated())
		{
			LOGGER.error(
					String.format("Cart %s has a calculated flag of FALSE, placement of order can't continue", cartData.getCode()));
			GlobalMessages.addErrorMessage(model, "checkout.error.cart.notcalculated");
			invalid = true;
		}

		return invalid;
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
		return getCheckoutStep(SUMMARY);
	}


}
