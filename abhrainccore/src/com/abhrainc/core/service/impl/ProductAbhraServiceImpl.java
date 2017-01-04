package com.abhrainc.core.service.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.abhrainc.core.dao.ProductAbhraDao;
import com.abhrainc.core.service.ProductAbhraService;


public class ProductAbhraServiceImpl implements ProductAbhraService
{

	final Logger logger = Logger.getLogger(ProductAbhraServiceImpl.class);

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
}

