package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ConfigRegion;

public interface ConfigRegionService {

	public long save(ConfigRegion configRegion) throws Exception;

	public List<ConfigRegion> findAll(String corpId);

	public ConfigRegion findByCode(String code,String corpId);
	public List<ConfigRegion> findByCountryCode(String countryCode,String corpId);
	public void update(ConfigRegion configRegion);

}
