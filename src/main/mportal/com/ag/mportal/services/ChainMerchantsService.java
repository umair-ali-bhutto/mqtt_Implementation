package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.ChainMerchant;

public interface ChainMerchantsService{
	
	public long insert(ChainMerchant tcn);
	public ChainMerchant fetchByID(ChainMerchant tcn);
	public ChainMerchant fetchByOtherParams(ChainMerchant tcn);
	public List<ChainMerchant> fetchAll();
	public List<ChainMerchant> fetchAllByID(String id);
	public void update(ChainMerchant id);
	public ChainMerchant searchByTypeAndMID(String Type,String mid);
	public List<String> fetchAllNonRegistered();

}