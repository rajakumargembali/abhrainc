/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.abhrainc.core.constants.AbhraincCoreConstants;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;


/**
 * @author sujan
 *
 */
public class GetIpAddressForUserController
{
	public String getPublicIp()
	{
		// YTODO Auto-generated method stub
		String systemipaddress = "";
		try
		{
			final URL url_name = new URL("http://bot.whatismyipaddress.com");
			final BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
			systemipaddress = sc.readLine().trim();
			if (!(systemipaddress.length() > 0))
			{
				try
				{
					final InetAddress localhost = InetAddress.getLocalHost();
					System.out.println((localhost.getHostAddress()).trim());
					systemipaddress = (localhost.getHostAddress()).trim();
				}
				catch (final Exception e1)
				{
					systemipaddress = "Cannot Execute Properly";
				}
			}
		}
		catch (final Exception e2)
		{
			systemipaddress = "Cannot Execute Properly";
		}
		System.out.println("\nYour IP Address: " + systemipaddress + "\n");
		return systemipaddress;
	}

	public String getISOCode(final String ipAddr)
	{
		// YTODO Auto-generated method stub
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		final HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		final String url = Config.getString(AbhraincCoreConstants.THIRD_PARTY_APPLICATION_IP,
				AbhraincCoreConstants.THIRD_PARTY_APPLICATION_IP) + "/getIpAddress";
		final ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		final WebServiceClient client = new WebServiceClient.Builder(119804, "c9fBzLEntM0E").build();
		{
			try
			{
				/*
				 * try { ipAddress = InetAddress.getByName(ipAddr); response = client.country(ipAddress); } catch (final
				 * Exception e) { e.printStackTrace(); }
				 *
				 * if (response == null) {
				 */
				final String ipadrr = getPublicIp();
				System.out.println("publicip" + ipadrr);
				final String localIp = result.getBody();
				System.out.println("thirdpartyIp" + localIp);
				final InetAddress ipAddress = InetAddress.getByName(localIp);
				final CountryResponse response = client.country(ipAddress);
				final Country country = response.getCountry();
				System.out.println(country.getIsoCode()); // 'US'
				System.out.println(country.getName()); // 'United States'
				return country.getIsoCode();
				//}
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
