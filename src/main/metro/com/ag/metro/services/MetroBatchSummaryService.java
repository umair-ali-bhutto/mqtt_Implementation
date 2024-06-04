package com.ag.metro.services;

import com.ag.metro.entity.MetroBatchSummary;

public interface MetroBatchSummaryService
{
	public int insertMetroBatchSummary(MetroBatchSummary adt);
	
	public String getBatchNumber();
	
	public String getExpiryDate(String shelfLife);
	
}
