package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignMerchants;

public interface CampaignMerchantsService {

	public void saveCampaignCurrency(CampaignMerchants campaignMerchants);

	public void updateCampaignCurrency(CampaignMerchants campaignMerchants);
	
	public void delete(String campid);

	public ArrayList<CampaignMerchants> fetchAllRecords(String corpId);

	public ArrayList<CampaignMerchants> fetchAllRecordByCampId(String campId,String corpId);
}