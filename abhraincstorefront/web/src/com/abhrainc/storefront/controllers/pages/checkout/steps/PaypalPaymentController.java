/**
 *
 */
package com.abhrainc.storefront.controllers.pages.checkout.steps;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;

import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value = "/silentOrderPostPage")
public class PaypalPaymentController
{
	private static final String SECURE_TOKEN = "secure-token";

	@RequestMapping(method = RequestMethod.GET)
	public String transactionDetails(final Model model, final RedirectAttributes redirectAttributes)
			throws CMSItemNotFoundException, CommerceCartModificationException
	{
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
		//		return getCheckoutStep().currentStep();
		//
		//		System.out.println("SUCCESS");
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

	//	@RequestMapping(value = "/back", method = RequestMethod.GET)
	//	@RequireHardLogIn
	//	public String back(final RedirectAttributes redirectAttributes)
	//	{
	//		return getCheckoutStep().previousStep();
	//	}

	//	@RequestMapping(value = "/next", method = RequestMethod.GET)
	//	@RequireHardLogIn
	//	public String next(final RedirectAttributes redirectAttributes)
	//	{
	//		return getCheckoutStep().nextStep();
	//	}
	//
	//	protected CheckoutStep getCheckoutStep()
	//	{
	//		return getCheckoutStep();
	//	}

}
