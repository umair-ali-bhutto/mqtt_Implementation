package com.ag.generic.service;


import java.util.HashMap;
import java.util.List;

import com.ag.generic.entity.ConfigProperty;


public interface ConfigPropertiesService {
	public List<ConfigProperty> SelectAllConfig();
	public HashMap<String, String> SelectAllConfigMap();
	public List<ConfigProperty> SelectConfigByGroup(String group);
	

}
