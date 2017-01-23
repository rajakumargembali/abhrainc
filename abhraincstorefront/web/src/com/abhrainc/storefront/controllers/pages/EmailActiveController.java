/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class EmailActiveController
{
	final Logger logger = Logger.getLogger(EmailActiveController.class);

	@Autowired
	AbhraIncFacadeService abhraIncFacadeService;


	@RequestMapping(method = RequestMethod.GET)
	public String activateEmail(@RequestParam final String email)
	{
		logger.info("in controller" + email);
		return abhraIncFacadeService.activateEmailAccount(email);
	}

}
