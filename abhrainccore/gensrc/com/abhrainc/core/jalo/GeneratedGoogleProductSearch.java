package com.abhrainc.core.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.cronjob.jalo.CronJob;
import de.hybris.platform.jalo.SessionContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Generated class for type {@link com.abhrainc.core.jalo.GoogleProductSearch GoogleProductSearch}.
 */
@SuppressWarnings(
{ "deprecation", "unused", "cast", "PMD" })
public abstract class GeneratedGoogleProductSearch extends CronJob
{
	/** Qualifier of the <code>GoogleProductSearch.catalogVersion</code> attribute **/
	public static final String CATALOGVERSION = "catalogVersion";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>(CronJob.DEFAULT_INITIAL_ATTRIBUTES);
		tmp.put(CATALOGVERSION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}

	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}

	public CatalogVersion getCatalogVersion(final SessionContext ctx)
	{
		return (CatalogVersion) getProperty(ctx, CATALOGVERSION);
	}


	public CatalogVersion getCatalogVersion()
	{
		return getCatalogVersion(getSession().getSessionContext());
	}


	public void setCatalogVersion(final SessionContext ctx, final CatalogVersion value)
	{
		setProperty(ctx, CATALOGVERSION, value);
	}

	public void setCatalogVersion(final CatalogVersion value)
	{
		setCatalogVersion(getSession().getSessionContext(), value);
	}

}
