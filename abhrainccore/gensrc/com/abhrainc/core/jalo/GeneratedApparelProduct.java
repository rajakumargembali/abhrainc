/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Jan 4, 2017 3:08:38 PM                      ---
 * --- Generated at 3 Jan, 2017 4:37:52 PM                      ---
 * --- Generated at 6 Jan, 2017 5:29:56 PM                      ---
 * --- Generated at 16 Jan, 2017 5:45:40 PM                     ---
 * --- Generated at Jan 16, 2017 5:32:46 PM                     ---
 * --- Generated at 15 Jan, 2017 7:00:48 PM                     ---
 * ----------------------------------------------------------------
 */
package com.abhrainc.core.jalo;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.omg.CORBA.AttributeMode;


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

	/**
	 * <i>Generated method</i> - Getter of the <code>ApparelProduct.genders</code> attribute.
	 * 
	 * @return the genders - List of genders that the ApparelProduct is designed for
	 */
	public List<EnumerationValue> getGenders(final SessionContext ctx)
	{
		final List<EnumerationValue> coll = (List<EnumerationValue>) getProperty(ctx, GENDERS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}

	/**
	 * <i>Generated method</i> - Getter of the <code>ApparelProduct.genders</code> attribute.
	 * 
	 * @return the genders - List of genders that the ApparelProduct is designed for
	 */
	public List<EnumerationValue> getGenders()
	{
		return getGenders(getSession().getSessionContext());
	}

	/**
	 * <i>Generated method</i> - Setter of the <code>ApparelProduct.genders</code> attribute.
	 * 
	 * @param value
	 *           the genders - List of genders that the ApparelProduct is designed for
	 */
	public void setGenders(final SessionContext ctx, final List<EnumerationValue> value)
	{
		setProperty(ctx, GENDERS, value == null || !value.isEmpty() ? value : null);
	}

	/**
	 * <i>Generated method</i> - Setter of the <code>ApparelProduct.genders</code> attribute.
	 * 
	 * @param value
	 *           the genders - List of genders that the ApparelProduct is designed for
	 */
	public void setGenders(final List<EnumerationValue> value)
	{
		setGenders(getSession().getSessionContext(), value);
	}

}
