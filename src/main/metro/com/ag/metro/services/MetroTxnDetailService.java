package com.ag.metro.services;

import java.util.Date;
import java.util.List;

import com.ag.generic.model.RequestModel;
import com.ag.metro.entity.MetroTxnDetail;

public interface MetroTxnDetailService {
	
	public List<MetroTxnDetail> fetchResultTopup(String cardType,String store,String batchNumber
			,Date dateFrom,Date dateTo);
	public List<MetroTxnDetail> fetchResultRedemption(String cardType,String store,String txnStatus
			,Date dateFrom,Date dateTo);
	
	public String[] createReportforTopup(List<MetroTxnDetail> lstReportItems,RequestModel rm); 
	public String[] createReportforRedemption(List<MetroTxnDetail> lstReportItems,RequestModel rm);
	

}
