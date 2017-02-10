/**
 *
 */
package com.abhrainc.storefront.controllers.pages.checkout.steps;

import java.security.SecureRandom;


/**
 * @author anumu
 *
 */
public class KeyGenerator
{
	// Code to generate the random 6 digit AlphaNumeric string
	// Write your util class
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom rnd = new SecureRandom();

	public String randomString(final int len)
	{
		final StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
		{
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}
}
