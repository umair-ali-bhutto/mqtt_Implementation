package com.ag.metro.services;

import java.util.List;

import com.ag.mportal.model.WeeklySalesBarChartModel;
import com.ag.mportal.model.YearlySalesChartModel;

public interface MetroDashboardService {
	public YearlySalesChartModel getYearSalesChartModel(List<String> mid);

	public WeeklySalesBarChartModel getWeeklySalesChartModel(List<String> mid, String inputdate);

}
