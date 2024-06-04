package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.MerchantMaster;

public interface MerchantMasterService {
	
	public MerchantMaster findAllByMidCorpId(String mid,String corpId);
	
	public void insert(MerchantMaster merchantMaster);
	
	public void update(MerchantMaster merchantMaster);
	
	public List<MerchantMaster> findAllByCorpId(String corpId);

}
