package org.symanowicz.data.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class CountryMapper extends DataValueMapper {

	private static final long serialVersionUID = 1L;

	public String getValue(String key) {
		if (!StringUtils.isEmpty(key)) {
			Map<String, String> data = hz.getMap("translationData");
			return data.get(key) != null ? data.get(key) : "";
		}
		return "";
	}

}
