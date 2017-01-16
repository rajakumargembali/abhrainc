package com.abhrainc.facades.order.impl;


import com.abhrainc.facades.dao.AbhraIncFacadeDAO;


public class AbhraincOrderFacade extends DefaultOrderFacade
{

	@Autowired
	AbhraIncFacadeDAO abhraIncDao;

	@Autowired
	ModelService modelService;

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

	/**
	 * @param string
	 * @param time
	 *           public void saveExpectedDeliveryDate(final String string, final Date time) { // YTODO Auto-generated
	 *           method stub final OrderModel model = abhraIncDao.getOrderDetailsForOrder(string);
	 *           model.setOrderExpectedDeliveryDate(time); modelService.save(model);
	 * 
	 * 
	 *           }
	 * 
	 *           /**
	 * @param code
	 * @return
	 */
	public ConsignmentModel getConsignmentDetailForCode(final PK pk)
	{
		// YTODO Auto-generated method stub

		return abhraIncDao.getConsignmentDetails(pk);
	}

	/**
	 * @param code
	 * @return
	 */
	public OrderModel getOrderDetailForCode(final String code)
	{
		// YTODO Auto-generated method stub
		return abhraIncDao.getOrderDetailsForOrder(code);
	}
}