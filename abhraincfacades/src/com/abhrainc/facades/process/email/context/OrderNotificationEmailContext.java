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
package com.abhrainc.facades.process.email.context;

import com.abhrainc.facades.dao.AbhraIncFacadeDAO;


/**
 * Velocity context for a order notification email.
 */
public class OrderNotificationEmailContext extends AbstractEmailContext<OrderProcessModel>
{
	private Converter<OrderModel, OrderData> orderConverter;
	private OrderData orderData;

	@Autowired
	AbhraIncFacadeDAO abhraIncDao;

	@Override
	public void init(final OrderProcessModel orderProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(orderProcessModel, emailPageModel);
		orderData = getOrderConverter().convert(orderProcessModel.getOrder());
		if (orderProcessModel.getOrder().getOrderExpectedDeliveryDate() != null)
		{
			put("Expected_Delivery_Date", orderProcessModel.getOrder().getOrderExpectedDeliveryDate().toString());
		}
		else
		{
			final OrderModel model = abhraIncDao.getOrderDetailsForOrder(orderData.getCode());
			final OrderModel model = abhraIncDao.getOrderDetails(orderData.getCode());
			if (model.getOrderExpectedDeliveryDate() != null)
			{
				put("Expected_Delivery_Date", model.getOrderExpectedDeliveryDate());
			}
			else
			{
				put("Expected_Delivery_Date", "within a week");
			}
		}
	}


	@Override
	protected BaseSiteModel getSite(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getSite();
	}

	@Override
	protected CustomerModel getCustomer(final OrderProcessModel orderProcessModel)
	{
		return (CustomerModel) orderProcessModel.getOrder().getUser();
	}

	protected Converter<OrderModel, OrderData> getOrderConverter()
	{
		return orderConverter;
	}

	@Required
	public void setOrderConverter(final Converter<OrderModel, OrderData> orderConverter)
	{
		this.orderConverter = orderConverter;
	}

	public OrderData getOrder()
	{
		return orderData;
	}

	@Override
	protected LanguageModel getEmailLanguage(final OrderProcessModel orderProcessModel)
	{
		return orderProcessModel.getOrder().getLanguage();
	}

}
