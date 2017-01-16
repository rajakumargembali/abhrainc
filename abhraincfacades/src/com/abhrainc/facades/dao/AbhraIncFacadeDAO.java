/**
 *
 */
package com.abhrainc.facades.dao;

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

	OrderModel getOrderDetails(String code);
}
