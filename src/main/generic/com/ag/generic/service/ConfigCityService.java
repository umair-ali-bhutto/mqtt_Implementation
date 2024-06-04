package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ConfigCity;

public interface ConfigCityService {

	public long save(ConfigCity configCity) throws Exception;

	public List<ConfigCity> findAll(String corpId);

	public ConfigCity findByCode(String code,String corpId);
	public List<ConfigCity> findByCountryCode(String countryCode,String corpId);
	public List<ConfigCity> findByRegionCode(String regionCode,String corpId);
	public List<ConfigCity> findByCountryCodeAndRegionCode(String countryCode,String regionCode,String corpId);

	public void update(ConfigCity configCity);

}
