package com.ag.generic.service;

import java.util.List;

import com.ag.generic.entity.ConfigCountry;

public interface ConfigCountryService {

	public long save(ConfigCountry configCountry) throws Exception;

	public List<ConfigCountry> findAll(String corpId);

	public ConfigCountry findByCode(String code,String corpId);

	public void update(ConfigCountry tcn);

}
