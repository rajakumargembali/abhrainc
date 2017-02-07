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
		final Client client = new ClientBuilder("9202acaf-1a99-1af3-902d-d8300d96ee34", "T3WHOzlNPDsqZ7hnPOrl").build();

		final Lookup lookup = new Lookup();
		lookup.setStreet(addressData.getLine1());
		lookup.setCity(addressData.getTownCity());
		lookup.setState(addressData.getCountryIso());

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
