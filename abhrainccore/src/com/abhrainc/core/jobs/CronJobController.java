package com.abhrainc.core.jobs;

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

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
public class CronJobController extends AbstractJobPerformable<CronJobModel> {

	
	@Resource(name = "productAbhraService")
	ProductAbhraService productAbhraService;
	
	final Logger logger = Logger.getLogger(CronJobController.class);
	
	@Override
	public PerformResult perform(CronJobModel arg0) {
		// TODO Auto-generated method stub
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		
		try
		{
			String url = "http://localhost:8080/AuditLobby/product_Detail_and_Price";
		    ResponseEntity<Map[]> result = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);
		    Map[] products = result.getBody();
		    for (int i = 0; i < products.length; i++) {
				String msh = productAbhraService.updateProductBYCode(products[i]);
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
