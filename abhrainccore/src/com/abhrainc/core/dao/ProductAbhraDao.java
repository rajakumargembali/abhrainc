package com.abhrainc.core.dao;

import de.hybris.platform.core.model.product.ProductModel;
<<<<<<< HEAD
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
=======
>>>>>>> praneeth

import java.util.List;


public interface ProductAbhraDao
{

	List<ProductModel> findProductByCodeandCatalog(String code);

	/**
	 * @return
	 */
	List<ProductModel> findListOfProducts();

<<<<<<< HEAD
	/**
	 * @return
	 */
	List<ConsignmentModel> getConsignmentsForOrderTrackingEmail();

	/**
	 * @param code
	 * @return
	 */
	ConsignmentModel getConsignmentDetailsbyCode(String code);

=======
>>>>>>> praneeth
}
