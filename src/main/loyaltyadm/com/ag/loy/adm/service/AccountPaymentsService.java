package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.AccountPayments;


public interface AccountPaymentsService {

	public ArrayList<AccountPayments> fetchAll(String corpId);
	public ArrayList<AccountPayments> fetch(String userId, String devBatch, String accountId,String fromDate,String toDate,String customerId, String corporateId);
	

}