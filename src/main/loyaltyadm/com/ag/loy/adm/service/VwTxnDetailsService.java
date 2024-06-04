package com.ag.loy.adm.service;

import java.util.ArrayList;
import java.util.List;

import com.ag.generic.model.RequestModel;
import com.ag.loy.adm.entity.VwTxnDetails;

public interface VwTxnDetailsService {

	public List<VwTxnDetails> fetchAll();

	
	public List<VwTxnDetails> fetchByParams(String userId, String devBatch, String accountId, String fromDate,
			String toDate, String customerId, String corporateId, String invoice, String cardNumber, String rrn,
			String terminalId);
	public String[] downloadReport(List<VwTxnDetails> lst , RequestModel rm);

}
