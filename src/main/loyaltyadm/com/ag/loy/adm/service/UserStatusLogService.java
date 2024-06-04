package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.UserStatusLog;

public interface UserStatusLogService {
	
	public List<UserStatusLog> fetchAll();
	public UserStatusLog fetchByCardNo(String corpId, String cardNumber);

}
