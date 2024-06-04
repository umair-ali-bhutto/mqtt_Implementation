package com.ag.mportal.services;

import java.util.Date;
import java.util.List;

import com.ag.mportal.model.OfflineDetailsReportModel;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;

public interface OfflineSaleService {

	public List<ReportModel> fetchOfflineSaleTxnReport(List<String> merchant, List<String> channelList, String terminal, Date from, Date to,String setled, String batch, String auth, int numberOfRows, int pageNumber);
	public List<TxnSummary> fetchOfflineSaleTxnSummary(List<String> merchant, List<String> channelList, String terminal, Date from, Date to, String setled,String batch, String auth);
	public String fetchOfflineSaleDetailsCount(List<String> merchant, List<String> channelList, String terminal, Date from,
			Date to, String setled, String batch, String auth, String reportType);
	public String[] doProcessBatchSettlmentOffline(String data);
	public void createOfflineSummaryDetailCsvFile(List<TxnSummary> txnSummaryModel, List<OfflineDetailsReportModel> txnDetails,
			String filePath);	
	public List<OfflineDetailsReportModel> fetchOfflineSaleTxnDetailsReport(List<String> merchant, List<String> channelList, String terminal,
			Date from, Date to, String setled, String batch, String auth, int numberOfRows, int pageNumber);
	}
