package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignFeatures;

public interface CampaignFeaturesService {

	public void saveCampaignFeatures(CampaignFeatures campaignFeatures);

	public void updateCampaignFeatures(CampaignFeatures campaignFeatures);

	public ArrayList<CampaignFeatures> fetchAllRecords(String corpId);

	public ArrayList<CampaignFeatures> fetchAllRecordByCampId(String id,String corpId);

	public void delete(String campaignProducts);

}