package com.ag.mportal.services;

import java.util.Date;
import java.util.List;

import com.ag.mportal.entity.EcrLog;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;

public interface EcrLogsService {

	public long insert(EcrLog tcn);

	public EcrLog fetchByID(String mid, String tid, long id);

	public EcrLog fetchByIDforPaymentSchedular(String mid, String tid, long id);

	public void update(EcrLog tcn);

	public List<EcrLog> fetchByMidTid(String mid, String tid);

	public EcrLog fetchByOrderId(String orderId);

	public EcrLog fetchPaymentByMidTid(String mid, String tid);

	public EcrLog fetchPaymentStatusByOrderId(String orderId);

	public EcrLog fetchByMidTidOrder(String mid, String tid);

	public String fetchWebEcrDetailsCount(List<String> merchant, List<String> channelList, String terminal, Date from,
			Date to, String setled, String batch, String auth, String reportType);

	public List<ReportModel> fetchWebEcrSettlementReport(List<String> merchant, List<String> channelList,
			String terminal, Date from, Date to, String setled, String batch, String auth, int numberOfRows,
			int pageNumber);

	public List<ReportModel> fetchWebEcrTxnReport(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth, int numberOfRows, int pageNumber);

	public List<TxnSummary> fetchWebEcrTxnSummary(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth);

	public void createTxnSummaryDetailCsvFile(List<TxnSummary> txnSummaryModel, List<ReportModel> txnDetails,
			String filePath);

	public void createSettlementTxnDetailCsvFile(List<ReportModel> txnDetails, String filePath);

	public void createSettlementTxnDetailExcelFile(List<ReportModel> txnDetails, String filePath);

	public List<ReportModel> fetchKEBillPaymentReport(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth, String ack, int numberOfRows, int pageNumber);
	
	public void createKEBillPaymentTxnDetailCsvFile(List<ReportModel> txnDetails, String filePath);


}
