package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.UserStatusState;


public interface UserStatusStateService {

	public void insert(UserStatusState acm);
	public void update(UserStatusState acm);
	public UserStatusState fetchById(String corpId,String accId);
	public List<UserStatusState> fetchAllByCorpId(String corpId);
	
	

}