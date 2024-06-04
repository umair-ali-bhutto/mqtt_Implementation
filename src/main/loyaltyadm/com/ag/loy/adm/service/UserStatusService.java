package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.UserStatusMaster;


public interface UserStatusService {

	public void insert(UserStatusMaster acm);
	public void update(UserStatusMaster acm);
	public UserStatusMaster fetchById(String corpId,String accId);
	public List<UserStatusMaster> fetchAllByCorpId(String corpId);
	public List<UserStatusMaster> fetchAllByCorpIdUtype(String corpId,String usertype);
	public List<UserStatusMaster> fetchByCorpId(String corpId);
	public UserStatusMaster fetchByUsertype(String corpId, String usertype);
	

}