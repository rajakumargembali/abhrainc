/**
 *
 */
package com.abhrainc.core.jobs;

import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.abhrainc.core.dao.ProductAbhraDao;
import com.abhrainc.core.model.GoogleProductSearchModel;


/**
 * @author Naresh Athukuri
 *
 */
public class SeoController extends AbstractJobPerformable<GoogleProductSearchModel>

{
	final Logger logger = Logger.getLogger(SeoController.class);

	@Autowired
	ProductAbhraDao productAbhraDao;
	@Autowired
	@Qualifier("productModelUrlResolver")
	private UrlResolver<ProductModel> productModelUrlResolver;

	@Override
	public PerformResult perform(final GoogleProductSearchModel model)
	{

		final List<ProductModel> products = productAbhraDao.getListOfProducts(model.getCatalogVersion());
		try
		{
			final PrintWriter writer = new PrintWriter("C:/Users/Naresh Athukuri/Desktop/googlesearchurl/producturls.csv", "UTF-8");
			for (final ProductModel product : products)
			{

				System.out.println("name:" + product.getName());
				System.out.println("url:" + productModelUrlResolver.resolve(product));
				logger.info("name:" + product.getName());
				logger.info("url:" + productModelUrlResolver.resolve(product));



				writer.println(product.getCode() + "," + productModelUrlResolver.resolve(product) + "," + product.getGalleryImages());




			}

			writer.close();


		}
		catch (final FileNotFoundException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final UnsupportedEncodingException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
		//
	}
}
