/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 * you entered into with hybris.
 *
 *
 */
package com.abhrainc.storefront.controllers.pages.checkout.steps;

import de.hybris.platform.acceleratorfacades.payment.data.PaymentSubscriptionResultData;
import de.hybris.platform.acceleratorservices.payment.data.PaymentErrorField;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.SopPaymentDetailsForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.customer.CustomerEmailResolutionService;
import de.hybris.platform.commerceservices.strategies.CheckoutCustomerStrategy;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abhrainc.facades.service.AbhraIncFacadeService;
import com.abhrainc.storefront.controllers.ControllerConstants;


@Controller
@RequestMapping(value = "/checkout/multi/sop")
public class SopPaymentResponseController extends PaymentMethodCheckoutStepController
{
	private static final Logger LOGGER = Logger.getLogger(SopPaymentResponseController.class);

	@Autowired
	AbhraIncFacadeService abhraIncFacadeService;

	@Autowired
	private CustomerAccountService customerAccountService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private CommonI18NService commonI18NService;

	@Autowired
	private CustomerEmailResolutionService customerEmailResolutionService;

	@Autowired
	private UserService userService;

	@Autowired
	private CheckoutCustomerStrategy checkoutCustomerStrategy;



	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}

	/**
	 * @param customerAccountService
	 *           the customerAccountService to set
	 */
	public void setCustomerAccountService(final CustomerAccountService customerAccountService)
	{
		this.customerAccountService = customerAccountService;
	}

	/**
	 * @return the checkoutCustomerStrategy
	 */
	@Override
	public CheckoutCustomerStrategy getCheckoutCustomerStrategy()
	{
		return checkoutCustomerStrategy;
	}

	/**
	 * @param checkoutCustomerStrategy
	 *           the checkoutCustomerStrategy to set
	 */
	public void setCheckoutCustomerStrategy(final CheckoutCustomerStrategy checkoutCustomerStrategy)
	{
		this.checkoutCustomerStrategy = checkoutCustomerStrategy;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	/**
	 * @return the customerEmailResolutionService
	 */
	public CustomerEmailResolutionService getCustomerEmailResolutionService()
	{
		return customerEmailResolutionService;
	}

	/**
	 * @param customerEmailResolutionService
	 *           the customerEmailResolutionService to set
	 */
	public void setCustomerEmailResolutionService(final CustomerEmailResolutionService customerEmailResolutionService)
	{
		this.customerEmailResolutionService = customerEmailResolutionService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
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

	@RequestMapping(value = "/response", method = RequestMethod.POST)
	@RequireHardLogIn
	public String doHandleSopResponse(final HttpServletRequest request, @Valid final SopPaymentDetailsForm sopPaymentDetailsForm,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException
	{
		final Map<String, String> resultMap = getRequestParameterMap(request);
		if (resultMap.get("yesno") != null)
		{
			final String paymentMode = resultMap.get("yesno").toString();
			if (paymentMode.equals("paypal"))
			{

				final CCPaymentInfoData ccPaymentInfoData = getCheckoutFacade().getCheckoutCart().getPaymentInfo();
				if (ccPaymentInfoData == null)
				{
					final boolean savePaymentInfo = sopPaymentDetailsForm.isSavePaymentInfo()
							|| getCheckoutCustomerStrategy().isAnonymousCheckout();
					final PaymentSubscriptionResultData paymentSubscriptionResultData = this.getPaymentFacade()
							.completeSopCreateSubscription(resultMap, savePaymentInfo);

					if (paymentSubscriptionResultData.isSuccess())
					{
						createNewPaymentSubscription(paymentSubscriptionResultData);
					}
					else if (paymentSubscriptionResultData.getDecision() != null
							&& "error".equalsIgnoreCase(paymentSubscriptionResultData.getDecision())
							|| paymentSubscriptionResultData.getErrors() != null && !paymentSubscriptionResultData.getErrors().isEmpty())
					{
						return processErrors(sopPaymentDetailsForm, bindingResult, model, redirectAttributes,
								paymentSubscriptionResultData);
					}
					else
					{
						// SOP ERROR!
						LOGGER.error("Failed to create subscription.  Please check the log files for more information");
						return REDIRECT_URL_ERROR + "/?decision=" + paymentSubscriptionResultData.getDecision() + "&reasonCode="
								+ paymentSubscriptionResultData.getResultCode();
					}
				}
				else
				{
					//

					final AddressModel billingAddress = getModelService().create(AddressModel.class);
					billingAddress.setFirstname(resultMap.get("billTo_firstName"));
					billingAddress.setLastname(resultMap.get("billTo_lastName"));
					billingAddress.setLine1(resultMap.get("billTo_street1"));
					billingAddress.setLine2(resultMap.get("billTo_street2"));
					billingAddress.setTown(resultMap.get("billTo_city"));
					billingAddress.setPostalcode(resultMap.get("billTo_postalCode"));

					if (StringUtils.isNotBlank(resultMap.get("billTo_titleCode")))
					{
						billingAddress.setTitle(getUserService().getTitleForCode(resultMap.get("billTo_titleCode")));
					}

					final CountryModel country = getCommonI18NService().getCountry(resultMap.get("billTo_country"));
					billingAddress.setCountry(country);
					if (StringUtils.isNotEmpty(resultMap.get("billTo_state")))
					{
						billingAddress.setRegion(
								getCommonI18NService().getRegion(country, country.getIsocode() + "-" + resultMap.get("billTo_state")));
					}
					final CustomerModel currentUserForCheckout = getCurrentUserForCheckout();
					final String email = getCustomerEmailResolutionService().getEmailForCustomer(currentUserForCheckout);
					billingAddress.setEmail(email);
					final CreditCardPaymentInfoModel ccPaymentInfoModel = getCustomerAccountService()
							.getCreditCardPaymentInfoForCode(currentUserForCheckout, ccPaymentInfoData.getId());
					ccPaymentInfoModel.setBillingAddress(billingAddress);
					billingAddress.setOwner(ccPaymentInfoModel);
					getModelService().saveAll(ccPaymentInfoModel, billingAddress);
				}
			}
			else
			{
				final CartModel cartmodel = abhraIncFacadeService.getCartDetails(getCartFacade().getSessionCart().getCode());
				cartmodel.setIsCashOnDelivery(true);
				modelService.save(cartmodel);
			}
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "checkout.multi.paymentDetails.notprovided");
			return enterStep(model, redirectAttributes);
		}
		return getCheckoutStep().nextStep();
	}

	protected CustomerModel getCurrentUserForCheckout()
	{
		return getCheckoutCustomerStrategy().getCurrentUserForCheckout();
	}

	protected String processErrors(@Valid final SopPaymentDetailsForm sopPaymentDetailsForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes,
			final PaymentSubscriptionResultData paymentSubscriptionResultData) throws CMSItemNotFoundException
	{
		// Have SOP errors that we can display

		setupAddPaymentPage(model);

		// Build up the SOP form data and render page containing form
		try
		{
			setupSilentOrderPostPage(sopPaymentDetailsForm, model);
		}
		catch (final Exception e)
		{
			LOGGER.error("Failed to build beginCreateSubscription request", e);
			GlobalMessages.addErrorMessage(model, "checkout.multi.paymentMethod.addPaymentDetails.generalError");
			return enterStep(model, redirectAttributes);
		}

		processPaymentSubscriptionErrors(bindingResult, model, paymentSubscriptionResultData);

		return ControllerConstants.Views.Pages.MultiStepCheckout.SilentOrderPostPage;
	}

	protected void createNewPaymentSubscription(final PaymentSubscriptionResultData paymentSubscriptionResultData)
	{
		if (paymentSubscriptionResultData.getStoredCard() != null
				&& StringUtils.isNotBlank(paymentSubscriptionResultData.getStoredCard().getSubscriptionId()))
		{
			final CCPaymentInfoData newPaymentSubscription = paymentSubscriptionResultData.getStoredCard();

			if (getUserFacade().getCCPaymentInfos(true).size() <= 1)
			{
				getUserFacade().setDefaultPaymentInfo(newPaymentSubscription);
			}
			getCheckoutFacade().setPaymentDetails(newPaymentSubscription.getId());
		}
	}

	protected void processPaymentSubscriptionErrors(final BindingResult bindingResult, final Model model,
			final PaymentSubscriptionResultData paymentSubscriptionResultData)
	{
		if (paymentSubscriptionResultData.getErrors() != null && !paymentSubscriptionResultData.getErrors().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.paymentethod.formentry.invalid");
			// Add in specific errors for invalid fields
			for (final PaymentErrorField paymentErrorField : paymentSubscriptionResultData.getErrors().values())
			{
				if (paymentErrorField.isMissing())
				{
					bindingResult.rejectValue(paymentErrorField.getName(),
							"checkout.error.paymentethod.formentry.sop.missing." + paymentErrorField.getName(),
							"Please enter a value for this field");
				}
				if (paymentErrorField.isInvalid())
				{
					bindingResult.rejectValue(paymentErrorField.getName(),
							"checkout.error.paymentethod.formentry.sop.invalid." + paymentErrorField.getName(),
							"This value is invalid for this field");
				}
			}
		}
		else if (paymentSubscriptionResultData.getDecision() != null
				&& "error".equalsIgnoreCase(paymentSubscriptionResultData.getDecision()))
		{
			LOGGER.error("Failed to create subscription. Error occurred while contacting external payment services.");
			GlobalMessages.addErrorMessage(model, "checkout.multi.paymentMethod.addPaymentDetails.generalError");
		}
	}

	@RequestMapping(value = "/billingaddressform", method = RequestMethod.GET)
	public String getCountryAddressForm(@RequestParam("countryIsoCode") final String countryIsoCode,
			@RequestParam("useDeliveryAddress") final boolean useDeliveryAddress, final Model model)
	{
		model.addAttribute("supportedCountries", getCountries());
		model.addAttribute("regions", getI18NFacade().getRegionsForCountryIso(countryIsoCode));
		model.addAttribute("country", countryIsoCode);

		final SopPaymentDetailsForm sopPaymentDetailsForm = new SopPaymentDetailsForm();
		model.addAttribute("sopPaymentDetailsForm", sopPaymentDetailsForm);
		if (useDeliveryAddress)
		{
			final AddressData deliveryAddress = getCheckoutFacade().getCheckoutCart().getDeliveryAddress();

			if (deliveryAddress.getRegion() != null && !StringUtils.isEmpty(deliveryAddress.getRegion().getIsocode()))
			{
				sopPaymentDetailsForm.setBillTo_state(deliveryAddress.getRegion().getIsocodeShort());
			}

			sopPaymentDetailsForm.setBillTo_titleCode(deliveryAddress.getTitleCode());
			sopPaymentDetailsForm.setBillTo_firstName(deliveryAddress.getFirstName());
			sopPaymentDetailsForm.setBillTo_lastName(deliveryAddress.getLastName());
			sopPaymentDetailsForm.setBillTo_street1(deliveryAddress.getLine1());
			sopPaymentDetailsForm.setBillTo_street2(deliveryAddress.getLine2());
			sopPaymentDetailsForm.setBillTo_city(deliveryAddress.getTown());
			sopPaymentDetailsForm.setBillTo_postalCode(deliveryAddress.getPostalCode());
			sopPaymentDetailsForm.setBillTo_country(deliveryAddress.getCountry().getIsocode());
			sopPaymentDetailsForm.setBillTo_phoneNumber(deliveryAddress.getPhone());
		}
		return ControllerConstants.Views.Fragments.Checkout.BillingAddressForm;
	}


}
