package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignCalenderProduct;

public interface CampaignCalenderProductService {

	public void saveCampaignProducts(CampaignCalenderProduct campaignProducts);

	public void delete(String campaignProducts);

	public void updateAwardMasters(CampaignCalenderProduct campaignProducts);

	public ArrayList<CampaignCalenderProduct> fetchAllRecords(String corpId);

	public ArrayList<CampaignCalenderProduct> fetchAllRecordByCampId(String campId,String corpId);
}