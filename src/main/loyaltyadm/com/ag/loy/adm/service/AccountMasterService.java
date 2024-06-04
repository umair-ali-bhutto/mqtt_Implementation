package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.AccountMaster;


public interface AccountMasterService {

	public void insert(AccountMaster acm);
	public void update(AccountMaster acm);
	public AccountMaster fetchById(String corpId,String accId);
	public List<AccountMaster> fetchAllByCorpId(String corpId);
	public List<AccountMaster> fetchAllByUserCode(String userCode);
	
	
	

}