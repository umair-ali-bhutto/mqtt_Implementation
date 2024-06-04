package com.ag.fuel.services;

import java.util.List;

import com.ag.fuel.model.DealerProductsModel;
import com.ag.fuel.model.FuelConfigModel;
import com.ag.fuel.model.LineChartModel;
import com.ag.fuel.model.PieChartModel;
import com.ag.fuel.model.TopDealerModel;

public interface FuelDashboardService {

	public String fetchProductsQuery(String dashboardType);
	public List<FuelConfigModel> fetchProducts(String dashboardType, String corpId, String userCode);

	public String fetchCurrentLineChartTitle(String dashboardType);
	public String[] fetchCurrentLineChartQuery(String dashboardType);
	public LineChartModel fetchCurrentLineChartData(String dashboardType,String corpId,String currentMonth, String userCode);
	
	public String fetchPreviousLineChartTitle(String dashboardType);
	public String[] fetchPreviousLineChartQuery(String dashboardType);
	public LineChartModel fetchPreviousLineChartData(String dashboardType,String corpId,String previousMonth, String userCode);
	
	public String fetchDonutChartTitle(String dashboardType);
	public String[] fetchDonutChartQuery(String dashboardType);
	public PieChartModel fetchDonutChartData(String dashboardType, String corpId,String date, String userCode);
	
	public String fetchTopDealerTitle(String dashboardType);
	public String fetchTopDealerQuery(String dashboardType);
	public List<TopDealerModel> fetchTopDealerData(String dashboardType, String corpId, String type, String userCode);

	public String fetchPieChartTitle(String dashboardType);
	public String[] fetchPieChartQuery(String dashboardType);
	public PieChartModel fetchPieChartData(String dashboardType, String corpId,String userCode);
	
	public String fetchProductPriceTitle(String dashboardType);
	public String fetchProductPriceQuery(String dashboardType);
	public List<DealerProductsModel> fetchProductPriceList(String dashboardType, String corpId, String userCode);
	
	public String[] fetchWeeklyDates(String date);

}
