package com.abhrainc.core.jalo;

import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.variants.jalo.VariantProduct;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Generated class for type {@link com.abhrainc.core.jalo.ElectronicsColorVariantProduct ElectronicsColorVariantProduct}
 * .
 */
@SuppressWarnings(
{ "deprecation", "unused", "cast", "PMD" })
public abstract class GeneratedElectronicsColorVariantProduct extends VariantProduct
{
	/** Qualifier of the <code>ElectronicsColorVariantProduct.color</code> attribute **/
	public static final String COLOR = "color";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(VariantProduct.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(COLOR, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}

	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}

	public String getColor(final SessionContext ctx)
	{
		if (ctx == null || ctx.getLanguage() == null)
		{
			throw new JaloInvalidParameterException("GeneratedElectronicsColorVariantProduct.getColor requires a session language",
					0);
		}
		return (String) getLocalizedProperty(ctx, COLOR);
	}

	public String getColor()
	{
		return getColor(getSession().getSessionContext());
	}

	public Map<Language, String> getAllColor(final SessionContext ctx)
	{
		return (Map<Language, String>) getAllLocalizedProperties(ctx, COLOR, C2LManager.getInstance().getAllLanguages());
	}

	public Map<Language, String> getAllColor()
	{
		return getAllColor(getSession().getSessionContext());
	}

	public void setColor(final SessionContext ctx, final String value)
	{
		if (ctx == null || ctx.getLanguage() == null)
		{
			throw new JaloInvalidParameterException("GeneratedElectronicsColorVariantProduct.setColor requires a session language",
					0);
		}
		setLocalizedProperty(ctx, COLOR, value);
	}

	public void setColor(final String value)
	{
		setColor(getSession().getSessionContext(), value);
	}

	public void setAllColor(final SessionContext ctx, final Map<Language, String> value)
	{
		setAllLocalizedProperties(ctx, COLOR, value);
	}

	public void setAllColor(final Map<Language, String> value)
	{
		setAllColor(getSession().getSessionContext(), value);
	}

}
