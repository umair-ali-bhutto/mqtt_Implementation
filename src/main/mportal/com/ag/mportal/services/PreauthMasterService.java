package com.ag.mportal.services;

import java.util.List;

import com.ag.metro.model.GenericLovModel;
import com.ag.mportal.entity.PreauthMaster;

public interface PreauthMasterService {
	
	
	public List<GenericLovModel> FetchAllActiveConfig();
	public List<GenericLovModel> FetchAllConfig();
	public Boolean insert(PreauthMaster preauthMaster);
	public List<PreauthMaster> fetchData(String cardEntry,String status);
	public List<PreauthMaster> fetchAll();
	public boolean update(PreauthMaster preauthMaster);
	public PreauthMaster fetchByID(long id);
	
	
}
