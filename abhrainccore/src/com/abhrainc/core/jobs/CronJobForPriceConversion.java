/**
 *
 */
package com.abhrainc.core.jobs;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.abhrainc.core.service.ProductAbhraService;


/**
 * @author sujan
 *
 */
public class CronJobForPriceConversion extends AbstractJobPerformable<CronJobModel>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.
	 * CronJobModel)
	 */
	@Resource(name = "productAbhraService")
	ProductAbhraService productAbhraService;

	final Logger logger = Logger.getLogger(CronJobForPriceConversion.class);

	@Override
	public PerformResult perform(final CronJobModel arg0)
	{
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		final HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);


		try
		{
			final String url = "http://192.168.2.96:8080/AuditLobby/pound_To_Euro_Conversion";
			final ResponseEntity<Map> result = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
			final Map products = result.getBody();
			logger.info(products);
			final float euro = Float.parseFloat(products.get("Euro").toString());
			final float pound = Float.parseFloat(products.get("Pound").toString());
			logger.info("prices ratio" + euro + ":" + pound);
			productAbhraService.updateProductPricesByConversion(pound, euro);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}


}
