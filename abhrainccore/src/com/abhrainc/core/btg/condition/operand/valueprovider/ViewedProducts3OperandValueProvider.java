
package com.abhrainc.core.btg.condition.operand.valueprovider;

import de.hybris.platform.btg.condition.operand.types.ProductSet;
import de.hybris.platform.btg.condition.operand.valueproviders.AbstractWCMSOperandValueProvider;
import de.hybris.platform.btg.condition.operand.valueproviders.CollectionOperandValueProvider;
import de.hybris.platform.btg.enums.BTGConditionEvaluationScope;
import de.hybris.platform.btg.rule.RuleDataContainer;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;

import com.abhrainc.core.model.BTGViewed3ProductsOperandModel;


public class ViewedProducts3OperandValueProvider extends AbstractWCMSOperandValueProvider<String>
		implements CollectionOperandValueProvider<BTGViewed3ProductsOperandModel>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.btg.condition.operand.OperandValueProvider#getValue(de.hybris.platform.btg.model.
	 * BTGOperandModel, de.hybris.platform.core.model.user.UserModel,
	 * de.hybris.platform.btg.enums.BTGConditionEvaluationScope)
	 */
	@Override
	public Object getValue(final BTGViewed3ProductsOperandModel arg0, final UserModel arg1, final BTGConditionEvaluationScope arg2)
	{
		final RuleDataContainer rdc = this.getRuleDataContainer();
		return new ProductSet(this.getItems(ProductModel.class, "Product", rdc.getAll()));
	}

	@Override
	public Class getValueType(final BTGViewed3ProductsOperandModel arg0)
	{
		return ProductSet.class;
	}

	@Override
	public Class getAtomicValueType(final BTGViewed3ProductsOperandModel arg0)
	{
		return ProductModel.class;
	}

}