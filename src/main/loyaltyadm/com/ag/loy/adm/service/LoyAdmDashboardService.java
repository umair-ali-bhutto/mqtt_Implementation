package com.ag.loy.adm.service;

import java.util.List;

import com.ag.generic.prop.AppProp;
import com.ag.loy.adm.model.BarChartModel;
import com.ag.loy.adm.model.LineChartModel;
import com.ag.loy.adm.model.PieChartModel;
import com.ag.loy.adm.model.StackedBarChartModel;
import com.ag.loy.adm.model.TopCustomerModel;
import com.ag.loy.adm.model.TxnSummaryModel;

public interface LoyAdmDashboardService {
	public List<TxnSummaryModel> fetchTxnSummary(String dashboardType, String corpId, String channel);
	public String fetchTxnSummaryQuery(String dashboardType);
	public BarChartModel fetchBarChartData(String dashboardType, String corpId);
	public String[] fetchBarChartQuery(String dashboardType);
	public String fetchBarChartTitle(String dashboardType);
	public PieChartModel fetchPieChartData(String dashboardType, String corpId, String date);
	public String[] fetchPieChartQuery(String dashboardType);
	public String fetchPieChartTitle(String dashboardType);
	public LineChartModel fetchLineChartData(String dashboardType, String corpId);
	public String[] fetchLineChartQuery(String dashboardType);
	public String fetchLineChartTitle(String dashboardType);
	public PieChartModel fetchDonutChartData(String dashboardType, String corpId);
	public String[] fetchDonutChartQuery(String dashboardType);
	public String fetchDonutChartTitle(String dashboardType);
	public List<TopCustomerModel> fetchTopCustomerData(String dashboardType, String corpId, String type);
	public String fetchTopCustomerQuery(String dashboardType);
	public String fetchTopCustomerTitle(String dashboardType);
	public List<TopCustomerModel> fetchTopMerchantData(String dashboardType, String corpId, String type);
	public String fetchTopMerchantQuery(String dashboardType);
	public String fetchTopMerchantTitle(String dashboardType);
	public String[] fetchWeeklyDates(String date);
	public StackedBarChartModel fetchStackedBarChartData(String dashboardType, String corpId);
	public String[] fetchStackedBarChartQuery(String dashboardType);
	public String fetchStackedBarChartTitle(String dashboardType);

}
