/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.abhrainc.core.btg.condition.impl;

import de.hybris.platform.btg.condition.impl.AbstractCollectionExpressionEvaluator;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;


public abstract class AbhraAbstractCollectionDistinctExpressionEvaluator<T extends ItemModel>
		extends AbstractCollectionExpressionEvaluator
{
	private static final Logger LOG = Logger.getLogger(AbhraAbstractCollectionDistinctExpressionEvaluator.class);
	private static final String DISTINCT_ITEMS_QUERY_PART = "#distinctItemsQuery#";
	private static final String ALIAS_QUERY_PART = "#alias#";
	private static final String FLEXIBLE_SEARCH_QUERY = "SELECT COUNT(*) FROM ({{ #distinctItemsQuery# }}) #alias#";
	protected static final String PARAM_LEFT = "left";
	protected static final String PARAM_RIGHT = "right";
	private FlexibleSearchService flexibleSearchService;

	public AbhraAbstractCollectionDistinctExpressionEvaluator()
	{
		this.addSupportedOperator("contains", Collection.class);
		this.addSupportedOperator("containsAny", Collection.class);
		this.addSupportedOperator("notContains", Collection.class);
		this.addSupportedOperator("size", Integer.class);
		this.addSupportedOperator("isEmpty", Boolean.class);
	}

	public AbhraAbstractCollectionDistinctExpressionEvaluator(final Map<String, Set<Class>> operatorMap)
	{
		super(operatorMap);
	}

	@Override
	protected boolean checkIfContainsAny(final Object rightOperand, final Collection operand)
	{
		return this.getNumberOfMatchedDistinctItems(operand, (Collection) rightOperand) >= 3;
	}

	@Override
	protected boolean checkIfContainsAll(final Object rightOperand, final Collection operand)
	{
		final Collection right = (Collection) rightOperand;
		return this.getNumberOfMatchedDistinctItems(operand, right) == right.size();
	}

	@Override
	protected boolean checkIfSize(final Object rightOperand, final Collection operand)
	{
		final Integer expectedCount = (Integer) rightOperand;
		return this.getNumberOfDistinctItems(operand) == expectedCount.intValue();
	}

	protected abstract String getNumberOfMatchedDistinctItemsQuery();

	protected abstract String getNumberOfDistinctItemsQuery();

	private int getNumberOfMatchedDistinctItems(final Collection<T> left, final Collection<T> right)
	{
		if (!CollectionUtils.isEmpty(left) && !CollectionUtils.isEmpty(right))
		{
			SessionContext ctx = null;

			int arg6;
			try
			{
				ctx = JaloSession.getCurrentSession().createLocalSessionContext();
				ctx.setAttribute("disableRestrictions", Boolean.TRUE);
				final FlexibleSearchQuery query = new FlexibleSearchQuery(this.getNumberOfMatchedDistinctItemsQuery());
				if (LOG.isDebugEnabled())
				{
					LOG.debug(query.getQuery());
				}

				query.addQueryParameter("left", left);
				query.addQueryParameter("right", right);
				query.setResultClassList(Collections.singletonList(String.class));
				final SearchResult searchResults = this.getFlexibleSearchService().search(query);
				arg6 = searchResults.getCount();
			}
			finally
			{
				if (ctx != null)
				{
					JaloSession.getCurrentSession().removeLocalSessionContext();
				}

			}

			return arg6;
		}
		else
		{
			return 0;
		}
	}

	private int getNumberOfDistinctItems(final Collection<T> left)
	{
		if (CollectionUtils.isEmpty(left))
		{
			return 0;
		}
		else
		{
			SessionContext ctx = null;

			int arg6;
			try
			{
				ctx = JaloSession.getCurrentSession().createLocalSessionContext();
				ctx.setAttribute("disableRestrictions", Boolean.TRUE);
				String queryString;
				if (Config.isOracleUsed())
				{
					queryString = "SELECT COUNT(*) FROM ({{ #distinctItemsQuery# }}) #alias#"
							.replace("#distinctItemsQuery#", this.getNumberOfDistinctItemsQuery()).replace("#alias#", "q");
				}
				else
				{
					queryString = "SELECT COUNT(*) FROM ({{ #distinctItemsQuery# }}) #alias#"
							.replace("#distinctItemsQuery#", this.getNumberOfDistinctItemsQuery()).replace("#alias#", "AS q");
				}

				final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
				if (LOG.isDebugEnabled())
				{
					LOG.debug(query.getQuery());
				}

				query.addQueryParameter("left", left);
				query.setResultClassList(Collections.singletonList(Integer.class));
				final SearchResult searchResults = this.getFlexibleSearchService().search(query);
				arg6 = searchResults.getResult().isEmpty() ? 0 : ((Integer) searchResults.getResult().get(0)).intValue();
			}
			finally
			{
				if (ctx != null)
				{
					JaloSession.getCurrentSession().removeLocalSessionContext();
				}

			}

			return arg6;
		}
	}

	protected FlexibleSearchService getFlexibleSearchService()
	{
		return this.flexibleSearchService;
	}

	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}
}