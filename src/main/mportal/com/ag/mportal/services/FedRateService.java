package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.FedRates;


public interface FedRateService
{
	public List<FedRates> retrieveAll();
	public List<FedRates> retrieveAllProvinces();
	public FedRates retrieveRateByProvince(String provinceName);
	
}
