package com.metro.ws.classes;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.metro.services.MetroDashboardService;
import com.ag.metro.services.MetroUtilService;
import com.ag.mportal.model.TxnSummaryModel;
import com.ag.mportal.model.WeeklySalesBarChartModel;
import com.ag.mportal.model.YearlySalesChartModel;

@Component("com.metro.ws.classes.WsMetroDashboard")
public class WsMetroDashboard implements Wisher {

	@Autowired
	MetroDashboardService dashboardService;
	@Autowired
	PortalNotificationUtil portalNotificationUtil;
	@Autowired
	MetroUtilService metroUtilService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		
		YearlySalesChartModel yearlySaleChart;
		WeeklySalesBarChartModel weeklySalesBarChart;

		try {
			String date = (String) rm.getAdditionalData().get("date");
			HashMap<Object, Object> map = new HashMap<Object, Object>();

			
			response.setCode("0000");
			response.setMessage("SUCCESS");

			/* YEAR SLAE CHART START */
			yearlySaleChart = dashboardService.getYearSalesChartModel(null);
			/* YEAR SLAE CHART END */

			/* WEEKLY SLAE CHART START */
			weeklySalesBarChart = dashboardService.getWeeklySalesChartModel(null, date);
			TxnSummaryModel txnSummary = metroUtilService.fetchMetroDashboard();
			HashMap<Object, Object> mpk = portalNotificationUtil
					.getNotificationsUpdated(Integer.parseInt(rm.getUserid()), rm.getCorpId());
			map.put("notifications", mpk.get("notifications"));
			map.put("notifications_count", mpk.get("notifications_count"));
			map.put("txn_summary", txnSummary);
			map.put("yearlySalesChart", yearlySaleChart);
			map.put("yearlySalesChart_title", "Yearly Detail");
			map.put("weeklySalesBarChart", weeklySalesBarChart);
			map.put("weeklySalesBarChart_title", "Weekly Detail");
			response.setData(map);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}