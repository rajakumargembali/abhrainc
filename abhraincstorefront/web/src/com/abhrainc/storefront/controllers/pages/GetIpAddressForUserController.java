/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;


/**
 * @author sujan
 *
 */
public class GetIpAddressForUserController extends GenericFilterBean
{
	/**
	 * @return
	 */
	private String getPublicIp()
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

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain arg2)
			throws IOException, ServletException
	{
		// YTODO Auto-generated method stub
		final String isocode = getISOCode(req.getRemoteAddr());
		final HttpServletResponse response = (HttpServletResponse) res;
		if (isocode != null)
		{
			//res.

			if (isocode.equals("DE"))
			{
				response.sendRedirect("https://localhost:9002/abhraincstorefront/electronics/de/");
			}
			else if (isocode.equals("US") || isocode.equals("IN"))
			{
				response.sendRedirect("https://localhost:9002/abhraincstorefront/electronics/en/");
			}
		}
		else
		{
			response.sendRedirect("https://localhost:9002/abhraincstorefront/electronics/en/");
		}
	}

	/**
	 * @param ipaddr
	 * @return
	 */
	private String getISOCode(final String ipaddr)
	{
		// YTODO Auto-generated method stub
		final WebServiceClient client = new WebServiceClient.Builder(119804, "c9fBzLEntM0E").build();
		{
			try
			{
				String localIp = null;
				try
				{
					final InetAddress ipAddr = InetAddress.getByName(ipaddr);
					localIp = ipAddr.getHostAddress();
				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
				if (localIp.equals(null))
				{
					localIp = getPublicIp();
				}
				final InetAddress ipAddress = InetAddress.getByName(localIp);

				// Do the lookup
				final CountryResponse response = client.country(ipAddress);
				final Country country = response.getCountry();
				System.out.println(country.getIsoCode()); // 'US'
				System.out.println(country.getName()); // 'United States'
				return country.getIsoCode();
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}

		return null;
	}


}
