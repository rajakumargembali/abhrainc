package com.abhrainc.core.dao.impl;

<<<<<<< HEAD
import de.hybris.platform.core.model.product.ProductModel;
<<<<<<< HEAD
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
=======
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
>>>>>>> praneeth
=======
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
>>>>>>> naresh

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

<<<<<<< HEAD
	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.core.dao.ProductAbhraDao#findListOfProducts()
	 */
	@Override
	public List<ProductModel> findListOfProducts()
	{
		// YTODO Auto-generated method stub
		final String query = "SELECT {pk} FROM {Product}";
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		/*
		 * final SearchResult searchResult = search(searchQuery); logger.info(""+searchResult.getResult());
		 */
		logger.info("search query" + flexibleSearchService.<ProductModel> search(searchQuery).getResult());
=======


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
>>>>>>> naresh
		final List<ProductModel> model = flexibleSearchService.<ProductModel> search(searchQuery).getResult();
		return model;
	}

<<<<<<< HEAD
<<<<<<< HEAD
	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.core.dao.ProductAbhraDao#getConsignmentsForOrderTrackingEmail()
	 */
	@Override
	public List<ConsignmentModel> getConsignmentsForOrderTrackingEmail()
	{
		// YTODO Auto-generated method stub
		final String search_query = "select {PK} from {consignment}";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(search_query);
		final SearchResult<ConsignmentModel> searchResult = flexibleSearchService.search(query);
		return searchResult.getResult();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.core.dao.ProductAbhraDao#getConsignmentDetailsbyCode(java.lang.String)
	 */
	@Override
	public ConsignmentModel getConsignmentDetailsbyCode(final String code)
	{
		// YTODO Auto-generated method stub
		final String search_query = "select {PK} from {consignment} where P_CODE = " + "'" + code + "'";
		final FlexibleSearchQuery query = new FlexibleSearchQuery(search_query);
		final SearchResult<ConsignmentModel> searchResult = flexibleSearchService.search(query);
		return searchResult.getResult().get(0);
	}

=======
>>>>>>> praneeth
=======


>>>>>>> naresh
}
