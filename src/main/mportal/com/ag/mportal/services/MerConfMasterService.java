package com.ag.mportal.services;

import java.util.HashMap;
import java.util.List;

import com.ag.mportal.entity.MerConfDetails;
import com.ag.mportal.entity.MerConfMaster;
import com.ag.mportal.model.ViewOfflineSaleModel;
import com.ag.mportal.model.ViewR1R2MarketSegmebtThresholdModel;

public interface MerConfMasterService {

	public long insert(MerConfMaster tcn);
	public void update(MerConfMaster tcn); 
	public MerConfMaster fetchByMidTid(String mid, String tid, String type);
	public List<ViewOfflineSaleModel> fetchData(String mid, String tid);
	public List<MerConfMaster> fetchAllByMidTid(String mid, String tid);
	public MerConfMaster fetchAllById(Long ids);
	public List<MerConfMaster> fetchDataByMidTid(String mid,String tid);
	public List<ViewR1R2MarketSegmebtThresholdModel> fetchR1R2Data(String mid, String tid);
	
	
	//Fetch Data For HeartBeart
	HashMap<String,HashMap<String, String>> fetchRecordForHeartBeat(String mid, String tid, List<String> types);
	public String fetchRecordForHeartBeatBatch(String mid,String tid);
}
