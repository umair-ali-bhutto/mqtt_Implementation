package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignCurrencyProduct;

public interface CampaignCurrencyProductService{


	public void saveCampaignProducts(CampaignCurrencyProduct campaignProducts);
	
	public void delete(String campaignProducts);

	public void updateAwardMasters(CampaignCurrencyProduct campaignProducts);

	public ArrayList<CampaignCurrencyProduct> fetchAllRecords(String corpId);

	public ArrayList<CampaignCurrencyProduct> fetchAllRecordByCampId(String campId,String corpId);
}