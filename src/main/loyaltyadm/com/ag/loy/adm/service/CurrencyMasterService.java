package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.CurrencyMaster;


public interface CurrencyMasterService{
	
	public Long saveCurrencyMaster(CurrencyMaster sdt);
	public CurrencyMaster fetchAllRecordById(String id,String corpId);
	public List<CurrencyMaster> fetchAllRecord(String corpId);

}