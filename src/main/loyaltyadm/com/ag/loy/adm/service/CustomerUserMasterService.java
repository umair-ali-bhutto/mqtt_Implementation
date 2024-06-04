package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.CustomerUserMaster;

public interface CustomerUserMasterService {
	
	public List<CustomerUserMaster> findAllByCidCorpId(String cid, String corpId);
	public List<CustomerUserMaster> findAllByCardNoCorpId(String cardNumber, String corpId);
	public CustomerUserMaster findAllByUidCorpId(String uid, String corpId);
	public List<CustomerUserMaster> fetchAllByUserCode(String userCode);
}
