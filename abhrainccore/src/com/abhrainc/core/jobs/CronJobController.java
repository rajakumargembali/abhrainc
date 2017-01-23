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


public class CronJobController extends AbstractJobPerformable<CronJobModel>
{


	@Resource(name = "productAbhraService")
	ProductAbhraService productAbhraService;

	final Logger logger = Logger.getLogger(CronJobController.class);

	@Override
	public PerformResult perform(final CronJobModel arg0)
	{
		// TODO Auto-generated method stub

		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		final HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);


		try
		{
			final String url = "http://192.168.1.236:8080/AuditLobby/product_Detail_and_Price";
			final ResponseEntity<Map[]> result = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);
			final Map[] products = result.getBody();
			for (int i = 0; i < products.length; i++)
			{
				productAbhraService.updateProductBYCode(products[i]);
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

}
