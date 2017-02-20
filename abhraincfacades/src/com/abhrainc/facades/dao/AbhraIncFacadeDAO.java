/**
 *
 */
package com.abhrainc.facades.dao;

import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;


/**
 * @author sujan
 *
 */
public interface AbhraIncFacadeDAO
{

	/**
	 * @param email
	 * @return
	 */
	UserModel getUserDetails(String email);

	/**
	 *
	 * @param pk
	 * @return
	 */
	ConsignmentModel getConsignmentDetails(PK pk);

	/**
	 * @param string
	 * @return
	 */
	OrderModel getOrderDetailsForOrder(String string);

	/**
	 * @param code
	 * @return
	 */
	CartModel getCartDetails(String code);

	/**
	 * @param code
	 * @return
	 */
	AbstractOrderModel getAbstractOrderDetails(String code);

	/**
	 * @param pk
	 * @return
	 */
	AbstractOrderEntryModel getOrderEntryModels(PK pk);

	//	OrderModel getOrderDetails(String code);
}
