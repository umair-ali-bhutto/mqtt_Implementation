package com.ag.generic.service;


import java.util.List;

import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.mportal.model.DisplayModel;

public interface LovService {

	public List<LovMaster> fetchLovs(String corpId);
	
	public List<LovDetail> fetchLovsDetailsAll(String corpId);
	
	public LovMaster fetchLovsbyId(String id,String corpId);
	
	public List<LovDetail> fetchLovsDetails(String id,String corpId);
	
	public LovDetail fetchLovsDetailsbyId(String id,String corpId);
	
	public LovDetail fetchLovsDetailsByLovIDandLovDetailID(String id,String lovId,String corpId);
	
	public LovDetail fetchLovsDetailsbyLovId(String id,String corpId);
	
	public List<DisplayModel> fetchLov(String tableName,String columName);

}
