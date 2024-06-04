package com.ag.mportal.services;

import java.math.BigDecimal;
import java.util.List;

import com.ag.mportal.entity.TapNPayTransactions;
import com.ag.mportal.model.BatchSettlmentViewModel;

public interface TapNPayTransactionService {

	public long insertBatch(TapNPayTransactions tcn);

	public BigDecimal getStanSequence();

	public BigDecimal getInvoiceNumberSequence();

	public List<TapNPayTransactions> fetchTxnByMidTidForViewAll(String mid, String tid);
	
	public List<TapNPayTransactions> fetchTxnByMidTidForVoid(String mid, String tid);
	
	public List<BatchSettlmentViewModel> fetchUnSettledTxn(String mid, String tid);
	
	public List<TapNPayTransactions> fetchUnSettledTxnUpdate(String mid, String tid);

	public TapNPayTransactions fetchTxnByRecId(long recId);
	
	public TapNPayTransactions fetchTxnByRecIdForReversal(long recId);
	
	public void update(TapNPayTransactions tcn);
	
	public List<TapNPayTransactions> validateAuthIdByMidTid(String mid, String tid, String authId);

}
