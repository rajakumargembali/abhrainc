/**
 *
 */
/*
 * package com.abhrainc.core.email;
 * 
 * import de.hybris.platform.acceleratorservices.email.impl.DefaultEmailGenerationService; import
 * de.hybris.platform.acceleratorservices.model.email.EmailAddressModel; import
 * de.hybris.platform.acceleratorservices.model.email.EmailMessageModel; import
 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext; import
 * de.hybris.platform.processengine.model.BusinessProcessModel; import de.hybris.platform.util.Config;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.apache.log4j.Logger;
 * 
 * 
 *//**
   * @author sujan
   *
   *//*
	  * public class AbhraIncCCEmailGenerationService extends DefaultEmailGenerationService { private static final Logger
	  * LOG = Logger.getLogger(AbhraIncCCEmailGenerationService.class); private final static String MAIL_ABHRAINC_CC =
	  * "mail.abhrainc.cc"; private final static String MAIL_ABHRAINC_CC_DISPLAYNAME = "mail.abhrainc.cc.displayname";
	  * final List<EmailAddressModel> emailAddressModelList = new ArrayList<EmailAddressModel>();
	  * 
	  * @Override protected EmailMessageModel createEmailMessage(final String emailSubject, final String emailBody, final
	  * AbstractEmailContext<BusinessProcessModel> emailContext) { LOG.info(
	  * "Start of createEmailMessage in AbhraincCCEmailGenerationService"); final List<EmailAddressModel> toEmails = new
	  * ArrayList<EmailAddressModel>(); final EmailAddressModel toAddress =
	  * getEmailService().getOrCreateEmailAddressForEmail(Config.getParameter(MAIL_ABHRAINC_CC),
	  * Config.getParameter(MAIL_ABHRAINC_CC_DISPLAYNAME)); toEmails.add(toAddress); final EmailAddressModel fromAddress
	  * = getEmailService().getOrCreateEmailAddressForEmail(emailContext.getFromEmail(),
	  * emailContext.getFromDisplayName()); LOG.info("End of createEmailMessage in AbhraincCCEmailGenerationService");
	  * return getEmailService().createEmailMessage(toEmails, new ArrayList<EmailAddressModel>(), emailAddressModelList,
	  * fromAddress, emailContext.getFromEmail(), emailSubject, emailBody, null); } }
	  */