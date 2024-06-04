package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.CampaignCalender;

public interface CampaignCalenderService{

public ArrayList<CampaignCalender> fetchAllRecordByCampId(String id,String corpId);
	
	public void save(CampaignCalender comp);
	
	public void delete(String campaignProducts);
}