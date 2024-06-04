package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignMaster;

public interface CampaignMasterService{

	public ArrayList<CampaignMaster> fetchAllRecords(String corpId);

	public CampaignMaster fetchAllRecordById(String id,String corpId);

	public Long save(CampaignMaster campaignMaster);
	
	public void update(CampaignMaster campaignMaster);
	
	public String fetchMaxCampId();
}