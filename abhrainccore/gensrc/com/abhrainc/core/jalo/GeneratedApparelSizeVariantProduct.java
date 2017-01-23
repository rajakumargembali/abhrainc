package com.abhrainc.core.jalo;

import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Generated class for type {@link com.abhrainc.core.jalo.ApparelSizeVariantProduct ApparelSizeVariantProduct}.
 */
@SuppressWarnings(
{ "deprecation", "unused", "cast", "PMD" })
public abstract class GeneratedApparelSizeVariantProduct extends ApparelStyleVariantProduct
{
	/** Qualifier of the <code>ApparelSizeVariantProduct.size</code> attribute **/
	public static final String SIZE = "size";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(
				ApparelStyleVariantProduct.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(SIZE, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}

	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}

	public String getSize(final SessionContext ctx)
	{
		if (ctx == null || ctx.getLanguage() == null)
		{
			throw new JaloInvalidParameterException("GeneratedApparelSizeVariantProduct.getSize requires a session language", 0);
		}
		return (String) getLocalizedProperty(ctx, SIZE);
	}


	public String getSize()
	{
		return getSize(getSession().getSessionContext());
	}


	public Map<Language, String> getAllSize(final SessionContext ctx)
	{
		return (Map<Language, String>) getAllLocalizedProperties(ctx, SIZE, C2LManager.getInstance().getAllLanguages());
	}


	public Map<Language, String> getAllSize()
	{
		return getAllSize(getSession().getSessionContext());
	}


	public void setSize(final SessionContext ctx, final String value)
	{
		if (ctx == null || ctx.getLanguage() == null)
		{
			throw new JaloInvalidParameterException("GeneratedApparelSizeVariantProduct.setSize requires a session language", 0);
		}
		setLocalizedProperty(ctx, SIZE, value);
	}

	public void setSize(final String value)
	{
		setSize(getSession().getSessionContext(), value);
	}

	public void setAllSize(final SessionContext ctx, final Map<Language, String> value)
	{
		setAllLocalizedProperties(ctx, SIZE, value);
	}

	public void setAllSize(final Map<Language, String> value)
	{
		setAllSize(getSession().getSessionContext(), value);
	}

}
