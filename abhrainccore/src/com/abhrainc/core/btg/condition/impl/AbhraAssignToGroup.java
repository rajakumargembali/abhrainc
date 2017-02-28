/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.abhrainc.core.btg.condition.impl;

import de.hybris.platform.btg.model.BTGAssignToGroupDefinitionModel;
import de.hybris.platform.btg.outputaction.OutputActionContext;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.action.impl.ActionPerformable;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.model.action.AbstractActionModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Required;


public class AbhraAssignToGroup implements ActionPerformable<OutputActionContext>
{
	private ModelService modelService;
	private UserService userService;

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	public void performAction(final AbstractActionModel action, final OutputActionContext argument)
	{
		final Collection userGroups = ((BTGAssignToGroupDefinitionModel) argument.getActionDefinition()).getUserGroups();
		final UserModel user = argument.getUser();
		if (!this.userService.isAnonymousUser(user))
		{
			//HashSet alreadyInGroups = new HashSet(user.getGroups());
			final HashSet alreadyInGroups = new HashSet();
			alreadyInGroups.addAll(userGroups);
			//alreadyInGroups.addAll(user.getGroups().;
			user.setGroups(alreadyInGroups);
			this.modelService.save(user);
		}

	}
}