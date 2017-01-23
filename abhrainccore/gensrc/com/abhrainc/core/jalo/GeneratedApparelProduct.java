package com.abhrainc.core.jalo;

import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Generated class for type {@link com.abhrainc.core.jalo.ApparelProduct ApparelProduct}.
 */
@SuppressWarnings(
{ "deprecation", "unused", "cast", "PMD" })
public abstract class GeneratedApparelProduct extends Product
{
	/** Qualifier of the <code>ApparelProduct.genders</code> attribute **/
	public static final String GENDERS = "genders";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(Product.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(GENDERS, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}

	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}

	public List<EnumerationValue> getGenders(final SessionContext ctx)
	{
		final List<EnumerationValue> coll = (List<EnumerationValue>) getProperty(ctx, GENDERS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}

	public List<EnumerationValue> getGenders()
	{
		return getGenders(getSession().getSessionContext());
	}

	public void setGenders(final SessionContext ctx, final List<EnumerationValue> value)
	{
		setProperty(ctx, GENDERS, value == null || !value.isEmpty() ? value : null);
	}

	public void setGenders(final List<EnumerationValue> value)
	{
		setGenders(getSession().getSessionContext(), value);
	}

}
