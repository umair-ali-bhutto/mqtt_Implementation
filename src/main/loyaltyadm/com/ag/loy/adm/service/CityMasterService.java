package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.CityMaster;


public interface CityMasterService {

	public void insert(CityMaster acm);
	public void update(CityMaster acm);
	public CityMaster fetchById(String corpId,String cityId);
	public List<CityMaster> fetchAllByCorpId(String corpId);
	
	

}