package com.ag.mportal.services;

import java.util.Date;
import java.util.List;

import com.ag.mportal.entity.TxnDetail;




public interface PreAuthReportService {
	
	public List<TxnDetail> fetchPreAuthCompletionReport(List<String> merchant,Date from, Date to,String auth);
	




}
