package com.ag.mportal.services;

import java.util.HashMap;
import java.util.List;

import com.ag.mportal.model.WeeklySalesBarChartModel;
import com.ag.mportal.model.YearlySalesChartModel;

public interface DashboardService {
	public YearlySalesChartModel getYearSalesChartModel(List<String> mid);

	public WeeklySalesBarChartModel getWeeklySalesChartModel(List<String> mid, String inputdate);

	public HashMap<String, Object> getTodayPieChartData(List<String> mid,String[] datValues);

	public HashMap<String, Object> getPreviousDayPieChartData(List<String> mid,String[] datValues);

	public int getOpenComplaintsCount(List<String> mid, String userId,int userGroupCOde);

	public int getTotalTerminalsCount(List<String> mid, String userId);

	public int getTotalTmsUpdatesCount(List<String> mid, String userId);

	public int getTotalProfileDownloadsCount(List<String> mid, String userId);

	public int getTotalTxnPendingforSettlment(List<String> mid, String userId);

	public int getTotalNewRequestforApproval(String userId);
	
	public String[] getCurrentWeekDateAndPreviousWeekDate(String date);
	
	public String[] getDatesByWeekIndex(String date,int dayIndex);
}
