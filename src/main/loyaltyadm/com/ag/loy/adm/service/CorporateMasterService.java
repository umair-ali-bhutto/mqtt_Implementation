package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.CorporateMaster;


public interface CorporateMasterService {

	public void insert(CorporateMaster acm);
	public void update(CorporateMaster acm);
	public List<CorporateMaster> fetchByCorpId(String corpId);
	public List<CorporateMaster> fetchAllList(int groupCode, String corpId);	
	public CorporateMaster fetchCorpId(String corpId);
	public String fetchMaxCorpid();
	public List<CorporateMaster> fetchAll();
	public List<CorporateMaster> fetchByFilter(String corpid, String status);
}