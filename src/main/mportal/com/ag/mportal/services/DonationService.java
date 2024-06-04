package com.ag.mportal.services;

import java.util.Date;
import java.util.List;

import com.ag.mportal.model.DonationReportModel;
import com.ag.mportal.model.OfflineDetailsReportModel;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;

public interface DonationService {

	public List<DonationReportModel> fetchDonationTxnDetailsReport(List<String> merchant, List<String> donorMerchant,
			String terminal, Date from, Date to, String batch, String auth, int numberOfRows, int pageNumber);

	public String fetchDonationTxnDetailsReportCount(List<String> merchant, List<String> donorMerchant, String terminal,
			Date from, Date to, String batch, String auth);
	
	public void createDonationDetailCsvFile(List<DonationReportModel> txnDetails, String filePath);
}
