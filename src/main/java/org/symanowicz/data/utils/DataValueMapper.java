package org.symanowicz.data.utils;

import java.io.Serializable;

import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;

import com.hazelcast.core.HazelcastInstance;

public abstract class DataValueMapper implements MuleContextAware, Serializable {
	private static final long serialVersionUID = 1L;
	private MuleContext muleContext;
	protected HazelcastInstance hz;

	@Override
	public void setMuleContext(MuleContext muleContext) {
		this.muleContext = muleContext;
		hz = (HazelcastInstance) this.muleContext.getRegistry().lookupObject("SOACache");
	}

	public DataValueMapper() {
		super();
	}

	// implementation may vary depending on need
	abstract String getValue(String key);

}