package com.ag.loy.adm.service;

import java.util.List;

import com.ag.loy.adm.entity.ChartConfigTxnSummary;

public interface ChartConfigTxnSummaryService {
	
	public List<ChartConfigTxnSummary> fetchAllByCorpId(String corpId);

}
