package org.symanowicz.cache;

import org.mule.AbstractAgent;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.InitialisationException;

public class CacheLoaderAgent extends AbstractAgent {

	public CacheLoaderAgent() {
		super("CacheLoaderAgent");
	}

	private TranslationDataDao dao;

	public void setDao(TranslationDataDao dao) {
		this.dao = dao;
	}

	@Override
	public void initialise() throws InitialisationException {

	}

	@Override
	public void start() throws MuleException {
		dao.preloadCache();
	}

	@Override
	public void stop() throws MuleException {

	}

	@Override
	public void dispose() {

	}
}
