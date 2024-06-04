package com.ag.loy.adm.service;

import java.util.ArrayList;

import com.ag.loy.adm.entity.AccountPaymentsLog;


public interface AccountPaymentsLogService {

	public ArrayList<AccountPaymentsLog> fetchAll(String corpId);
	public ArrayList<AccountPaymentsLog> fetch(String userId, String devBatch, String accountId, String fromDate,
			String toDate, String customerId, String corporateId) ;

}