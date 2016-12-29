package com.abhrainc.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.abhrainc.core.dao.ProductAbhraDao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

public class ProductAbhraDaoImpl  implements ProductAbhraDao  {

	final Logger logger = Logger.getLogger(ProductAbhraDaoImpl.class);
	
	@Resource(name = "flexibleSearchService")
	FlexibleSearchService flexibleSearchService;
	
	@Override
	public List<ProductModel> findProductByCodeandCatalog(String code) {
		// TODO Auto-generated method stub
		String query = "SELECT {pk} FROM {Product} where P_CODE ="+"'"+code+"'";
		FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		/*final SearchResult searchResult = search(searchQuery);
		logger.info(""+searchResult.getResult());*/
		logger.info("search query"+flexibleSearchService.<ProductModel>search(searchQuery).getResult());
		List<ProductModel> model =   flexibleSearchService.<ProductModel>search(searchQuery).getResult();
		return model;
	}

}
