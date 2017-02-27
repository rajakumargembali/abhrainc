/**
 *
 */
package com.abhrainc.storefront.controllers.pages.checkout.steps;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.acceleratorfacades.payment.data.PaymentSubscriptionResultData;
import de.hybris.platform.acceleratorfacades.payment.impl.DefaultPaymentFacade;
import de.hybris.platform.acceleratorservices.model.payment.CCPaySubValidationModel;
import de.hybris.platform.acceleratorservices.payment.dao.CreditCardPaymentSubscriptionDao;
import de.hybris.platform.acceleratorservices.payment.data.CreateSubscriptionResult;
import de.hybris.platform.acceleratorservices.payment.data.PaymentErrorField;
import de.hybris.platform.acceleratorservices.payment.data.PaymentSubscriptionResultItem;
import de.hybris.platform.acceleratorservices.payment.enums.DecisionsEnum;
import de.hybris.platform.acceleratorservices.payment.strategies.ClientReferenceLookupStrategy;
import de.hybris.platform.acceleratorservices.payment.strategies.CreateSubscriptionResultValidationStrategy;
import de.hybris.platform.acceleratorservices.payment.strategies.CreditCardPaymentInfoCreateStrategy;
import de.hybris.platform.acceleratorservices.payment.strategies.PaymentResponseInterpretationStrategy;
import de.hybris.platform.acceleratorservices.payment.strategies.PaymentTransactionStrategy;
import de.hybris.platform.acceleratorservices.payment.strategies.SignatureValidationStrategy;
import de.hybris.platform.commerceservices.strategies.CheckoutCustomerStrategy;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;


/**
 * @author sujan
 *
 */
public class PaymentSeperator extends DefaultPaymentFacade
{
	private static final Logger LOG = Logger.getLogger(PaymentSeperator.class);

	private static final String SUBSCRIPTION_SIGNATURE_DATA_CANNOT_BE_NULL_MSG = "SubscriptionSignatureData cannot be null";
	private static final String SUBSCRIPTION_INFO_DATA_CANNOT_BE_NULL_MSG = "SubscriptionInfoData cannot be null";
	private static final String SIGNATURE_DATA_CANNOT_BE_NULL_MSG = "SignatureData cannot be null";
	private static final String PAYMENT_INFO_DATA_CANNOT_BE_NULL_MSG = "PaymentInfoData cannot be null";
	private static final String ORDER_INFO_DATA_CANNOT_BE_NULL_MSG = "OrderInfoData cannot be null";
	private static final String CUSTOMER_INFO_DATA_CANNOT_BE_NULL_MSG = "CustomerInfoData cannot be null";
	private static final String AUTH_REPLY_DATA_CANNOT_BE_NULL_MSG = "AuthReplyData cannot be null";
	private static final String DECISION_CANNOT_BE_NULL_MSG = "Decision cannot be null";
	private static final String CREATE_SUBSCRIPTION_RESULT_CANNOT_BE_NULL_MSG = "CreateSubscriptionResult cannot be null";


	private PaymentResponseInterpretationStrategy paymentResponseInterpretation;
	private ClientReferenceLookupStrategy clientReferenceLookupStrategy;
	private CreateSubscriptionResultValidationStrategy createSubscriptionResultValidationStrategy;
	private SignatureValidationStrategy signatureValidationStrategy;
	private CreditCardPaymentInfoCreateStrategy creditCardPaymentInfoCreateStrategy;
	private PaymentTransactionStrategy paymentTransactionStrategy;
	private ModelService modelService;
	private CreditCardPaymentSubscriptionDao creditCardPaymentSubscriptionDao;

	@Override
	public PaymentSubscriptionResultData completeSopCreateSubscription(final Map<String, String> parameters,
			final boolean saveInAccount)
	{
		final CustomerModel customerModel = getCurrentUserForCheckout();
		final PaymentSubscriptionResultItem paymentSubscriptionResultItem = completeSopCreatePaymentSubscription(customerModel,
				saveInAccount, parameters);

		if (paymentSubscriptionResultItem != null)
		{
			return getPaymentSubscriptionResultDataConverter().convert(paymentSubscriptionResultItem);
		}

		return null;
	}

	public PaymentSubscriptionResultItem completeSopCreatePaymentSubscription(final CustomerModel customerModel,
			final boolean saveInAccount, final Map<String, String> parameters)
	{
		final PaymentSubscriptionResultItem paymentSubscriptionResult = new PaymentSubscriptionResultItem();
		final Map<String, PaymentErrorField> errors = new HashMap<String, PaymentErrorField>();
		paymentSubscriptionResult.setErrors(errors);

		final CreateSubscriptionResult response = getPaymentResponseInterpretation().interpretResponse(parameters,
				getClientReferenceLookupStrategy().lookupClientReferenceId(), errors);

		validateParameterNotNull(response, CREATE_SUBSCRIPTION_RESULT_CANNOT_BE_NULL_MSG);
		validateParameterNotNull(response.getDecision(), DECISION_CANNOT_BE_NULL_MSG);

		if (!getCreateSubscriptionResultValidationStrategy().validateCreateSubscriptionResult(errors, response).isEmpty())
		{
			return paymentSubscriptionResult;
		}

		paymentSubscriptionResult.setSuccess(DecisionsEnum.ACCEPT.name().equalsIgnoreCase(response.getDecision()));
		paymentSubscriptionResult.setDecision(String.valueOf(response.getDecision()));
		paymentSubscriptionResult.setResultCode(String.valueOf(response.getReasonCode()));

		if (DecisionsEnum.ACCEPT.name().equalsIgnoreCase(response.getDecision()))
		{
			// in case of ACCEPT we should have all these fields filled out
			Assert.notNull(response.getAuthReplyData(), AUTH_REPLY_DATA_CANNOT_BE_NULL_MSG);
			Assert.notNull(response.getCustomerInfoData(), CUSTOMER_INFO_DATA_CANNOT_BE_NULL_MSG);
			Assert.notNull(response.getOrderInfoData(), ORDER_INFO_DATA_CANNOT_BE_NULL_MSG);
			Assert.notNull(response.getPaymentInfoData(), PAYMENT_INFO_DATA_CANNOT_BE_NULL_MSG);
			Assert.notNull(response.getSignatureData(), SIGNATURE_DATA_CANNOT_BE_NULL_MSG);
			Assert.notNull(response.getSubscriptionInfoData(), SUBSCRIPTION_INFO_DATA_CANNOT_BE_NULL_MSG);
			Assert.notNull(response.getSubscriptionSignatureData(), SUBSCRIPTION_SIGNATURE_DATA_CANNOT_BE_NULL_MSG);

			// Validate signature
			if (getSignatureValidationStrategy().validateSignature(response.getSubscriptionInfoData()))
			{
				getPaymentTransactionStrategy().savePaymentTransactionEntry(customerModel, response.getRequestId(),
						response.getOrderInfoData());
				final CreditCardPaymentInfoModel cardPaymentInfoModel = getCreditCardPaymentInfoCreateStrategy().saveSubscription(
						customerModel, response.getCustomerInfoData(), response.getSubscriptionInfoData(),
						response.getPaymentInfoData(), saveInAccount);
				paymentSubscriptionResult.setStoredCard(cardPaymentInfoModel);

				// Check if the subscription has already been validated
				final CCPaySubValidationModel subscriptionValidation = getCreditCardPaymentSubscriptionDao()
						.findSubscriptionValidationBySubscription(cardPaymentInfoModel.getSubscriptionId());
				if (subscriptionValidation != null)
				{
					cardPaymentInfoModel.setSubscriptionValidated(true);
					getModelService().save(cardPaymentInfoModel);
					getModelService().remove(subscriptionValidation);
				}
			}
			else
			{
				LOG.error("Cannot create subscription. Subscription signature does not match.");
			}
		}
		else
		{
			LOG.error(
					"Cannot create subscription. Decision: " + response.getDecision() + " - Reason Code: " + response.getReasonCode());
		}
		return paymentSubscriptionResult;
	}


	/**
	 * @return the createSubscriptionResultValidationStrategy
	 */
	public CreateSubscriptionResultValidationStrategy getCreateSubscriptionResultValidationStrategy()
	{
		return createSubscriptionResultValidationStrategy;
	}

	/**
	 * @param createSubscriptionResultValidationStrategy
	 *           the createSubscriptionResultValidationStrategy to set
	 */
	public void setCreateSubscriptionResultValidationStrategy(
			final CreateSubscriptionResultValidationStrategy createSubscriptionResultValidationStrategy)
	{
		this.createSubscriptionResultValidationStrategy = createSubscriptionResultValidationStrategy;
	}

	/**
	 * @return the signatureValidationStrategy
	 */
	public SignatureValidationStrategy getSignatureValidationStrategy()
	{
		return signatureValidationStrategy;
	}

	/**
	 * @param signatureValidationStrategy
	 *           the signatureValidationStrategy to set
	 */
	public void setSignatureValidationStrategy(final SignatureValidationStrategy signatureValidationStrategy)
	{
		this.signatureValidationStrategy = signatureValidationStrategy;
	}

	/**
	 * @return the creditCardPaymentInfoCreateStrategy
	 */
	public CreditCardPaymentInfoCreateStrategy getCreditCardPaymentInfoCreateStrategy()
	{
		return creditCardPaymentInfoCreateStrategy;
	}

	/**
	 * @param creditCardPaymentInfoCreateStrategy
	 *           the creditCardPaymentInfoCreateStrategy to set
	 */
	public void setCreditCardPaymentInfoCreateStrategy(
			final CreditCardPaymentInfoCreateStrategy creditCardPaymentInfoCreateStrategy)
	{
		this.creditCardPaymentInfoCreateStrategy = creditCardPaymentInfoCreateStrategy;
	}

	/**
	 * @return the paymentTransactionStrategy
	 */
	public PaymentTransactionStrategy getPaymentTransactionStrategy()
	{
		return paymentTransactionStrategy;
	}

	/**
	 * @param paymentTransactionStrategy
	 *           the paymentTransactionStrategy to set
	 */
	public void setPaymentTransactionStrategy(final PaymentTransactionStrategy paymentTransactionStrategy)
	{
		this.paymentTransactionStrategy = paymentTransactionStrategy;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the creditCardPaymentSubscriptionDao
	 */
	public CreditCardPaymentSubscriptionDao getCreditCardPaymentSubscriptionDao()
	{
		return creditCardPaymentSubscriptionDao;
	}

	/**
	 * @param creditCardPaymentSubscriptionDao
	 *           the creditCardPaymentSubscriptionDao to set
	 */
	public void setCreditCardPaymentSubscriptionDao(final CreditCardPaymentSubscriptionDao creditCardPaymentSubscriptionDao)
	{
		this.creditCardPaymentSubscriptionDao = creditCardPaymentSubscriptionDao;
	}

	/**
	 * @return the clientReferenceLookupStrategy
	 */
	public ClientReferenceLookupStrategy getClientReferenceLookupStrategy()
	{
		return clientReferenceLookupStrategy;
	}

	/**
	 * @param clientReferenceLookupStrategy
	 *           the clientReferenceLookupStrategy to set
	 */
	public void setClientReferenceLookupStrategy(final ClientReferenceLookupStrategy clientReferenceLookupStrategy)
	{
		this.clientReferenceLookupStrategy = clientReferenceLookupStrategy;
	}

	/**
	 * @return the paymentResponseInterpretation
	 */
	public PaymentResponseInterpretationStrategy getPaymentResponseInterpretation()
	{
		return paymentResponseInterpretation;
	}

	/**
	 * @param paymentResponseInterpretation
	 *           the paymentResponseInterpretation to set
	 */
	public void setPaymentResponseInterpretation(final PaymentResponseInterpretationStrategy paymentResponseInterpretation)
	{
		this.paymentResponseInterpretation = paymentResponseInterpretation;
	}

	@Autowired
	private CheckoutCustomerStrategy checkoutCustomerStrategy;

	@Override
	protected CheckoutCustomerStrategy getCheckoutCustomerStrategy()
	{
		return checkoutCustomerStrategy;
	}

	@Override
	@Required
	public void setCheckoutCustomerStrategy(final CheckoutCustomerStrategy checkoutCustomerStrategy)
	{
		this.checkoutCustomerStrategy = checkoutCustomerStrategy;
	}

	@Override
	protected CustomerModel getCurrentUserForCheckout()
	{
		return getCheckoutCustomerStrategy().getCurrentUserForCheckout();
	}
}
