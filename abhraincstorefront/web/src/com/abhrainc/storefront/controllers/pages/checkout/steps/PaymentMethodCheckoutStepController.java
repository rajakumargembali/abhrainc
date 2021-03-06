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


import de.hybris.platform.acceleratorfacades.payment.data.PaymentSubscriptionResultData;
import de.hybris.platform.acceleratorservices.enums.CheckoutPciOptionEnum;
import de.hybris.platform.acceleratorservices.payment.constants.PaymentConstants;
import de.hybris.platform.acceleratorservices.payment.cybersource.enums.CardTypeEnum;
import de.hybris.platform.acceleratorservices.payment.data.PaymentData;
import de.hybris.platform.acceleratorservices.payment.data.PaymentSubscriptionResultItem;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.PreValidateCheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.PaymentDetailsForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.SopPaymentDetailsForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.CardTypeData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.enums.CreditCardType;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abhrainc.facades.service.AbhraIncFacadeService;
import com.abhrainc.storefront.controllers.ControllerConstants;


@Controller
@RequestMapping(value = "/checkout/multi/payment-method")
public class PaymentMethodCheckoutStepController extends AbstractCheckoutStepController
{
	@Autowired
	AbhraIncFacadeService abhraIncFacadeService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private Converter<PaymentSubscriptionResultItem, PaymentSubscriptionResultData> paymentSubscriptionResultDataConverter;

	protected static final Map<String, String> CYBERSOURCE_SOP_CARD_TYPES = new HashMap<>();
	private static final String PAYMENT_METHOD = "payment-method";
	private static final String CART_DATA_ATTR = "cartData";

	private static final Logger LOGGER = Logger.getLogger(PaymentMethodCheckoutStepController.class);



	/**
	 * @return the paymentSubscriptionResultDataConverter
	 */
	public Converter<PaymentSubscriptionResultItem, PaymentSubscriptionResultData> getPaymentSubscriptionResultDataConverter()
	{
		return paymentSubscriptionResultDataConverter;
	}

	/**
	 * @param paymentSubscriptionResultDataConverter
	 *           the paymentSubscriptionResultDataConverter to set
	 */
	public void setPaymentSubscriptionResultDataConverter(
			final Converter<PaymentSubscriptionResultItem, PaymentSubscriptionResultData> paymentSubscriptionResultDataConverter)
	{
		this.paymentSubscriptionResultDataConverter = paymentSubscriptionResultDataConverter;
	}

	@ModelAttribute("billingCountries")
	public Collection<CountryData> getBillingCountries()
	{
		return getCheckoutFacade().getBillingCountries();
	}

	@ModelAttribute("cardTypes")
	public Collection<CardTypeData> getCardTypes()
	{
		return getCheckoutFacade().getSupportedCardTypes();
	}

	@ModelAttribute("months")
	public List<SelectOption> getMonths()
	{
		final List<SelectOption> months = new ArrayList<SelectOption>();

		months.add(new SelectOption("1", "01"));
		months.add(new SelectOption("2", "02"));
		months.add(new SelectOption("3", "03"));
		months.add(new SelectOption("4", "04"));
		months.add(new SelectOption("5", "05"));
		months.add(new SelectOption("6", "06"));
		months.add(new SelectOption("7", "07"));
		months.add(new SelectOption("8", "08"));
		months.add(new SelectOption("9", "09"));
		months.add(new SelectOption("10", "10"));
		months.add(new SelectOption("11", "11"));
		months.add(new SelectOption("12", "12"));

		return months;
	}

	@ModelAttribute("startYears")
	public List<SelectOption> getStartYears()
	{
		final List<SelectOption> startYears = new ArrayList<SelectOption>();
		final Calendar calender = new GregorianCalendar();

		for (int i = calender.get(Calendar.YEAR); i > calender.get(Calendar.YEAR) - 6; i--)
		{
			startYears.add(new SelectOption(String.valueOf(i), String.valueOf(i)));
		}

		return startYears;
	}

	@ModelAttribute("expiryYears")
	public List<SelectOption> getExpiryYears()
	{
		final List<SelectOption> expiryYears = new ArrayList<SelectOption>();
		final Calendar calender = new GregorianCalendar();

		for (int i = calender.get(Calendar.YEAR); i < calender.get(Calendar.YEAR) + 11; i++)
		{
			expiryYears.add(new SelectOption(String.valueOf(i), String.valueOf(i)));
		}

		return expiryYears;
	}

	/*
	 * @RequestMapping(value = "/paypalresponse", method = RequestMethod.GET)
	 *
	 * @RequireHardLogIn
	 *
	 * @PreValidateCheckoutStep(checkoutStep = PAYMENT_METHOD) public PaymentTransactionType
	 * getPaymentTransactionDetails(final Model model, final RedirectAttributes redirectAttributes) throws
	 * CMSItemNotFoundException { PaymentTransactionType result = null; CallerServices caller = new CallerServices(); try
	 * { caller.setAPIProfile(getProfile());
	 *
	 * GetTransactionDetailsRequestType request = new GetTransactionDetailsRequestType();
	 * request.setTransactionID(transactionId); DetailLevelCodeType[] detail = new DetailLevelCodeType[1]; detail[0] =
	 * DetailLevelCodeType.ReturnAll; request.setDetailLevel(detail);
	 *
	 * GetTransactionDetailsResponseType res = (GetTransactionDetailsResponseType) caller.call("GetTransactionDetails",
	 * request); if (res != null) result = res.getPaymentTransactionDetails();
	 *
	 * } catch (PayPalException e) { LOG.error(e.getMessage()); }
	 *
	 * return result; }
	 */



	@Override
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@RequireHardLogIn
	@PreValidateCheckoutStep(checkoutStep = PAYMENT_METHOD)
	public String enterStep(final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		getCheckoutFacade().setDeliveryModeIfAvailable();
		setupAddPaymentPage(model);

		// Use the checkout PCI strategy for getting the URL for creating new subscriptions.
		final CheckoutPciOptionEnum subscriptionPciOption = getCheckoutFlowFacade().getSubscriptionPciOption();
		setCheckoutStepLinksForModel(model, getCheckoutStep());
		if (CheckoutPciOptionEnum.HOP.equals(subscriptionPciOption))
		{
			// Redirect the customer to the HOP page or show error message if it fails (e.g. no HOP configurations).
			try
			{
				final PaymentData hostedOrderPageData = getPaymentFacade().beginHopCreateSubscription("/checkout/multi/hop/response",
						"/integration/merchant_callback");
				model.addAttribute("hostedOrderPageData", hostedOrderPageData);

				final boolean hopDebugMode = getSiteConfigService().getBoolean(PaymentConstants.PaymentProperties.HOP_DEBUG_MODE,
						false);
				model.addAttribute("hopDebugMode", Boolean.valueOf(hopDebugMode));

				return ControllerConstants.Views.Pages.MultiStepCheckout.HostedOrderPostPage;
			}
			catch (final Exception e)
			{
				LOGGER.error("Failed to build beginCreateSubscription request", e);
				GlobalMessages.addErrorMessage(model, "checkout.multi.paymentMethod.addPaymentDetails.generalError");
			}
		}
		else if (CheckoutPciOptionEnum.SOP.equals(subscriptionPciOption))
		{
			// Build up the SOP form data and render page containing form
			final SopPaymentDetailsForm sopPaymentDetailsForm = new SopPaymentDetailsForm();
			try
			{
				setupSilentOrderPostPage(sopPaymentDetailsForm, model);
				return ControllerConstants.Views.Pages.MultiStepCheckout.SilentOrderPostPage;
			}
			catch (final Exception e)
			{
				LOGGER.error("Failed to build beginCreateSubscription request", e);
				GlobalMessages.addErrorMessage(model, "checkout.multi.paymentMethod.addPaymentDetails.generalError");
				model.addAttribute("sopPaymentDetailsForm", sopPaymentDetailsForm);
			}
		}

		/*
		 * // If not using HOP or SOP we need to build up the payment details form final PaymentDetailsForm
		 * paymentDetailsForm = new PaymentDetailsForm(); final AddressForm addressForm = new AddressForm();
		 * paymentDetailsForm.setBillingAddress(addressForm); model.addAttribute(paymentDetailsForm);
		 *
		 * final CartData cartData = getCheckoutFacade().getCheckoutCart(); model.addAttribute(CART_DATA_ATTR, cartData);
		 *
		 * final KeyGenerator random = new KeyGenerator(); final String key = random.randomString(32);
		 * System.out.println(key); model.addAttribute("SECURETOKENID", key); final StringBuilder postBody = new
		 * StringBuilder(); postBody.append("PARTNER=").append("PayPal");
		 * postBody.append("&VENDOR=").append("praneethanumula"); postBody.append("&USER=").append("saipraneethanumula");
		 * postBody.append("&PWD=").append("05$praneeth"); postBody.append("&TRXTYPE=S");
		 * postBody.append("&CREATESECURETOKEN=Y"); postBody.append("&CURRENCY=").append("USD");
		 * postBody.append("&AMT=").append("40"); postBody.append("&SECURETOKENID=").append(key); final String url =
		 * "https://pilot-payflowpro.paypal.com"; final PostMethod post = new PostMethod(url); final RequestEntity entity1
		 * = new StringRequestEntity(postBody.toString()); post.setRequestEntity(entity1); final HttpClient httpclient =
		 * new HttpClient(); try { httpclient.executeMethod(post); final HashMap<String, String> getResponse =
		 * parseReponse(post.getResponseBodyAsString()); System.out.println("response" + getResponse);
		 * model.addAttribute("SECURETOKEN", getResponse.get("SECURETOKEN")); setCheckoutStepLinksForModel(model,
		 * getCheckoutStep()); } catch (final Exception e) { e.printStackTrace(); }
		 */
		return ControllerConstants.Views.Pages.MultiStepCheckout.AddPaymentMethodPage;
	}

	@RequestMapping(value = "/paymentconfirmation")
	public String paypalPayment(final String RESPMSG, final String EXPDATE, final String PNREF, final String METHOD,
			final int CARDTYPE, final int RESULT, final double AMT, final String ACCT, final Model model)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		System.out.println("Response Message ::" + RESPMSG);
		System.out.println("Expiry Date ::" + EXPDATE);
		System.out.println("Pnref=" + PNREF);
		System.out.println("Payment Method:" + METHOD);
		System.out.println("Card Type:" + CARDTYPE);
		System.out.println("Result:" + RESULT);
		System.out.println("Amount:" + AMT);
		System.out.println("Credit Card Number:" + "************" + ACCT);
		if (RESULT == 0)
		{
			final CartData cartData = getCheckoutFacade().getCheckoutCart();
			System.out.println("Cart Data::" + cartData.getCode());
			final CreditCardPaymentInfoModel cardPaymentInfoModel = new CreditCardPaymentInfoModel();
			cardPaymentInfoModel.setNumber("************" + ACCT);
			if (CARDTYPE == 0)
			{
				cardPaymentInfoModel.setType(CreditCardType.valueOf(CardTypeEnum.get("001").name().toUpperCase()));
			}
			if (CARDTYPE == 1)
			{
				cardPaymentInfoModel.setType(CreditCardType.valueOf(CardTypeEnum.get("002").name().toUpperCase()));
			}
			if (CARDTYPE == 2)
			{
				cardPaymentInfoModel.setType(CreditCardType.valueOf(CardTypeEnum.get("003").name().toUpperCase()));
			}
			if (CARDTYPE == 3)
			{
				cardPaymentInfoModel.setType(CreditCardType.valueOf(CardTypeEnum.get("004").name().toUpperCase()));
			}
			if (CARDTYPE == 4)
			{
				cardPaymentInfoModel.setType(CreditCardType.valueOf(CardTypeEnum.get("005").name().toUpperCase()));
			}
			if (CARDTYPE == 5)
			{
				cardPaymentInfoModel.setType(CreditCardType.valueOf(CardTypeEnum.get("007").name().toUpperCase()));
			}
			cardPaymentInfoModel.setValidToMonth(EXPDATE.charAt(0) + "" + EXPDATE.charAt(1));
			cardPaymentInfoModel.setValidToYear(EXPDATE.charAt(2) + "" + EXPDATE.charAt(3));
			System.out.println("Expiry month:" + EXPDATE.charAt(0) + "" + EXPDATE.charAt(1));
			System.out.println("Expiry year:" + EXPDATE.charAt(2) + "" + EXPDATE.charAt(3));
			cardPaymentInfoModel.setSaved(true);
			final CustomerModel customerModel = getCheckoutCustomerStrategy().getCurrentUserForCheckout();
			cardPaymentInfoModel.setCcOwner(getCheckoutCustomerStrategy().getCurrentUserForCheckout().getName());
			cardPaymentInfoModel.setCode(customerModel.getUid() + "_" + UUID.randomUUID());
			cardPaymentInfoModel.setUser(customerModel);
			modelService.save(cardPaymentInfoModel);
			final PaymentSubscriptionResultItem paymentSubscriptionResult = new PaymentSubscriptionResultItem();
			paymentSubscriptionResult.setStoredCard(cardPaymentInfoModel);
			final PaymentSubscriptionResultData paymentSubscriptionResultData = getPaymentSubscriptionResultDataConverter()
					.convert(paymentSubscriptionResult);
			System.out.println(paymentSubscriptionResultData);
			final CCPaymentInfoData newPaymentSubscription = paymentSubscriptionResultData.getStoredCard();
			System.out.println(newPaymentSubscription);
			getCheckoutFacade().setPaymentDetails(newPaymentSubscription.getId());
			/*
			 * final CartModel cartModel = abhraIncFacadeService.getCartDetails(cartData.getCode());
			 * cartModel.setPaymentCost(AMT);
			 */
			model.addAttribute("PaymentStatus", true);
		}
		else
		{
			model.addAttribute("PaymentStatus", false);
		}
		return "/pages/payment/paypalpayment";
		//return getCheckoutStep().currentStep();
	}

	@RequestMapping(value =
	{ "/silentOrderPostPage" }, method = RequestMethod.GET)
	public String transactionDetails(final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		final KeyGenerator random = new KeyGenerator();
		final String key = random.randomString(32);
		final CartData cartData = getCheckoutFacade().getCheckoutCart();
		System.out.println(key);
		model.addAttribute("SECURETOKENID", key);
		final StringBuilder postBody = new StringBuilder();
		postBody.append("PARTNER=").append("PayPal");
		postBody.append("&VENDOR=").append("praneethanumula");
		postBody.append("&USER=").append("saipraneethanumula");
		postBody.append("&PWD=").append("05$praneeth");
		postBody.append("&TRXTYPE=S");
		postBody.append("&CREATESECURETOKEN=Y");
		postBody.append("&CURRENCY=").append("USD");
		postBody.append("&AMT=").append(cartData.getTotalPriceWithTax().getValue());
		System.out.println("Amount value::" + cartData.getTotalPriceWithTax().getValue());
		postBody.append("&SECURETOKENID=").append(key);
		final String url = "https://pilot-payflowpro.paypal.com";
		final PostMethod post = new PostMethod(url);
		final RequestEntity entity1 = new StringRequestEntity(postBody.toString());
		post.setRequestEntity(entity1);
		final HttpClient httpclient = new HttpClient();
		//		httpclient.executeMethod(post);
		//		final HashMap<String, String> getResponse = parseReponse(post.getResponseBodyAsString());
		//		System.out.println("response" + getResponse);
		//		model.addAttribute("SECURETOKEN", getResponse.get("SECURETOKEN"));
		//		return ControllerConstants.Views.Pages.MultiStepCheckout.AddPaymentMethodPage;

		try
		{
			httpclient.executeMethod(post);
			final HashMap<String, String> getResponse = parseReponse(post.getResponseBodyAsString());
			System.out.println("response" + getResponse);
			model.addAttribute("SECURETOKEN", getResponse.get("SECURETOKEN"));
			//			return getCheckoutStep().nextStep();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return "/pages/checkout/multi/success";
	}


	public static HashMap<String, String> parseReponse(final String cad)
	{
		final HashMap<String, String> result = new HashMap<String, String>();
		final String[] arr = cad.split("&");
		for (final String e : arr)
		{
			final String[] arr1 = e.split("=");
			if ((arr1.length > 0) && (StringUtils.isNotEmpty(arr1[0])))
			{
				result.put(arr1[0], (arr1.length > 1) && (StringUtils.isNotEmpty(arr1[1])) ? arr1[1] : "");
			}
		}
		return result;
	}

	@RequestMapping(value =
	{ "/add" }, method = RequestMethod.POST)
	@RequireHardLogIn
	public String add(final Model model, @Valid final PaymentDetailsForm paymentDetailsForm, final BindingResult bindingResult)
			throws CMSItemNotFoundException
	{
		getPaymentDetailsValidator().validate(paymentDetailsForm, bindingResult);
		setupAddPaymentPage(model);

		final CartData cartData = getCheckoutFacade().getCheckoutCart();
		model.addAttribute(CART_DATA_ATTR, cartData);

		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.paymentethod.formentry.invalid");
			return ControllerConstants.Views.Pages.MultiStepCheckout.AddPaymentMethodPage;
		}

		final CCPaymentInfoData paymentInfoData = new CCPaymentInfoData();
		fillInPaymentData(paymentDetailsForm, paymentInfoData);

		final AddressData addressData;
		if (Boolean.FALSE.equals(paymentDetailsForm.getNewBillingAddress()))
		{
			addressData = getCheckoutFacade().getCheckoutCart().getDeliveryAddress();
			if (addressData == null)
			{
				GlobalMessages.addErrorMessage(model,
						"checkout.multi.paymentMethod.createSubscription.billingAddress.noneSelectedMsg");
				return ControllerConstants.Views.Pages.MultiStepCheckout.AddPaymentMethodPage;
			}

			addressData.setBillingAddress(true); // mark this as billing address
		}
		else
		{
			final AddressForm addressForm = paymentDetailsForm.getBillingAddress();
			addressData = new AddressData();
			fillInAddressData(addressData, addressForm);
		}

		getAddressVerificationFacade().verifyAddressData(addressData);
		paymentInfoData.setBillingAddress(addressData);

		final CCPaymentInfoData newPaymentSubscription = getCheckoutFacade().createPaymentSubscription(paymentInfoData);
		if (!checkPaymentSubscription(model, paymentDetailsForm, newPaymentSubscription))
		{
			return ControllerConstants.Views.Pages.MultiStepCheckout.AddPaymentMethodPage;
		}

		model.addAttribute("paymentId", newPaymentSubscription.getId());
		setCheckoutStepLinksForModel(model, getCheckoutStep());

		return getCheckoutStep().nextStep();
	}

	protected boolean checkPaymentSubscription(final Model model, @Valid final PaymentDetailsForm paymentDetailsForm,
			final CCPaymentInfoData newPaymentSubscription)
	{
		if (newPaymentSubscription != null && StringUtils.isNotBlank(newPaymentSubscription.getSubscriptionId()))
		{
			if (Boolean.TRUE.equals(paymentDetailsForm.getSaveInAccount()) && getUserFacade().getCCPaymentInfos(true).size() <= 1)
			{
				getUserFacade().setDefaultPaymentInfo(newPaymentSubscription);
			}
			getCheckoutFacade().setPaymentDetails(newPaymentSubscription.getId());
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "checkout.multi.paymentMethod.createSubscription.failedMsg");
			return false;
		}
		return true;
	}

	protected void fillInPaymentData(@Valid final PaymentDetailsForm paymentDetailsForm, final CCPaymentInfoData paymentInfoData)
	{
		paymentInfoData.setId(paymentDetailsForm.getPaymentId());
		paymentInfoData.setCardType(paymentDetailsForm.getCardTypeCode());
		paymentInfoData.setAccountHolderName(paymentDetailsForm.getNameOnCard());
		paymentInfoData.setCardNumber(paymentDetailsForm.getCardNumber());
		paymentInfoData.setStartMonth(paymentDetailsForm.getStartMonth());
		paymentInfoData.setStartYear(paymentDetailsForm.getStartYear());
		paymentInfoData.setExpiryMonth(paymentDetailsForm.getExpiryMonth());
		paymentInfoData.setExpiryYear(paymentDetailsForm.getExpiryYear());
		if (Boolean.TRUE.equals(paymentDetailsForm.getSaveInAccount()) || getCheckoutCustomerStrategy().isAnonymousCheckout())
		{
			paymentInfoData.setSaved(true);
		}
		paymentInfoData.setIssueNumber(paymentDetailsForm.getIssueNumber());
	}

	protected void fillInAddressData(final AddressData addressData, final AddressForm addressForm)
	{
		if (addressForm != null)
		{
			addressData.setId(addressForm.getAddressId());
			addressData.setTitleCode(addressForm.getTitleCode());
			addressData.setFirstName(addressForm.getFirstName());
			addressData.setLastName(addressForm.getLastName());
			addressData.setLine1(addressForm.getLine1());
			addressData.setLine2(addressForm.getLine2());
			addressData.setTown(addressForm.getTownCity());
			addressData.setPostalCode(addressForm.getPostcode());
			addressData.setCountry(getI18NFacade().getCountryForIsocode(addressForm.getCountryIso()));
			if (addressForm.getRegionIso() != null)
			{
				addressData.setRegion(getI18NFacade().getRegion(addressForm.getCountryIso(), addressForm.getRegionIso()));
			}

			addressData.setShippingAddress(Boolean.TRUE.equals(addressForm.getShippingAddress()));
			addressData.setBillingAddress(Boolean.TRUE.equals(addressForm.getBillingAddress()));
		}
	}


	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@RequireHardLogIn
	public String remove(@RequestParam(value = "paymentInfoId") final String paymentMethodId,
			final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		getUserFacade().unlinkCCPaymentInfo(paymentMethodId);
		GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.profile.paymentCart.removed");
		return getCheckoutStep().currentStep();
	}

	/**
	 * This method gets called when the "Use These Payment Details" button is clicked. It sets the selected payment
	 * method on the checkout facade and reloads the page highlighting the selected payment method.
	 *
	 * @param selectedPaymentMethodId
	 *           - the id of the payment method to use.
	 * @return - a URL to the page to load.
	 */
	@RequestMapping(value = "/choose", method = RequestMethod.GET)
	@RequireHardLogIn
	public String doSelectPaymentMethod(@RequestParam("selectedPaymentMethodId") final String selectedPaymentMethodId)
	{
		if (StringUtils.isNotBlank(selectedPaymentMethodId))
		{
			getCheckoutFacade().setPaymentDetails(selectedPaymentMethodId);
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

	protected CardTypeData createCardTypeData(final String code, final String name)
	{
		final CardTypeData cardTypeData = new CardTypeData();
		cardTypeData.setCode(code);
		cardTypeData.setName(name);
		return cardTypeData;
	}

	protected void setupAddPaymentPage(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("metaRobots", "noindex,nofollow");
		model.addAttribute("hasNoPaymentInfo", Boolean.valueOf(getCheckoutFlowFacade().hasNoPaymentInfo()));
		prepareDataForPage(model);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getResourceBreadcrumbBuilder().getBreadcrumbs("checkout.multi.paymentMethod.breadcrumb"));
		final ContentPageModel contentPage = getContentPageForLabelOrId(MULTI_CHECKOUT_SUMMARY_CMS_PAGE_LABEL);
		storeCmsPageInModel(model, contentPage);
		setUpMetaDataForContentPage(model, contentPage);
		setCheckoutStepLinksForModel(model, getCheckoutStep());
	}

	protected void setupSilentOrderPostPage(final SopPaymentDetailsForm sopPaymentDetailsForm, final Model model)
	{
		try
		{
			final PaymentData silentOrderPageData = getPaymentFacade().beginSopCreateSubscription("/checkout/multi/sop/response",
					"/integration/merchant_callback");
			model.addAttribute("silentOrderPageData", silentOrderPageData);
			sopPaymentDetailsForm.setParameters(silentOrderPageData.getParameters());
			model.addAttribute("paymentFormUrl", silentOrderPageData.getPostUrl());
		}
		catch (final IllegalArgumentException e)
		{
			model.addAttribute("paymentFormUrl", "");
			model.addAttribute("silentOrderPageData", null);
			LOGGER.warn("Failed to set up silent order post page", e);
			GlobalMessages.addErrorMessage(model, "checkout.multi.sop.globalError");
		}

		final CartData cartData = getCheckoutFacade().getCheckoutCart();
		model.addAttribute("silentOrderPostForm", new PaymentDetailsForm());
		model.addAttribute(CART_DATA_ATTR, cartData);
		model.addAttribute("deliveryAddress", cartData.getDeliveryAddress());
		model.addAttribute("sopPaymentDetailsForm", sopPaymentDetailsForm);
		model.addAttribute("paymentInfos", getUserFacade().getCCPaymentInfos(true));
		model.addAttribute("sopCardTypes", getSopCardTypes());
		if (StringUtils.isNotBlank(sopPaymentDetailsForm.getBillTo_country()))
		{
			model.addAttribute("regions", getI18NFacade().getRegionsForCountryIso(sopPaymentDetailsForm.getBillTo_country()));
			model.addAttribute("country", sopPaymentDetailsForm.getBillTo_country());
		}
	}

	protected Collection<CardTypeData> getSopCardTypes()
	{
		final Collection<CardTypeData> sopCardTypes = new ArrayList<CardTypeData>();

		final List<CardTypeData> supportedCardTypes = getCheckoutFacade().getSupportedCardTypes();
		for (final CardTypeData supportedCardType : supportedCardTypes)
		{
			// Add credit cards for all supported cards that have mappings for cybersource SOP
			if (CYBERSOURCE_SOP_CARD_TYPES.containsKey(supportedCardType.getCode()))
			{
				sopCardTypes.add(
						createCardTypeData(CYBERSOURCE_SOP_CARD_TYPES.get(supportedCardType.getCode()), supportedCardType.getName()));
			}
		}
		return sopCardTypes;
	}

	protected CheckoutStep getCheckoutStep()
	{
		return getCheckoutStep(PAYMENT_METHOD);
	}

	static
	{
		// Map hybris card type to Cybersource SOP credit card
		CYBERSOURCE_SOP_CARD_TYPES.put("visa", "001");
		CYBERSOURCE_SOP_CARD_TYPES.put("master", "002");
		CYBERSOURCE_SOP_CARD_TYPES.put("amex", "003");
		CYBERSOURCE_SOP_CARD_TYPES.put("diners", "005");
		CYBERSOURCE_SOP_CARD_TYPES.put("maestro", "024");
	}

}
