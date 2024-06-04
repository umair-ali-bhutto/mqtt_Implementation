package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignCurrency;

public interface CampaignCurrencyService{
	
	public void saveCampaignCurrency(CampaignCurrency campaignCurrency);

	public void updateCampaignCurrency(CampaignCurrency campaignCurrency);

	public ArrayList<CampaignCurrency> fetchAllRecords(String corpId);

	public ArrayList<CampaignCurrency> fetchAllRecordByCampId(String id,String corpId);
	
	public void delete(String campaignProducts);

}