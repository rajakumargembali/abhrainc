/**
 *
 */
/*
 * package com.abhrainc.core.service.impl;
 * 
 * import de.hybris.platform.acceleratorservices.email.CMSEmailPageService; import
 * de.hybris.platform.acceleratorservices.email.EmailGenerationService; import
 * de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel; import
 * de.hybris.platform.acceleratorservices.model.email.EmailMessageModel; import
 * de.hybris.platform.acceleratorservices.process.strategies.ProcessContextResolutionStrategy; import
 * de.hybris.platform.catalog.model.CatalogVersionModel; import
 * de.hybris.platform.core.model.order.AbstractOrderEntryModel; import
 * de.hybris.platform.core.model.product.ProductModel; import
 * de.hybris.platform.orderprocessing.model.OrderProcessModel; import
 * de.hybris.platform.processengine.action.AbstractSimpleDecisionAction; import
 * de.hybris.platform.processengine.model.BusinessProcessModel; import de.hybris.platform.task.RetryLaterException;
 * import de.hybris.platform.variants.model.VariantProductModel;
 * 
 * import java.util.ArrayList; import java.util.List; import java.util.Set;
 * 
 * import org.apache.log4j.Logger; import org.fest.util.Collections; import
 * org.springframework.beans.factory.annotation.Required;
 * 
 * 
 *//**
   * @author sujan
   *
   *//*
	  * public class AbhraIncGenerateEmailAction extends AbstractSimpleDecisionAction { private static final Logger LOG =
	  * Logger.getLogger(AbhraIncGenerateEmailAction.class);
	  * 
	  * private CMSEmailPageService cmsEmailPageService; private String frontendTemplateName; private
	  * ProcessContextResolutionStrategy contextResolutionStrategy; private EmailGenerationService
	  * emailGenerationService;
	  * 
	  * protected CMSEmailPageService getCmsEmailPageService() { return cmsEmailPageService; }
	  * 
	  * @Required public void setCmsEmailPageService(final CMSEmailPageService cmsEmailPageService) {
	  * this.cmsEmailPageService = cmsEmailPageService; }
	  * 
	  * protected String getFrontendTemplateName() { return frontendTemplateName; }
	  * 
	  * @Required public void setFrontendTemplateName(final String frontendTemplateName) { this.frontendTemplateName =
	  * frontendTemplateName; }
	  * 
	  * protected ProcessContextResolutionStrategy getContextResolutionStrategy() { return contextResolutionStrategy; }
	  * 
	  * @Required public void setContextResolutionStrategy(final ProcessContextResolutionStrategy
	  * contextResolutionStrategy) { this.contextResolutionStrategy = contextResolutionStrategy; }
	  * 
	  * 
	  * protected EmailGenerationService getEmailGenerationService() { return emailGenerationService; }
	  * 
	  * @Required public void setEmailGenerationService(final EmailGenerationService emailGenerationService) {
	  * this.emailGenerationService = emailGenerationService; }
	  * 
	  * @Override public Transition executeAction(final BusinessProcessModel businessProcessModel) throws
	  * RetryLaterException { final CatalogVersionModel contentCatalogVersion = getContextResolutionStrategy()
	  * .getContentCatalogVersion(businessProcessModel); if (contentCatalogVersion != null) { final EmailPageModel
	  * emailPageModel = getCmsEmailPageService().getEmailPageForFrontendTemplate(getFrontendTemplateName(),
	  * contentCatalogVersion);
	  * 
	  * if (emailPageModel != null) { final EmailMessageModel emailMessageModel =
	  * getEmailGenerationService().generate(businessProcessModel, emailPageModel); if (emailMessageModel != null) {
	  * final List<EmailMessageModel> emails = new ArrayList<EmailMessageModel>();
	  * emails.addAll(businessProcessModel.getEmails()); emails.add(emailMessageModel);
	  * businessProcessModel.setEmails(emails);
	  * 
	  * getModelService().save(businessProcessModel);
	  * 
	  * LOG.info("Email message generated"); if (businessProcessModel instanceof OrderProcessModel) { //Boolean
	  * jerseyEnabled = Boolean.FALSE; final OrderProcessModel orderProcessModel = (OrderProcessModel)
	  * businessProcessModel; for (final AbstractOrderEntryModel entry : orderProcessModel.getOrder().getEntries()) {
	  * //jerseyEnabled = Boolean.valueOf(isJerseyEnabled(entry.getProduct(), new HashSet<ProductModel>())); if
	  * (entry.getProduct().getIsServiceProduct().booleanValue()) { LOG.info(
	  * "calling the next action generateCCOrderConfirmationEmail"); return Transition.OK; } } } else { return
	  * Transition.NOK; } } else { LOG.warn("Failed to generate email message"); } } else { LOG.warn(
	  * "Could not retrieve email page model for " + getFrontendTemplateName() + " and " +
	  * contentCatalogVersion.getCatalog().getName() + ":" + contentCatalogVersion.getVersion() +
	  * ", cannot generate email content"); } } else { LOG.warn(
	  * "Could not resolve the content catalog version, cannot generate email content"); }
	  * 
	  * return Transition.NOK; }
	  * 
	  * private boolean isJerseyEnabled(final ProductModel product, final Set<ProductModel> visited) { if (product ==
	  * null) { return false; }
	  * 
	  * if (product.getJerseyEnabled() != null && product.getJerseyEnabled().booleanValue()) { return true; } else if
	  * (!visited.contains(product)) { visited.add(product); if (product instanceof VariantProductModel) { if
	  * (isJerseyEnabled(((VariantProductModel) product).getBaseProduct(), visited)) { return true; } } else if
	  * (!Collections.isEmpty(product.getVariants())) { for (final VariantProductModel variant : product.getVariants()) {
	  * if (isJerseyEnabled(variant, visited)) { return true; } } } } return false; }
	  * 
	  * }
	  */