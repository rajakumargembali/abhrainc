/**
 *
 */
package com.abhrainc.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


/**
 * @author sujan
 *
 */
@Controller
public class SendingEmails
{

	@Autowired
	private EmailService emailService;


	void sendEmailforCustomer(final String name, final String email, final String content, final String subject)
	{

		final EmailAddressModel toAddress, fromAddress;
		final String toEmail = email;
		final String fromEmail = "naresh.athukuri@abhrainc.com";
		final String replyEmail = "naresh.athukuri@abhrainc.com";
		toAddress = emailService.getOrCreateEmailAddressForEmail(toEmail, toEmail);
		fromAddress = emailService.getOrCreateEmailAddressForEmail(fromEmail, fromEmail);
		final List<EmailAddressModel> toAddresses = new ArrayList<EmailAddressModel>();
		toAddresses.add(toAddress);
		final StringBuilder messageBody = new StringBuilder();
		messageBody.append(content);

		final EmailMessageModel message = emailService.createEmailMessage(toAddresses, null, null, fromAddress, replyEmail, subject,
				messageBody.toString(), null);
		emailService.send(message);




	}
}
