package com.ag.metro.services;

import java.util.List;

import com.ag.metro.model.VwMetroRepParametersModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.model.TxnSummaryModel;

public interface MetroUtilService {
	
	
	public List<VwMetroRepParametersModel> getLovsOfMetro();
	public TxnSummaryModel fetchMetroDashboard();

}
