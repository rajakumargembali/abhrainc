package com.abhrainc.core.dao;

<<<<<<< HEAD
import de.hybris.platform.core.model.product.ProductModel;
<<<<<<< HEAD
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
=======
>>>>>>> praneeth
=======
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
>>>>>>> naresh

import java.util.List;


public interface ProductAbhraDao
{

	List<ProductModel> findProductByCodeandCatalog(String code);

	/**
	 * @return
	 */
<<<<<<< HEAD
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
=======
	List<ProductModel> getListOfProducts(CatalogVersionModel catalogVersionModel);

>>>>>>> naresh
}
