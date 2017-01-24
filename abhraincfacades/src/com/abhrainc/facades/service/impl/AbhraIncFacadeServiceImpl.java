/**
 *
 */
package com.abhrainc.facades.service.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;

import org.springframework.beans.factory.annotation.Autowired;

import com.abhrainc.facades.dao.AbhraIncFacadeDAO;
import com.abhrainc.facades.service.AbhraIncFacadeService;


/**
 * @author sujan
 *
 */
public class AbhraIncFacadeServiceImpl implements AbhraIncFacadeService
{

	@Autowired
	AbhraIncFacadeDAO abhraIncFacadeDAO;

	@Autowired
	ModelService modelService;

	@Override
	public String activateEmailAccount(final String email)
	{
		final UserModel userModel = abhraIncFacadeDAO.getUserDetails(email);
		userModel.setIsEmailActivated(java.lang.Boolean.TRUE);
		modelService.save(userModel);
		return "/EmailActiveLogin";

	}

}
