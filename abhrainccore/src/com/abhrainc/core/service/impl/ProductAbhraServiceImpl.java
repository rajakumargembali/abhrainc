package com.abhrainc.core.service.impl;

import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.abhrainc.core.dao.ProductAbhraDao;
import com.abhrainc.core.event.OrderTrackingEmailEvent;
import com.abhrainc.core.service.ProductAbhraService;


public class ProductAbhraServiceImpl implements ProductAbhraService
{

	final Logger logger = Logger.getLogger(ProductAbhraServiceImpl.class);

	@Autowired
	BusinessProcessService businessProcessService;

	@Autowired
	private EventService eventService;

	@Autowired
	ProductAbhraDao productAbhraDao;

	@Autowired
	ModelService modelService;

	@Override
	public String updateProductBYCode(final Map products)
	{
		logger.info("product_in_impl" + products);
		final List<ProductModel> productModel = productAbhraDao
				.findProductByCodeandCatalog(products.get("product_code").toString());
		logger.info(productModel);
		for (int j = 0; j < productModel.size(); j++)
		{
			final ProductModel model = productModel.get(j);
			final Collection<PriceRowModel> priceRowModel = model.getEurope1Prices();
			for (final PriceRowModel priceRowModel2 : priceRowModel)
			{
				logger.info("\n\nThese are the prices" + priceRowModel2.getPrice());
				final Double price = (Double) products.get("price");
				priceRowModel2.setPrice(price);
				modelService.save(priceRowModel2);
			}
		}
		//final ProductModel model = productService.getProductForCode(products.get("product_code").toString());
		//System.out.println(model.getCode() + "" + model.getDescription());
		return "Success";
	}

	@Override
	public String updateStockBYCode(final Map products)
	{
		// TODO Auto-generated method stub
		logger.info("product_in_impl" + products);
		final List<ProductModel> productModel = productAbhraDao
				.findProductByCodeandCatalog(products.get("product_code").toString());
		logger.info(productModel);
		for (int j = 0; j < productModel.size(); j++)
		{
			final ProductModel model = productModel.get(j);
			final Set<StockLevelModel> stockLevelModel = model.getStockLevels();
			for (final StockLevelModel stockLevelModel2 : stockLevelModel)
			{
				final int quantity = Integer.parseInt(products.get("quantity").toString());
				stockLevelModel2.setAvailable(quantity);
				modelService.save(stockLevelModel2);
			}


		}
		return "Success";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.core.service.ProductAbhraService#updateProductPricesByConversion(float, float)
	 */
	@Override
	public String updateProductPricesByConversion(final float pound, final float euro)
	{
		// YTODO Auto-generated method stub
		final List<ProductModel> productModel = productAbhraDao.findListOfProducts();
		logger.info(productModel);
		for (int j = 0; j < productModel.size(); j++)
		{

			final ProductModel model = productModel.get(j);
			final Collection<PriceRowModel> priceRowModel = model.getEurope1Prices();
			Double usdPrice = null;
			for (final PriceRowModel priceRowModel2 : priceRowModel)
			{

				if (priceRowModel2.getCurrency().getIsocode().equals("USD")
						|| priceRowModel2.getCurrency().getIsocode().equals("GBP"))
				{
					usdPrice = priceRowModel2.getPrice();
				}
				logger.info("currencynameoutsideJPY and isocode" + priceRowModel2.getCurrency().getName() + "-"
						+ priceRowModel2.getCurrency().getIsocode());
				logger.info("\n\nThese are the prices" + priceRowModel2.getPrice());
				if (priceRowModel2.getCurrency().getIsocode().equals("EUR")
						|| priceRowModel2.getCurrency().getIsocode().equals("JPY"))
				{
					logger.info("currencynameinsideJPY" + priceRowModel2.getCurrency().getName());
					if (usdPrice != null)
					{
						priceRowModel2.setPrice(usdPrice * euro);
						modelService.save(priceRowModel2);
					}
				}

			}
		}
		//final ProductModel model = productService.getProductForCode(products.get("product_code").toString());
		//System.out.println(model.getCode() + "" + model.getDescription());
		return "Success";

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.core.service.ProductAbhraService#sendOrderTrackingEmail()
	 */
	@Override
	public void sendOrderTrackingEmail()
	{
		// YTODO Auto-generated method stub
		final List<ConsignmentModel> consignments = productAbhraDao.getConsignmentsForOrderTrackingEmail();
		if (consignments == null || consignments.size() == 0)
		{
			return;
		}
		final OrderTrackingEmailEvent<BaseSiteModel> event = new OrderTrackingEmailEvent<BaseSiteModel>(consignments);
		logger.debug("Publishing Order Tracking Email Event");
		logger.debug("No of Consignments for Order Tracking Event =  " + consignments.size());
		eventService.publishEvent(event);
		logger.debug("Successfully Published Events for Order Tracking");


	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.core.service.ProductAbhraService#sendOrderConsignmentStatusEmail()
	 */
	@Override
	public void sendOrderConsignmentStatusEmail()
	{
		// YTODO Auto-generated method stub
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		final HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		final String url = "http://localhost:8080/AuditLobby/getConsignmentStatuses";
		final ResponseEntity<Map[]> result = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);
		final Map[] products = result.getBody();
		for (int i = 0; i < products.length; i++)
		{
			logger.info(products[i].get("hybris_consignmentid"));
			final String code = products[i].get("hybris_consignmentid").toString();
			final ConsignmentModel consignmentModel = productAbhraDao.getConsignmentDetailsbyCode(code);
			final boolean status = consignmentModel.getIsDeliveryEmailSent().booleanValue();
			if (consignmentModel.getStatus().equals(ConsignmentStatus.READY))
			{
				consignmentModel.setStatus(ConsignmentStatus.SHIPPED);
			}
			if (consignmentModel.getStatus().equals(ConsignmentStatus.SHIPPED))
			{
				consignmentModel.setStatus(ConsignmentStatus.ACCEPTED);
			}
			else if (consignmentModel.getStatus().equals(ConsignmentStatus.ACCEPTED))
			{
				consignmentModel.setStatus(ConsignmentStatus.DISPATCHED);
			}
			else if (consignmentModel.getStatus().equals(ConsignmentStatus.DISPATCHED))
			{
				consignmentModel.setStatus(ConsignmentStatus.DELIVERED);
				consignmentModel.setIsDeliveryEmailSent(java.lang.Boolean.TRUE);
			}

			modelService.save(consignmentModel);
			if (!consignmentModel.getStatus().equals(ConsignmentStatus.SHIPPED) || !status)
			{
				sendEmail(consignmentModel);
			}

			final String updateUrl = "http://localhost:8080/AuditLobby/update_Consignemnt";
			final HashMap orderData = new HashMap();
			orderData.put("orderid", products[i].get("orderid"));
			orderData.put("consignment_status", consignmentModel.getStatus().getCode());
			restTemplate.put(updateUrl, orderData, HashMap.class);


		}
	}

	/**
	 * @param consignmentModel
	 *
	 */
	private void sendEmail(final ConsignmentModel consignmentModel)
	{
		// YTODO Auto-generated method stub

		final ConsignmentProcessModel consignmentProcessModel = businessProcessService
				.createProcess("sendDeliveryEmailProcess" + System.currentTimeMillis(), "sendDeliveryEmailProcess");

		consignmentProcessModel.setCode(consignmentModel.getCode());
		consignmentProcessModel.setConsignment(consignmentModel);
		final List<EmailAddressModel> addressModels = new ArrayList<>();
		final EmailAddressModel addressModel = new EmailAddressModel();
		addressModel.setEmailAddress(consignmentModel.getOrder().getUser().getUid());
		addressModels.add(addressModel);
		final EmailMessageModel emailMessageModel = new EmailMessageModel();
		emailMessageModel.setToAddresses(addressModels);
		businessProcessService.startProcess(consignmentProcessModel);
	}
}

