package com.ag.loy.cust.service;

public interface LoyCustDashboardService {
	
	public String getCidFromAccountUser(String corpId, String cardNumber);
	public String getCustIdFromAccountUser(String corpId, String uid);
}

