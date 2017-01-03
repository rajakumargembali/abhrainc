/**
 *
 */
package com.abhrainc.facades.dao;

import de.hybris.platform.core.model.user.UserModel;


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

}
