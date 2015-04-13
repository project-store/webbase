package com.ass.base.prop;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class MyPropertyConfigurer extends PropertyPlaceholderConfigurer {

	private Map<String, String> myPropertiesMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory factory, Properties props) throws BeansException {
		super.processProperties(factory, props);
		
		myPropertiesMap = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			myPropertiesMap.put(keyStr, value);
		}
	}

	public String getVal(String name) {
		return myPropertiesMap.get(name);
	}

}