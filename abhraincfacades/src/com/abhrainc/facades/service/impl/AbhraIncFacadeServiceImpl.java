/**
 *
 */
package com.abhrainc.facades.service.impl;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;

import org.springframework.beans.factory.annotation.Autowired;

import com.abhrainc.facades.dao.AbhraIncFacadeDAO;
import com.abhrainc.facades.service.AbhraIncFacadeService;


/**
 * @author sujan
 *
 */
public class AbhraIncFacadeServiceImpl implements AbhraIncFacadeService
{

	@Autowired
	AbhraIncFacadeDAO abhraIncFacadeDAO;

	@Autowired
	ModelService modelService;

	@Override
	public String activateEmailAccount(final String email)
	{
		final UserModel userModel = abhraIncFacadeDAO.getUserDetails(email);
		userModel.setIsEmailActivated(java.lang.Boolean.TRUE);
		modelService.save(userModel);
		return "/EmailActiveLogin";

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.service.AbhraIncFacadeService#getCartDetails(java.lang.String)
	 */
	@Override
	public CartModel getCartDetails(final String code)
	{
		// YTODO Auto-generated method stub
		return abhraIncFacadeDAO.getCartDetails(code);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.abhrainc.facades.service.AbhraIncFacadeService#getAbstractorderDetails(de.hybris.platform.commercefacades.
	 * order.data.OrderData)
	 */
	@Override
	public AbstractOrderModel getAbstractorderDetails(final OrderData orderData)
	{
		// YTODO Auto-generated method stub

		return abhraIncFacadeDAO.getAbstractOrderDetails(orderData.getCode());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.abhrainc.facades.service.AbhraIncFacadeService#getorderEntryDetails(de.hybris.platform.core.model.order.
	 * AbstractOrderModel)
	 */
	@Override
	public AbstractOrderEntryModel getorderEntryDetails(final AbstractOrderModel orderModel)
	{
		// YTODO Auto-generated method stub
		return abhraIncFacadeDAO.getOrderEntryModels(orderModel.getPk());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abhrainc.facades.service.AbhraIncFacadeService#getOrderDetails(de.hybris.platform.commercefacades.order.data.
	 * OrderData)
	 */
	@Override
	public OrderModel getOrderDetails(final OrderData orderData)
	{
		// YTODO Auto-generated method stub
		return abhraIncFacadeDAO.getOrderDetailsForOrder(orderData.getCode());
	}

}
