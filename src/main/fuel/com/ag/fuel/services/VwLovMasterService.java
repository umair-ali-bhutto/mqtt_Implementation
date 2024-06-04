package com.ag.fuel.services;

import java.util.List;

import com.ag.fuel.entity.VwLovMaster;

public interface VwLovMasterService {
	public List<VwLovMaster> fetchAll();
	
	public List<VwLovMaster> fetchAllByScreenID(String screenId, String corpid);

}
