package com.ag.mportal.services;

import java.util.Date;
import java.util.List;

import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;

public interface TxnDetailsService {

	public long insertLog(TxnDetail que) throws Exception;

	public List<TxnSummary> fetchTxnSummary(List<String> merchant, String terminal, Date from, Date to, String setled,
			String batch, String auth);

	public TxnDetail fetchTxnDetail(String batch, String tid, String auth);

	public void updateTxn(TxnDetail tcn);

	public TxnDetail fetchTxnDetailVoid(String batch, String tid, String invoice);

	public TxnDetail fetchTxnDetailNonVoid(String batch, String tid, String invoice);

	public List<TxnSummary> fetchTxnSummaryUpd(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth);

	public boolean checkExistanceOfTxnDetail(String mId, String tId, String batchNumber, String refNumber,
			String authId, String type);

	public String fetchTxnDetailsCount(List<String> merchant, String terminal, Date from, Date to, String setled,
			String batch, String auth, String reportType);

	public List<ReportModel> fetchTxnDetailsReport(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, int numberOfRows, int pageNumber);

	public List<ReportModel> fetchSettlementTxnDetailsReport(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, int numberOfRows, int pageNumber);

	public List<ReportModel> fetchVoidTxnDetailsForReport(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, String type, int numberOfRows, int pageNumber);

	public ReportModel fetchTxnDetailById(String txnDetailId);

	public ReportModel fetchTxnDetailByIdForRoc(String txnDetailId);

	public List<MerchantTerminalDetail> convertTxnDetails(String xmls, int txnLogId);

	public String[] getdetailsForPreAuth(String mid, String tid, String authCode);

	public TxnDetail fetchTxnDetailPreAuth(String batch, String tid, String auth);

	public TxnDetail fetchTxnDetailVoidTxn(String mid, String tid, String auth);

	public void updateRecordPreAuth(String txnDetailsId, String paidFlag, String dateTime);
	
	public List<ReportModel> fetchMarketSegmentReport(List<String> merchant, String terminal, Date from, Date to,
			String setled, String batch, String auth, String mrktSeg, int numberOfRows, int pageNumber);
	
	public String fetchMarketSegmentCount(List<String> merchant, String terminal, Date from, Date to, String setled,
			String batch, String auth, String reportType);

}
