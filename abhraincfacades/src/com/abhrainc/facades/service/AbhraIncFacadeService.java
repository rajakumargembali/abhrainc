/**
 *
 */
package com.abhrainc.facades.service;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;


/**
 * @author sujan
 *
 */
public interface AbhraIncFacadeService
{

	/**
	 * @param email
	 * @return
	 */
	String activateEmailAccount(String email);

	/**
	 * @param code
	 * @return
	 */
	CartModel getCartDetails(String code);

	/**
	 * @param orderData
	 * @return
	 */
	AbstractOrderModel getAbstractorderDetails(OrderData orderData);

	/**
	 * @param orderModel
	 * @return
	 */
	AbstractOrderEntryModel getorderEntryDetails(AbstractOrderModel orderModel);

	/**
	 * @param orderData
	 * @return
	 */
	OrderModel getOrderDetails(OrderData orderData);

}
