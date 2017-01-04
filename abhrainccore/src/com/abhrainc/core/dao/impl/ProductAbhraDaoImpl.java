package com.abhrainc.core.dao.impl;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.abhrainc.core.dao.ProductAbhraDao;


public class ProductAbhraDaoImpl implements ProductAbhraDao
{

	final Logger logger = Logger.getLogger(ProductAbhraDaoImpl.class);

	@Resource(name = "flexibleSearchService")
	FlexibleSearchService flexibleSearchService;

	@Override
	public List<ProductModel> findProductByCodeandCatalog(final String code)
	{
		// TODO Auto-generated method stub
		final String query = "SELECT {pk} FROM {Product} where P_CODE =" + "'" + code + "'";
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		/*
		 * final SearchResult searchResult = search(searchQuery); logger.info(""+searchResult.getResult());
		 */
		logger.info("search query" + flexibleSearchService.<ProductModel> search(searchQuery).getResult());
		final List<ProductModel> model = flexibleSearchService.<ProductModel> search(searchQuery).getResult();
		return model;
	}



	@Override
	public List<ProductModel> getListOfProducts(final CatalogVersionModel catalogVersionModel)
	{
		// TODO Auto-generated method stub
		final String query = "SELECT {pk} FROM {Product} where {catalogVersion}=?catalogVersion";
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		searchQuery.addQueryParameter("catalogVersion", catalogVersionModel.getPk());
		/*
		 * final SearchResult searchResult = search(searchQuery); logger.info(""+searchResult.getResult());
		 */
		final List<ProductModel> model = flexibleSearchService.<ProductModel> search(searchQuery).getResult();
		return model;
	}



}
