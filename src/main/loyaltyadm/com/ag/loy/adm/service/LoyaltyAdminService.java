package com.ag.loy.adm.service;

import java.util.List;

import com.ag.metro.model.GenericLovModel;

public interface LoyaltyAdminService {
	
	public String getCidFromAccountUser(String corpId, String cardNumber);
	public List<GenericLovModel> fetchCustomerTypes(String corpId);
	public String[] isRecordExist(String cardNumber, String name, String cnic, String corpId);
	public String fetchCid(String userId,String corpId);
}
