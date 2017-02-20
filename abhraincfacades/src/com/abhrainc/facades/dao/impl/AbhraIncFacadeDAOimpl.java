/**
 *
 */
package com.abhrainc.facades.dao.impl;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.abhrainc.facades.dao.AbhraIncFacadeDAO;


/**
 * @author sujan
 *
 */
public class AbhraIncFacadeDAOimpl implements AbhraIncFacadeDAO
{
	@Resource(name = "flexibleSearchService")
	FlexibleSearchService flexibleSearchService;

	final Logger logger = Logger.getLogger(AbhraIncFacadeDAOimpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.dao.AbhraIncFacadeDAO#getUserDetails(java.lang.String)
	 */
	@Override
	public UserModel getUserDetails(final String email)
	{
		// YTODO Auto-generated method stub
		final String query = "SELECT {pk} FROM {user} where P_ORIGINALUID =" + "'" + email + "'";
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(query);
		/*
		 * final SearchResult searchResult = search(searchQuery); logger.info(""+searchResult.getResult());
		 */
		logger.info("search query" + flexibleSearchService.<UserModel> search(searchQuery).getResult());
		final List<UserModel> model = flexibleSearchService.<UserModel> search(searchQuery).getResult();
		return model.get(0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.dao.AbhraIncFacadeDAO#getOrderDetails(java.lang.String)
	 */
	// YTODO Auto-generated method stub

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.dao.AbhraIncFacadeDAO#getOrderDetailsForOrder(java.lang.String)
	 */
	@Override
	public OrderModel getOrderDetailsForOrder(final String code)
	{
		// YTODO Auto-generated method stub
		final String query = "select {pk} from {order} where P_CODE = " + "'" + code + "'";
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
		final List<OrderModel> model = flexibleSearchService.<OrderModel> search(flexibleSearchQuery).getResult();
		return model.get(0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.dao.AbhraIncFacadeDAO#getConsignmentDetails(de.hybris.platform.core.PK)
	 */
	@Override
	public ConsignmentModel getConsignmentDetails(final PK pk)
	{
		// YTODO Auto-generated method stub
		final String query = "select {pk} from {consignment} where P_ORDER = " + "'" + pk + "'";
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
		final List<ConsignmentModel> model = flexibleSearchService.<ConsignmentModel> search(flexibleSearchQuery).getResult();
		return model.get(0);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.dao.AbhraIncFacadeDAO#getCartDetails(java.lang.String)
	 */
	@Override
	public CartModel getCartDetails(final String code)
	{
		// YTODO Auto-generated method stub
		// YTODO Auto-generated method stub
		final String query = "select {pk} from {cart} where P_CODE = " + "'" + code + "'";
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
		final List<CartModel> model = flexibleSearchService.<CartModel> search(flexibleSearchQuery).getResult();
		return model.get(0);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.dao.AbhraIncFacadeDAO#getAbstractOrderDetails(java.lang.String)
	 */
	@Override
	public AbstractOrderModel getAbstractOrderDetails(final String code)
	{
		// YTODO Auto-generated method stub
		final String query = "select {pk} from {AbstractOrder} where P_CODE = " + "'" + code + "'";
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
		final List<AbstractOrderModel> model = flexibleSearchService.<AbstractOrderModel> search(flexibleSearchQuery).getResult();
		return model.get(0);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.dao.AbhraIncFacadeDAO#getOrderEntryModels(de.hybris.platform.core.PK)
	 */
	@Override
	public AbstractOrderEntryModel getOrderEntryModels(final PK pk)
	{
		// YTODO Auto-generated method stub
		final String query = "select {pk} from {orderentry} where P_ORDER = " + "'" + pk + "'";
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
		final List<AbstractOrderEntryModel> model = flexibleSearchService.<AbstractOrderEntryModel> search(flexibleSearchQuery)
				.getResult();
		return model.get(0);

	}

}
