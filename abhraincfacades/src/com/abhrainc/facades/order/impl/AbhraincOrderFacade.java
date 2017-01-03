package com.abhrainc.facades.order.impl;


import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.impl.DefaultOrderFacade;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.store.BaseStoreModel;


public class AbhraincOrderFacade extends DefaultOrderFacade
{

	private static final String ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE = "Order with guid %s not found for current user in current BaseStore";

	@Override
	public OrderData getOrderDetailsForCode(final String code)
	{
		final BaseStoreModel baseStoreModel = getBaseStoreService().getCurrentBaseStore();

		OrderModel orderModel = null;
		if (getCheckoutCustomerStrategy().isAnonymousCheckout())
		{
			orderModel = getCustomerAccountService().getOrderDetailsForGUID(code, baseStoreModel);
		}
		else
		{
			try
			{
				orderModel = getCustomerAccountService().getOrderForCode((CustomerModel) getUserService().getCurrentUser(), code,
						baseStoreModel);
			}
			catch (final ModelNotFoundException e)
			{
				throw new UnknownIdentifierException(String.format(ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE, code));
			}
		}

		if (orderModel == null)
		{
			throw new UnknownIdentifierException(String.format(ORDER_NOT_FOUND_FOR_USER_AND_BASE_STORE, code));
		}
		return getOrderConverter().convert(orderModel);

	}
}