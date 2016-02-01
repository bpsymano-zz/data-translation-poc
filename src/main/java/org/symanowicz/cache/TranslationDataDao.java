package org.symanowicz.cache;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;


public class TranslationDataDao extends PropertiesLoaderSupport implements MuleContextAware {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private MuleContext muleContext;
	private HazelcastInstance hz;
	private String mapPropertyName;

	public void setMapPropertyName(String mapPropertyName) {
		this.mapPropertyName = mapPropertyName;
	}

	@Override
	public void setMuleContext(MuleContext muleContext) {
		this.muleContext = muleContext;
	}

	public void preloadCache()  {
		Properties props;
		try {
			props = mergeProperties();
			String propertyList = props.getProperty(mapPropertyName + ".list");
			if (StringUtils.isEmpty(propertyList)) {
				log.error("Failed to read map '{}', missing list property.", mapPropertyName);
			} else {
				hz = (HazelcastInstance) muleContext.getRegistry().lookupObject("SOACache");
				IMap<String, String> data = hz.getMap("translationData");
				data.lock("translationData");
				log.debug("lock acquired");
				data.clear();
				createCacheMap(props, data, propertyList);			
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void createCacheMap(final Properties props, final IMap<String, String> map, final String propertyList) {
		String[] data = StringUtils.split(propertyList, ",");
		for (String datum : data) {
			String[] propertyData = StringUtils.split(datum, "|");
			String key = propertyData[0];
			String value = propertyData[1];
			if (value == null) {
				log.error("Failed to read map '{}': Missing value for key '{}'", mapPropertyName, key);
			} else {
				map.put(key, value);
			}
		}
	}

}
