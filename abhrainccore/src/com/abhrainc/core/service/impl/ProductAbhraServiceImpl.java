package com.abhrainc.core.service.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
			final CatalogVersionModel catalogModel = model.getCatalogVersion();
			logger.info("this is the catalog" + catalogModel);
			logger.info("version of catalog" + catalogModel.getCatalog().getVersion());
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
			//	ProductModel model = productModel.get(j);


		}
		//final ProductModel model = productService.getProductForCode(products.get("product_code").toString());
		//System.out.println(model.getCode() + "" + model.getDescription());
		return "Success";
	}
}
