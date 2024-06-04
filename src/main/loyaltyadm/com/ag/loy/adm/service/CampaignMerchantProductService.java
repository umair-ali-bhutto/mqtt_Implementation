package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignMerchantProduct;

public interface CampaignMerchantProductService {

	public void saveCampaignProducts(CampaignMerchantProduct campaignProducts);

	public void delete(String campaignProducts);

	public void updateAwardMasters(CampaignMerchantProduct campaignProducts);

	public ArrayList<CampaignMerchantProduct> fetchAllRecords(String corpId);

	public ArrayList<CampaignMerchantProduct> fetchAllRecordByCampId(String campId,String corpId);

}