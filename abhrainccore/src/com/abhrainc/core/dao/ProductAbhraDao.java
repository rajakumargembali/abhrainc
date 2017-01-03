package com.abhrainc.core.dao;

import de.hybris.platform.core.model.product.ProductModel;

import java.util.List;


public interface ProductAbhraDao
{

	List<ProductModel> findProductByCodeandCatalog(String code);

	/**
	 * @return
	 */
	List<ProductModel> findListOfProducts();

}
