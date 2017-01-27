/**
 *
 */
package com.abhrainc.core.jobs;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.abhrainc.core.service.ProductAbhraService;


/**
 * @author sujan
 *
 */
public class CronJobToTrackOrder extends AbstractJobPerformable<CronJobModel>
{

	@Autowired
	ProductAbhraService abhraService;

	private static final Logger LOG = Logger.getLogger(CronJobToTrackOrder.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.
	 * CronJobModel)
	 */
	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		// YTODO Auto-generated method stub
		// YTODO Auto-generated method stub
		LOG.debug("Starting the Cronjob[Order Tracking Cron]  with code [" + cronJob.getCode() + "]");
		abhraService.sendOrderConsignmentStatusEmail();
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);

	}

}
