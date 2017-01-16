/**
 *
 */
package com.abhrainc.facades.dao;

<<<<<<< HEAD
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ordersplitting.model.ConsignmentModel;
=======
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
>>>>>>> praneeth


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
<<<<<<< HEAD
	 * @param pk
	 * @return
	 */
	ConsignmentModel getConsignmentDetails(PK pk);

	/**
	 * @param string
	 * @return
	 */
	OrderModel getOrderDetailsForOrder(String string);
=======
	 * @param code
	 * @return
	 */
	OrderModel getOrderDetails(String code);
>>>>>>> praneeth

}
