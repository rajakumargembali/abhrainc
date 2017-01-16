/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2016 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractLoginPageController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abhrainc.storefront.controllers.ControllerConstants;


/**
 * Login Controller. Handles login and register for the account flow.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/login")
public class LoginPageController extends AbstractLoginPageController
{

	private static final String FORM_GLOBAL_ERROR = "form.global.error";

	private HttpSessionRequestCache httpSessionRequestCache;

	@Override
	protected String getView()
	{
		return ControllerConstants.Views.Pages.Account.AccountLoginPage;
	}

	@Override
	protected String getSuccessRedirect(final HttpServletRequest request, final HttpServletResponse response)
	{
		if (httpSessionRequestCache.getRequest(request, response) != null)
		{
			return httpSessionRequestCache.getRequest(request, response).getRedirectUrl();
		}
		return "/";
	}

	@Override
	protected AbstractPageModel getCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("login");
	}


	@Resource(name = "httpSessionRequestCache")
	public void setHttpSessionRequestCache(final HttpSessionRequestCache accHttpSessionRequestCache)
	{
		this.httpSessionRequestCache = accHttpSessionRequestCache;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String doLogin(@RequestHeader(value = "referer", required = false) final String referer,
			@RequestParam(value = "error", defaultValue = "false") final boolean loginError, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final HttpSession session)
			throws CMSItemNotFoundException
	{
		if (!loginError)
		{
			storeReferer(referer, request, response);
		}
		return getDefaultLoginPage(loginError, session, model);
	}

	protected void storeReferer(final String referer, final HttpServletRequest request, final HttpServletResponse response)
	{
		if (StringUtils.isNotBlank(referer) && !StringUtils.endsWith(referer, "/login")
				&& StringUtils.contains(referer, request.getServerName()))
		{
			httpSessionRequestCache.saveRequest(request, response);
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(@RequestHeader(value = "referer", required = false) final String referer, final RegisterForm form,
			final BindingResult bindingResult, final Model model, final HttpServletRequest request,
			final HttpServletResponse response, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		getRegistrationValidator().validate(form, bindingResult);
		return processRegisterUserRequest(referer, form, bindingResult, model, request, response, redirectModel);
	}

	/*
	 * @Override protected String processRegisterUserRequest(final String referer, final RegisterForm form, final
	 * BindingResult bindingResult, final Model model, final HttpServletRequest request, final HttpServletResponse
	 * response, final RedirectAttributes redirectModel) throws CMSItemNotFoundException { if (bindingResult.hasErrors())
	 * { model.addAttribute(form); model.addAttribute(new LoginForm()); model.addAttribute(new GuestForm());
	 * GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR); return handleRegistrationError(model); }
	 * 
	 * final RegisterData data = new RegisterData(); data.setFirstName(form.getFirstName());
	 * data.setLastName(form.getLastName()); data.setLogin(form.getEmail()); data.setPassword(form.getPwd());
	 * data.setTitleCode(form.getTitleCode()); try { getCustomerFacade().register(data);
	 * getAutoLoginStrategy().login(form.getEmail().toLowerCase(), form.getPwd(), request, response);
	 * 
	 * GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
	 * "registration.confirmation.message.title"); } catch (final DuplicateUidException e) { //LOGGER.warn(
	 * "registration failed: " + e); model.addAttribute(form); model.addAttribute(new LoginForm());
	 * model.addAttribute(new GuestForm()); bindingResult.rejectValue("email",
	 * "registration.error.account.exists.title"); GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR); return
	 * handleRegistrationError(model); } return REDIRECT_PREFIX + getSuccessRedirect(request, response); }
	 */}
