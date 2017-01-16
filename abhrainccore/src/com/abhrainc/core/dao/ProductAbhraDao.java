package com.abhrainc.core.dao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;

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

}
