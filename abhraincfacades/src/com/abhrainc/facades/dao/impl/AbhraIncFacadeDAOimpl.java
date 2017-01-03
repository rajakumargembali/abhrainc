/**
 *
 */
package com.abhrainc.facades.dao.impl;

import de.hybris.platform.core.model.user.UserModel;
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

}
