/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abhrainc.facades.service.AbhraIncFacadeService;


/**
 * @author sujan
 *
 */
@Controller
@RequestMapping(value = "/activateEmail")
public class EmailActiveController extends AbstractPageController
{
	final Logger logger = Logger.getLogger(EmailActiveController.class);

	@Autowired
	AbhraIncFacadeService abhraIncFacadeService;


	@RequestMapping(method = RequestMethod.GET)
	public String activateEmail(@RequestParam final String email, final Model model) throws CMSItemNotFoundException
	{
		logger.info("in controller" + email);
		final String url = abhraIncFacadeService.activateEmailAccount(email);

		storeCmsPageInModel(model, getContentPageForLabelOrId(url));
		return getViewForPage(model);
	}

}
