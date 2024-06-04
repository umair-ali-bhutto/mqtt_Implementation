package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.DiscountBinProdConfig;

public interface DiscountBinProdConfigService {

	public void insert(DiscountBinProdConfig tcn);

	public void update(DiscountBinProdConfig tcn);

	public List<DiscountBinProdConfig> fetchByCorpId(String corpId);

	public List<DiscountBinProdConfig> fetchByBin(String fromBin, String toBin, String corpId);
}