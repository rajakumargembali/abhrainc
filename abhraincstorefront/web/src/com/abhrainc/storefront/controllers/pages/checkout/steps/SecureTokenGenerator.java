/**
 *
 */
package com.abhrainc.storefront.controllers.pages.checkout.steps;

import de.hybris.platform.acceleratorfacades.flow.CheckoutFlowFacade;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.checkout.steps.CheckoutStep;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.AbstractCheckoutStepController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;

import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @author anumu
 *
 */

@Controller
@RequestMapping(value = "/paypalpayment")
public class SecureTokenGenerator extends AbstractCheckoutStepController
{

	private static final String SECURE_TOKEN = "secure-token";


	@Autowired
	private CheckoutFlowFacade checkoutFlowFacade;


	@Autowired
	private CheckoutFacade checkoutFacade;

	/**
	 * @return the checkoutFlowFacade
	 */
	@Override
	public CheckoutFlowFacade getCheckoutFlowFacade()
	{
		return checkoutFlowFacade;
	}

	/**
	 * @param checkoutFlowFacade
	 *           the checkoutFlowFacade to set
	 */
	public void setCheckoutFlowFacade(final CheckoutFlowFacade checkoutFlowFacade)
	{
		this.checkoutFlowFacade = checkoutFlowFacade;
	}




	/**
	 * @param checkoutFacade
	 *           the checkoutFacade to set
	 */
	public void setCheckoutFacade(final CheckoutFacade checkoutFacade)
	{
		this.checkoutFacade = checkoutFacade;
	}

	/**
	 * @return the checkoutFacade
	 */
	/*
	 * @Override public CheckoutFacade getCheckoutFacade() { return checkoutFacade; }
	 *
	 *//**
	   * @param checkoutFacade
	   *           the checkoutFacade to set
	   *//*
		  * public void setCheckoutFacade(final CheckoutFacade checkoutFacade) { this.checkoutFacade = checkoutFacade; }
		  */

	/*
	 * @RequestMapping(value = "/pay") public String securetoken(final Model model) throws Exception { final KeyGenerator
	 * random = new KeyGenerator(); final String key = random.randomString(32); System.out.println(key);
	 * model.addAttribute("SECURETOKENID", key); final StringBuilder postBody = new StringBuilder();
	 * postBody.append("PARTNER=").append("PayPal"); postBody.append("&VENDOR=").append("praneethanumula");
	 * postBody.append("&USER=").append("saipraneethanumula"); postBody.append("&PWD=").append("05$praneeth");
	 * postBody.append("&TRXTYPE=S"); postBody.append("&CREATESECURETOKEN=Y");
	 * postBody.append("&CURRENCY=").append("USD"); postBody.append("&AMT=").append("40");
	 * postBody.append("&SECURETOKENID=").append(key); final String url = "https://pilot-payflowpro.paypal.com"; final
	 * PostMethod post = new PostMethod(url); final RequestEntity entity1 = new StringRequestEntity(postBody.toString());
	 * post.setRequestEntity(entity1); final HttpClient httpclient = new HttpClient(); httpclient.executeMethod(post);
	 * final HashMap<String, String> getResponse = parseReponse(post.getResponseBodyAsString());
	 * System.out.println("response" + getResponse); model.addAttribute("SECURETOKEN", getResponse.get("SECURETOKEN"));
	 * return ControllerConstants.Views.Pages.MultiStepCheckout.AddPaymentMethodPage; }
	 */
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.CheckoutStepController#enterStep(
	 * org.springframework.ui.Model, org.springframework.web.servlet.mvc.support.RedirectAttributes)
	 */
	@Override
	@RequestMapping(value = "/pay")
	public String enterStep(final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
		// YTODO Auto-generated method stub
		final KeyGenerator random = new KeyGenerator();
		final String key = random.randomString(32);
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
		postBody.append("&AMT=").append("40");
		postBody.append("&SECURETOKENID=").append(key);
		final String url = "https://pilot-payflowpro.paypal.com";
		final PostMethod post = new PostMethod(url);
		final RequestEntity entity1 = new StringRequestEntity(postBody.toString());
		post.setRequestEntity(entity1);
		final HttpClient httpclient = new HttpClient();
		try
		{
			httpclient.executeMethod(post);
			final HashMap<String, String> getResponse = parseReponse(post.getResponseBodyAsString());
			System.out.println("response" + getResponse);
			model.addAttribute("SECURETOKEN", getResponse.get("SECURETOKEN"));
			return getCheckoutStep().nextStep();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return getCheckoutStep().currentStep();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.checkout.steps.CheckoutStepController#back(org.
	 * springframework.web.servlet.mvc.support.RedirectAttributes)
	 */

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
		return getCheckoutStep(SECURE_TOKEN);
	}
}
