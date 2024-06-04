package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.CorporateRdmRatio;


public interface CorporateRdmRatioService {

	public void insert(CorporateRdmRatio acm);
	public void update(CorporateRdmRatio acm);
	public CorporateRdmRatio fetchById(String corpId,String accId);
	public List<CorporateRdmRatio> fetchAllByCorpId(String corpId);
	
	

}