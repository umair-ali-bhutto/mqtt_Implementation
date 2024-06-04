package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignProducts;

public interface CampaignProductsService {

	public void saveCampaignProducts(CampaignProducts campaignProducts);

	public void delete(String campaignProducts);

	public void updateAwardMasters(CampaignProducts campaignProducts);

	public ArrayList<CampaignProducts> fetchAllRecords(String corpId);

	public ArrayList<CampaignProducts> fetchAllRecordByCampId(String campId,String corpId);
}