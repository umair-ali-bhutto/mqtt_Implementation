package com.ag.fuel.services;

import java.util.List;

import com.ag.fuel.entity.FuelProductConfig;

public interface FuelProductConfigService {

	public List<FuelProductConfig> fetchAll();

	public List<FuelProductConfig> fetchAllByCorpId(String corpId);
}
