package com.abhrainc.core.dao;

import java.util.List;


public interface ProductAbhraDao
{

	List<ProductModel> findProductByCodeandCatalog(String code);

	/**
	 * @return
	 */
	List<ProductModel> findListOfProducts();

	/**
	 * @return
	 */
	List<ConsignmentModel> getConsignmentsForOrderTrackingEmail();

	/**
	 * @param code
	 * @return
	 */
	ConsignmentModel getConsignmentDetailsbyCode(String code);

	List<ProductModel> getListOfProducts(CatalogVersionModel catalogVersionModel);

}
