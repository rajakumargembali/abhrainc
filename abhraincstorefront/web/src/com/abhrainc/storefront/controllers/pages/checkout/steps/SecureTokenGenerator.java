/**
 *
 */
package com.abhrainc.storefront.controllers.pages.checkout.steps;

import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author anumu
 *
 */

@Controller
@RequestMapping(value = "/paypalpayment")
public class SecureTokenGenerator
{

	@RequestMapping(value = "/pay")
	public String securetoken(final Model model) throws Exception
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
		httpclient.executeMethod(post);
		final HashMap<String, String> getResponse = parseReponse(post.getResponseBodyAsString());
		System.out.println("response" + getResponse);
		model.addAttribute("SECURETOKEN", getResponse.get("SECURETOKEN"));
		return "pages/payment/paypalpayment";
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
}
