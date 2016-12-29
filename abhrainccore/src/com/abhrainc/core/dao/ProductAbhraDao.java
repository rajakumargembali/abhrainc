package com.abhrainc.core.dao;

import java.util.List;

import de.hybris.platform.core.model.product.ProductModel;

public interface ProductAbhraDao {

	List<ProductModel> findProductByCodeandCatalog(String code);

}
