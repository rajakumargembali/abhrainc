/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.abhrainc.core.btg.condition.impl;

import de.hybris.platform.core.model.product.ProductModel;

import java.util.Map;
import java.util.Set;


public class AbhraProductCollectionExpressionEvaluator extends AbhraAbstractCollectionDistinctExpressionEvaluator<ProductModel>
{
	private static final String NUMBER_OF_MATCHED_DISTINCT_ITEMS_QUERY = "SELECT DISTINCT {p.pk} FROM {Product AS p JOIN CatalogVersion AS cv ON {p.catalogVersion} = {cv.pk}}, {Product AS p2 JOIN CatalogVersion AS cv2 ON {p2.catalogVersion} = {cv2.pk}} WHERE {p.pk} IN (?right) AND {p2.pk} IN (?left) AND {p.code} = {p2.code} AND {cv.catalog} = {cv2.catalog}";
	private static final String NUMBER_OF_DISTINCT_ITEMS_QUERY = "SELECT DISTINCT {p.code}, {cv.catalog} FROM {Product AS p JOIN CatalogVersion AS cv ON {p.catalogVersion} = {cv.pk}} WHERE {p.pk} IN (?left)";

	public AbhraProductCollectionExpressionEvaluator()
	{
	}

	public AbhraProductCollectionExpressionEvaluator(final Map<String, Set<Class>> operatorMap)
	{
		super(operatorMap);
	}

	@Override
	protected String getNumberOfMatchedDistinctItemsQuery()
	{
		return "SELECT DISTINCT {p.pk} FROM {Product AS p JOIN CatalogVersion AS cv ON {p.catalogVersion} = {cv.pk}}, {Product AS p2 JOIN CatalogVersion AS cv2 ON {p2.catalogVersion} = {cv2.pk}} WHERE {p.pk} IN (?right) AND {p2.pk} IN (?left) AND {p.code} = {p2.code} AND {cv.catalog} = {cv2.catalog}";
	}

	@Override
	protected String getNumberOfDistinctItemsQuery()
	{
		return "SELECT DISTINCT {p.code}, {cv.catalog} FROM {Product AS p JOIN CatalogVersion AS cv ON {p.catalogVersion} = {cv.pk}} WHERE {p.pk} IN (?left)";
	}
}