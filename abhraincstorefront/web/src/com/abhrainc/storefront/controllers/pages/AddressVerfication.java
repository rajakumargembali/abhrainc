/**
 *
 */

package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.forms.AddressForm;

import java.util.ArrayList;

import com.smartystreets.api.us_street.Candidate;
import com.smartystreets.api.us_street.Client;
import com.smartystreets.api.us_street.ClientBuilder;
import com.smartystreets.api.us_street.Lookup;


/*
@author sujan
*/

public class AddressVerfication
{
	public String addressVerfiy(final AddressForm addressData)
	{
		final Client client = new ClientBuilder("b50fa090-7704-4425-0367-5b2fbfbc077a", "Qrlvj6kffCfz7d5Ddckk").build();

		final Lookup lookup = new Lookup();
		if (addressData.getLine1() != null)
		{
			lookup.setStreet(addressData.getLine1());
		}
		if (addressData.getTownCity() != null)
		{
			lookup.setCity(addressData.getTownCity());
		}
		if (addressData.getRegionIso() != null)
		{
			lookup.setState(addressData.getRegionIso());
		}
		if (addressData.getPostcode() != null)
		{
			lookup.setZipCode(addressData.getPostcode());
		}
		try
		{
			client.send(lookup);
		}
		catch (final Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}

		final ArrayList<Candidate> results = lookup.getResult();

		if (results.isEmpty())
		{
			System.out.println("No candidates. This means the address is not valid.");
			return null;
		}

		final Candidate firstCandidate = results.get(0);

		System.out.println("Address is valid. (There is at least one candidate)\n");
		System.out.println("ZIP Code: " + firstCandidate.getComponents().getZipCode());
		System.out.println("County: " + firstCandidate.getMetadata().getCountyName());
		System.out.println("Latitude: " + firstCandidate.getMetadata().getLatitude());
		System.out.println("Longitude: " + firstCandidate.getMetadata().getLongitude());
		return "Success";
	}
}
