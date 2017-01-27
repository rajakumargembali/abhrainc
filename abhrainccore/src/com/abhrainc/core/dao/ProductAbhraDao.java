package com.abhrainc.core.dao;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;

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

	/**
	 * @return
	 */
	List<WarehouseModel> getWareHouseDetails();

	/**
	 * @return
	 */
	List<UnitModel> getunitModelDetails();

	/**
	 * @return
	 */
	List<CurrencyModel> getCurrencyModelDetaails();

}
