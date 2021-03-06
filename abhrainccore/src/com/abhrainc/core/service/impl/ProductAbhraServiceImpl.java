package com.abhrainc.core.service.impl;

import de.hybris.platform.basecommerce.enums.ConsignmentStatus;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.order.price.TaxModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.europe1.model.TaxRowModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;

import java.util.Arrays;
import java.util.Collection;
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

import com.abhrainc.core.constants.AbhraincCoreConstants;
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
			final CatalogVersionModel catalogModel = model.getCatalogVersion();
			logger.info("this is the catalog" + catalogModel);
			logger.info("version of catalog" + catalogModel.getCatalog().getVersion());
			final Collection<PriceRowModel> priceRowModel = model.getEurope1Prices();
			final Double price = (Double) products.get("price");
			if (!priceRowModel.isEmpty())
			{
				for (final PriceRowModel priceRowModel2 : priceRowModel)
				{
					logger.info("\n\nThese are the prices" + priceRowModel2.getPrice());
					if (priceRowModel2.getCurrency().getIsocode().equals("USD"))
					{
						priceRowModel2.setPrice(price);
						modelService.save(priceRowModel2);
					}
				}
			}
			else
			{
				//
				final TaxRowModel taxRow = new TaxRowModel();
				final List<TaxModel> taxModels = productAbhraDao.getTaxModelDetails();
				taxRow.setTax(taxModels.get(0));
				final PriceRowModel priceRow = new PriceRowModel();
				priceRow.setPrice(price);
				priceRow.setProduct(model);
				final List<UnitModel> unitModel = productAbhraDao.getunitModelDetails();
				priceRow.setUnit(unitModel.get(0));
				final List<CurrencyModel> currencyModel = productAbhraDao.getCurrencyModelDetaails();
				for (int i = 0; i < currencyModel.size(); i++)
				{
					if (currencyModel.get(i).getIsocode().equals("USD"))
					{
						priceRow.setCurrency(currencyModel.get(i));
					}
				}

				modelService.save(priceRow);
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
			final List<WarehouseModel> warehouseModel = productAbhraDao.getWareHouseDetails();
			final Set<StockLevelModel> stockLevelModel = model.getStockLevels();
			final int quantity = Integer.parseInt(products.get("quantity").toString());
			if (!stockLevelModel.isEmpty())
			{
				for (final StockLevelModel stockLevelModel2 : stockLevelModel)
				{
					stockLevelModel2.setAvailable(quantity);
					modelService.save(stockLevelModel2);
				}
			}
			else
			{
				final StockLevelModel levelModel = new StockLevelModel();
				levelModel.setAvailable(quantity);
				levelModel.setProduct(model);
				levelModel.setProductCode(model.getCode());
				for (int i = 0; i < warehouseModel.size(); i++)
				{
					if (warehouseModel.get(i).getCode().equals("warehouse_n"))
					{
						levelModel.setWarehouse(warehouseModel.get(i));
					}
				}

				modelService.save(levelModel);
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
			if (priceRowModel.size() > 1)
			{
				for (final PriceRowModel priceRowModel2 : priceRowModel)
				{
					if (priceRowModel2.getCurrency().getIsocode().equals("USD"))
					{
						usdPrice = priceRowModel2.getPrice();
					}
					logger.info("currencynameoutsideJPY and isocode" + priceRowModel2.getCurrency().getName() + "-"
							+ priceRowModel2.getCurrency().getIsocode());
					logger.info("\n\nThese are the prices" + priceRowModel2.getPrice());
					if (priceRowModel2.getCurrency().getIsocode().equals("EUR"))
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
			else
			{
				boolean euroPrice = false;
				for (final PriceRowModel priceRowModel2 : priceRowModel)
				{
					if (priceRowModel2.getCurrency().getIsocode().equals("EUR"))
					{
						euroPrice = true;
					}
				}
				if (!euroPrice)
				{
					for (final PriceRowModel priceRowModel2 : priceRowModel)
					{
						if (priceRowModel2.getCurrency().getIsocode().equals("USD"))
						{
							usdPrice = priceRowModel2.getPrice();
						}
						final PriceRowModel rowModel = new PriceRowModel();
						if (usdPrice != null)
						{
							rowModel.setPrice(usdPrice * euro);
							rowModel.setProduct(model);
							final List<UnitModel> unitModel = productAbhraDao.getunitModelDetails();
							rowModel.setUnit(unitModel.get(0));
							final List<CurrencyModel> currencyModel = productAbhraDao.getCurrencyModelDetaails();
							for (int i = 0; i < currencyModel.size(); i++)
							{
								if (currencyModel.get(i).getIsocode().equals("EUR"))
								{
									rowModel.setCurrency(currencyModel.get(i));
								}
							}
							modelService.save(rowModel);
						}
					}
				}
			}
			/*
			 * for (final PriceRowModel priceRowModel2 : priceRowModel) {
			 *
			 * if (priceRowModel2.getCurrency().getIsocode().equals("USD")) { usdPrice = priceRowModel2.getPrice(); }
			 * logger.info("currencynameoutsideJPY and isocode" + priceRowModel2.getCurrency().getName() + "-" +
			 * priceRowModel2.getCurrency().getIsocode()); logger.info("\n\nThese are the prices" +
			 * priceRowModel2.getPrice()); if (priceRowModel2.getCurrency().getIsocode().equals("EUR")) {
			 * logger.info("currencynameinsideJPY" + priceRowModel2.getCurrency().getName()); if (usdPrice != null) {
			 * priceRowModel2.setPrice(usdPrice * euro); modelService.save(priceRowModel2); } } else if
			 * (!priceRowModel2.getCurrency().getIsocode().equals("USD")) { final PriceRowModel rowModel = new
			 * PriceRowModel(); if (usdPrice != null) { rowModel.setPrice(usdPrice * euro); rowModel.setProduct(model);
			 * final List<UnitModel> unitModel = productAbhraDao.getunitModelDetails(); rowModel.setUnit(unitModel.get(0));
			 * final List<CurrencyModel> currencyModel = productAbhraDao.getCurrencyModelDetaails(); for (int i = 0; i <
			 * currencyModel.size(); i++) { if (currencyModel.get(i).getIsocode().equals("EUR")) {
			 * rowModel.setCurrency(currencyModel.get(i)); } } modelService.save(rowModel); } } }
			 */
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
		final String url = Config.getString(AbhraincCoreConstants.THIRD_PARTY_APPLICATION_IP,
				AbhraincCoreConstants.THIRD_PARTY_APPLICATION_IP) + "/getConsignmentStatuses";
		final ResponseEntity<Map[]> result = restTemplate.exchange(url, HttpMethod.GET, entity, Map[].class);
		final Map[] products = result.getBody();
		for (int i = 0; i < products.length; i++)
		{
			logger.info(products[i].get("hybris_consignmentid"));
			final String code = products[i].get("hybris_consignmentid").toString();
			final ConsignmentModel consignmentModel = productAbhraDao.getConsignmentDetailsbyCode(code);
			final boolean status = consignmentModel.getIsDeliveryEmailSent().booleanValue();
			if (!status)
			{
				if (products[i].get("consignment_status").toString().equals(ConsignmentStatus.ACCEPTED.getCode()))
				{
					for (final ConsignmentProcessModel process : consignmentModel.getConsignmentProcesses())
					{
						businessProcessService.triggerEvent(process.getCode() + "_ConsignmentOrderAccepted");
					}
				}
				else if (products[i].get("consignment_status").toString().equals(ConsignmentStatus.DISPATCHED.getCode()))
				{
					for (final ConsignmentProcessModel process : consignmentModel.getConsignmentProcesses())
					{
						businessProcessService.triggerEvent(process.getCode() + "_ConsignmentOrderDispatched");
					}
				}
				else if (products[i].get("consignment_status").toString().equals(ConsignmentStatus.DELIVERED.getCode()))
				{
					for (final ConsignmentProcessModel process : consignmentModel.getConsignmentProcesses())
					{
						businessProcessService.triggerEvent(process.getCode() + "_ConsignmentOrderDelivered");
						consignmentModel.setIsDeliveryEmailSent(true);
						modelService.save(consignmentModel);
					}
				}
			}
		}
	}

	/**
	 * @param consignmentModel
	 *
	 */
	/*
	 * private void sendEmail(final ConsignmentModel consignmentModel) { // YTODO Auto-generated method stub
	 *
	 * final ConsignmentProcessModel consignmentProcessModel = businessProcessService
	 * .createProcess("sendDeliveryEmailProcess" + System.currentTimeMillis(), "sendDeliveryEmailProcess");
	 *
	 * consignmentProcessModel.setCode(consignmentModel.getCode());
	 * consignmentProcessModel.setConsignment(consignmentModel);
	 *
	 * final List<EmailAddressModel> addressModels = new ArrayList<>(); final EmailAddressModel addressModel = new
	 * EmailAddressModel(); addressModel.setEmailAddress(consignmentModel.getOrder().getUser().getUid());
	 * addressModels.add(addressModel); final EmailMessageModel emailMessageModel = new EmailMessageModel();
	 * emailMessageModel.setToAddresses(addressModels); businessProcessService.startProcess(consignmentProcessModel); }
	 */

}
