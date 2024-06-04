package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignFeatureProduct;

public interface CampaignFeatureProductService{
	
public void saveCampaignProducts(CampaignFeatureProduct campaignProducts);
	
	public void delete(String campaignProducts);

	public void updateAwardMasters(CampaignFeatureProduct campaignProducts);

	public ArrayList<CampaignFeatureProduct> fetchAllRecords(String corpId);

	public ArrayList<CampaignFeatureProduct> fetchAllRecordByCampId(String campId,String corpId);


}