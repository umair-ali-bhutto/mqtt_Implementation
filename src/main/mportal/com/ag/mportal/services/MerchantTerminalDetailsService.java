package com.ag.mportal.services;

import java.util.List;

import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.model.MerchantTerminalDetailsModel;
import com.ag.mportal.model.ReportModel;

public interface MerchantTerminalDetailsService {

	public void save(MerchantTerminalDetail tcn);
	public void savetoMerchantTerminalDetails(MerchantTerminalDetail mtd);
	public List<MerchantTerminalDetailsModel> listMerchantTerminalDetails(int txnId);
	public ReportModel txnDetails(int txnId);
	
}
