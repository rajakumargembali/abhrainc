/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @author Naresh Athukuri
 *
 */


@Controller
@Scope("tenant")
@RequestMapping(value = "/contactus")
public class Contactuscontroller extends AbstractPageController
{
	@Autowired
	private EmailService emailService;
	@Autowired
	ModelService modelService;

	@ModelAttribute("titles")
	public Collection<TitleData> getTitles()
	{
		return userFacade.getTitles();
	}

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@RequestMapping(method = RequestMethod.GET)
	public String viewContactUS(final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		//		return "/pages/contactus/contactus";
		model.addAttribute(new RegisterForm());
		storeCmsPageInModel(model, getContentPageForLabelOrId("contactus1"));
		//return new ModelAndView("/pages/contactus/contactus", "command", new RegisterForm());
		return getViewForPage(model);

	}

	@RequestMapping(method = RequestMethod.POST)
	public String doCheckoutRegister(final RegisterForm form, final BindingResult bindingResult, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{

		final CustomerModel customer = new CustomerModel();
		customer.setName(form.getFirstName());


		sendEmailforCustomer(form.getFirstName(), form.getEmail());



		final Random rand = new Random();

		final long n = rand.nextLong() + 1;
		customer.setUid("" + n);
		modelService.save(customer);

		model.addAttribute("form", form);
		return "/pages/contactus/contactusview";
	}

	private void sendEmailforCustomer(final String name, final String email)
	{

		final EmailAddressModel toAddress, fromAddress;
		final String toEmail = email;
		final String fromEmail = "naresh.athukuri@abhrainc.com";
		final String replyEmail = "naresh.athukuri@abhrainc.com";
		final String subject = "Thanks for contacting us";
		toAddress = emailService.getOrCreateEmailAddressForEmail(toEmail, toEmail);
		fromAddress = emailService.getOrCreateEmailAddressForEmail(fromEmail, fromEmail);
		final List<EmailAddressModel> toAddresses = new ArrayList<EmailAddressModel>();
		toAddresses.add(toAddress);
		final StringBuilder messageBody = new StringBuilder();
		messageBody.append("<html><body>");
		messageBody.append("<p>Hi " + name + "Thanks for contacting us</p>");
		messageBody.append("</body></html>");

		final EmailMessageModel message = emailService.createEmailMessage(toAddresses, null, null, fromAddress, replyEmail, subject,
				messageBody.toString(), null);
		emailService.send(message);




	}

}
