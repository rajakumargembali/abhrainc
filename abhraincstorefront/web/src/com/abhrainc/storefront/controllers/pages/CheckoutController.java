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
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.acceleratorfacades.flow.impl.SessionOverrideCheckoutFlowFacade;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractCheckoutController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.GuestRegisterForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.GuestRegisterValidator;
import de.hybris.platform.acceleratorstorefrontcommons.security.AutoLoginStrategy;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.util.ResponsiveUtils;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abhrainc.core.constants.AbhraincCoreConstants;
import com.abhrainc.facades.order.impl.AbhraincOrderFacade;
import com.abhrainc.storefront.controllers.ControllerConstants;


/**
 * CheckoutController
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/checkout")
public class CheckoutController extends AbstractCheckoutController
{
	private static final Logger LOG = Logger.getLogger(CheckoutController.class);
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String ORDER_CODE_PATH_VARIABLE_PATTERN = "{orderCode:.*}";

	private static final String CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE_LABEL = "orderConfirmation";
	private static final String CONTINUE_URL_KEY = "continueUrl";

	@Resource(name = "productFacade")
	private ProductFacade productFacade;

	@Resource(name = "orderFacade")
	private AbhraincOrderFacade orderFacade;

	@Resource(name = "checkoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource(name = "guestRegisterValidator")
	private GuestRegisterValidator guestRegisterValidator;

	@Resource(name = "autoLoginStrategy")
	private AutoLoginStrategy autoLoginStrategy;

	@ExceptionHandler(ModelNotFoundException.class)
	public String handleModelNotFoundException(final ModelNotFoundException exception, final HttpServletRequest request)
	{
		request.setAttribute("message", exception.getMessage());
		return FORWARD_PREFIX + "/404";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String checkout(final RedirectAttributes redirectModel)
	{
		if (getCheckoutFlowFacade().hasValidCart())
		{
			if (validateCart(redirectModel))
			{
				return REDIRECT_PREFIX + "/cart";
			}
			else
			{
				checkoutFacade.prepareCartForCheckout();
				return getCheckoutRedirectUrl();
			}
		}

		LOG.info("Missing, empty or unsupported cart");

		// No session cart or empty session cart. Bounce back to the cart page.
		return REDIRECT_PREFIX + "/cart";
	}

	@RequestMapping(value = "/orderConfirmation/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String orderConfirmation(@PathVariable("orderCode") final String orderCode, final HttpServletRequest request,
			final Model model) throws CMSItemNotFoundException
	{
		SessionOverrideCheckoutFlowFacade.resetSessionOverrides();
		final OrderData orderData = orderFacade.getOrderDetailsForCode(orderCode);
		try
		{
			storeOrderDetailsInOtherSystem(orderData);
		}
		catch (final Exception e)
		{
			LOG.info("error placing order details in other system");
		}
		return processOrderCode(orderCode, model, request);
	}


	@RequestMapping(value = "/orderConfirmation/" + ORDER_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.POST)
	public String orderConfirmation(final GuestRegisterForm form, final BindingResult bindingResult, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{

		getGuestRegisterValidator().validate(form, bindingResult);
		return processRegisterGuestUserRequest(form, bindingResult, model, request, response, redirectModel);
	}

	/**
	 * Method used to determine the checkout redirect URL that will handle the checkout process.
	 *
	 * @return A <code>String</code> object of the URL to redirect to.
	 */
	protected String getCheckoutRedirectUrl()
	{
		if (getUserFacade().isAnonymousUser())
		{
			return REDIRECT_PREFIX + "/login/checkout";
		}

		// Default to the multi-step checkout
		return REDIRECT_PREFIX + "/checkout/multi";
	}

	protected String processRegisterGuestUserRequest(final GuestRegisterForm form, final BindingResult bindingResult,
			final Model model, final HttpServletRequest request, final HttpServletResponse response,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			return processOrderCode(form.getOrderCode(), model, request);
		}
		try
		{
			getCustomerFacade().changeGuestToCustomer(form.getPwd(), form.getOrderCode());
			getAutoLoginStrategy().login(getCustomerFacade().getCurrentCustomer().getUid(), form.getPwd(), request, response);
			getSessionService().removeAttribute(WebConstants.ANONYMOUS_CHECKOUT);
		}
		catch (final DuplicateUidException e)
		{
			// User already exists
			LOG.warn("guest registration failed: " + e);
			model.addAttribute(new GuestRegisterForm());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER,
					"guest.checkout.existingaccount.register.error", new Object[]
					{ form.getUid() });
			return REDIRECT_PREFIX + request.getHeader("Referer");
		}

		return REDIRECT_PREFIX + "/";
	}

	protected String processOrderCode(final String orderCode, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		final GregorianCalendar gc = new GregorianCalendar();

		final int year = randBetween(2017, 2018);

		gc.set(gc.YEAR, year);

		final int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

		gc.set(gc.DAY_OF_YEAR, dayOfYear);

		saveExpectedDeliveryDate(orderCode, gc.getTime());

		final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);


		if (orderDetails.isGuestCustomer() && !StringUtils.substringBefore(orderDetails.getUser().getUid(), "|")
				.equals(getSessionService().getAttribute(WebConstants.ANONYMOUS_CHECKOUT_GUID)))
		{
			return getCheckoutRedirectUrl();
		}

		if (orderDetails.getEntries() != null && !orderDetails.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : orderDetails.getEntries())
			{
				final String productCode = entry.getProduct().getCode();
				final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
						Arrays.asList(ProductOption.BASIC, ProductOption.PRICE, ProductOption.CATEGORIES));
				entry.setProduct(product);
			}
		}

		model.addAttribute("orderCode", orderCode);
		model.addAttribute("orderData", orderDetails);
		model.addAttribute("allItems", orderDetails.getEntries());
		model.addAttribute("deliveryAddress", orderDetails.getDeliveryAddress());
		model.addAttribute("deliveryMode", orderDetails.getDeliveryMode());
		model.addAttribute("paymentInfo", orderDetails.getPaymentInfo());
		model.addAttribute("pageType", PageType.ORDERCONFIRMATION.name());
		model.addAttribute("deliveryDate", gc.getTime());

		//storeOrderDetailsInOtherSystem(orderDetails);
		processEmailAddress(model, orderDetails);

		final String continueUrl = (String) getSessionService().getAttribute(WebConstants.CONTINUE_URL);
		model.addAttribute(CONTINUE_URL_KEY, (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : ROOT);

		final AbstractPageModel cmsPage = getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE_LABEL);
		storeCmsPageInModel(model, cmsPage);
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CHECKOUT_ORDER_CONFIRMATION_CMS_PAGE_LABEL));
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);

		if (ResponsiveUtils.isResponsive())
		{
			return getViewForPage(model);
		}

		return ControllerConstants.Views.Pages.Checkout.CheckoutConfirmationPage;
	}

	/**
	 * @param string
	 * @param time
	 */
	private void saveExpectedDeliveryDate(final String string, final Date time)
	{
		// YTODO Auto-generated method stub
		orderFacade.saveExpectedDeliveryDate(string, time);

	}

	/**
	 * @param orderDetails
	 */
	private void storeOrderDetailsInOtherSystem(final OrderData orderDetails)
	{ // YTODO Auto-generated method stub
		final RestTemplate restTemplate = new RestTemplate();
		try
		{
			final String url = Config.getString(AbhraincCoreConstants.THIRD_PARTY_APPLICATION_IP,
					AbhraincCoreConstants.THIRD_PARTY_APPLICATION_IP) + "/addOrderDetails";
			//final String url = "http://192.168.1.236:8080/AuditLobby/addOrderDetails";
			final HashMap orderData = new HashMap();
			final OrderModel model = orderFacade.getOrderDetailForCode(orderDetails.getCode());
			orderData.put("OrderID", model.getPk().getLongValue());
			orderData.put("OrderCode", orderDetails.getCode());
			final ConsignmentModel consignmentDetail = orderFacade.getConsignmentDetailForCode(model.getPk());

			orderData.put("ConsignmentId", consignmentDetail.getPk().getLongValue());
			orderData.put("ConsignmentData", consignmentDetail.getStatus().getCode());

			if (orderDetails.getDeliveryAddress() != null)
			{
				orderData.put("AddressData", orderDetails.getDeliveryAddress());
			}
			else
			{
				orderData.put("AddressData", "");
			}

			if (orderDetails.getDeliveryCost() != null)
			{
				orderData.put("PriceData", orderDetails.getDeliveryCost().getFormattedValue());
			}
			else
			{
				orderData.put("PriceData", "");
			}

			if (orderDetails.getStatus() != null)
			{
				orderData.put("orderStatus", orderDetails.getStatus().getCode());
			}
			else
			{
				orderData.put("orderStatus", "");
			}

			if (orderDetails.getUser() != null)
			{
				orderData.put("PrincipleData", orderDetails.getUser().getUid());
			}
			else
			{
				orderData.put("PrincipleData", "");
			}
			restTemplate.postForObject(url, orderData, HashMap.class);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	private void processEmailAddress(final Model model, final OrderData orderDetails)
	{
		final String uid;
		if (orderDetails.isGuestCustomer() && !model.containsAttribute("guestRegisterForm"))
		{
			final GuestRegisterForm guestRegisterForm = new GuestRegisterForm();
			guestRegisterForm.setOrderCode(orderDetails.getGuid());
			uid = orderDetails.getPaymentInfo().getBillingAddress().getEmail();

			guestRegisterForm.setUid(uid);
			model.addAttribute(guestRegisterForm);
		}
		else
		{
			uid = orderDetails.getUser().getUid();
		}
		model.addAttribute("email", uid);
	}

	protected GuestRegisterValidator getGuestRegisterValidator()
	{
		return guestRegisterValidator;
	}

	protected AutoLoginStrategy getAutoLoginStrategy()
	{
		return autoLoginStrategy;
	}

	private static int randBetween(final int start, final int end)
	{
		return start + (int) Math.round(Math.random() * (end - start));
	}
}
