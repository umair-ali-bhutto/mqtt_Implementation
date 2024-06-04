package com.loy.adm.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.loy.adm.model.BarChartModel;
import com.ag.loy.adm.model.LineChartModel;
import com.ag.loy.adm.model.PieChartModel;
import com.ag.loy.adm.model.StackedBarChartModel;
import com.ag.loy.adm.model.TopCustomerModel;
import com.ag.loy.adm.model.TxnSummaryModel;
import com.ag.loy.adm.service.LoyAdmDashboardService;

@Component("com.loy.adm.ws.classes.WsLoyAdmDashboard")
public class WsLoyAdmDashboard implements Wisher {

	@Autowired
	PortalNotificationUtil portalNotificationUtil;
	@Autowired
	LoyAdmDashboardService admDashboardService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			HashMap<Object, Object> resp = new HashMap<Object, Object>();

			String dashboardType = rm.getAdditionalData().containsKey("dashboardType")
					? rm.getAdditionalData().get("dashboardType").toString()
					: "N/A";

			switch (dashboardType) {
			case "1":
				resp = adminDashboard(rm, dashboardType);
				break;
			case "2":
				resp = corporateDashboard(rm, dashboardType);
				break;
			case "3":
				resp = retailDashboard(rm, dashboardType);
				break;
			default:
				resp = defaultDashboard();
			}

			HashMap<Object, Object> mpk = portalNotificationUtil
					.getNotificationsUpdated(Integer.parseInt(rm.getUserid()), rm.getCorpId());
			resp.put("notifications", mpk.get("notifications"));
			resp.put("notifications_count", mpk.get("notifications_count"));

			response.setCode(resp.get("code").toString());
			response.setMessage(resp.get("message").toString());
			response.setData(resp);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	HashMap<Object, Object> adminDashboard(RequestModel rm, String dashboardType) {
		HashMap<Object, Object> resp = new HashMap<Object, Object>();
		ResponseModel response = new ResponseModel();

		try {
			AgLogger.logInfo("Loyalty Admin Dashboard");

			String topMerchantListType = rm.getAdditionalData().containsKey("topMerchantListType")
					? rm.getAdditionalData().get("topMerchantListType").toString()
					: "N/A";

			String selectedChannel = rm.getAdditionalData().containsKey("selectedChannel")
					? rm.getAdditionalData().get("selectedChannel").toString()
					: "N/A";

			String topCustListType = rm.getAdditionalData().containsKey("topCustListType")
					? rm.getAdditionalData().get("topCustListType").toString()
					: "N/A";

			// CARDS
			List<TxnSummaryModel> lst = admDashboardService.fetchTxnSummary(dashboardType, rm.getCorpId(),
					selectedChannel);

			// TOP CUSTOMER DATA
			List<TopCustomerModel> TopCustList = admDashboardService.fetchTopCustomerData(dashboardType, rm.getCorpId(),
					topCustListType);
			String TopCustTitle = admDashboardService.fetchTopCustomerTitle(dashboardType);

			// TOP MERCHANT DATA
			List<TopCustomerModel> TopMerchantList = admDashboardService.fetchTopMerchantData(dashboardType,
					rm.getCorpId(), topMerchantListType);
			String TopMerchantTitle = admDashboardService.fetchTopMerchantTitle(dashboardType);

			// DONUT CHART
			PieChartModel DonutChartData = admDashboardService.fetchDonutChartData(dashboardType, rm.getCorpId());
			String DonutChartTitle = admDashboardService.fetchDonutChartTitle(dashboardType);

			// STACKED BAR CHART
			StackedBarChartModel StackedBarChartData = admDashboardService.fetchStackedBarChartData(dashboardType,
					rm.getCorpId());
			String StackedBarChartTitle = admDashboardService.fetchStackedBarChartTitle(dashboardType);
			resp.put("StackedBarChartTitle", StackedBarChartTitle);
			resp.put("StackedBarChartData", StackedBarChartData);

			resp.put("lst", lst);

			resp.put("TopCustomerListTitle", TopCustTitle);
			resp.put("TopCustomerListData", TopCustList);

			resp.put("TopMerchantListTitle", TopMerchantTitle);
			resp.put("TopMerchantListData", TopMerchantList);

			resp.put("DonutChartTitle", DonutChartTitle);
			resp.put("DonutChartData", DonutChartData);

			response.setCode("0000");
			response.setMessage("Success.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9991");
			response.setMessage("Something Went Wrong.");
		}

		resp.put("code", response.getCode());
		resp.put("message", response.getMessage());

		return resp;
	}

	HashMap<Object, Object> corporateDashboard(RequestModel rm, String dashboardType) {
		HashMap<Object, Object> resp = new HashMap<Object, Object>();
		ResponseModel response = new ResponseModel();
		try {
			AgLogger.logInfo("Loyalty Corporate Dashboard");

			String topMerchantListType = rm.getAdditionalData().containsKey("topMerchantListType")
					? rm.getAdditionalData().get("topMerchantListType").toString()
					: "N/A";

			String selectedChannel = rm.getAdditionalData().containsKey("selectedChannel")
					? rm.getAdditionalData().get("selectedChannel").toString()
					: "N/A";

			String topCustListType = rm.getAdditionalData().containsKey("topCustListType")
					? rm.getAdditionalData().get("topCustListType").toString()
					: "N/A";

			// CARDS
			List<TxnSummaryModel> lst = admDashboardService.fetchTxnSummary(dashboardType, rm.getCorpId(),
					selectedChannel);

			// TOP CUSTOMER DATA
			List<TopCustomerModel> TopCustList = admDashboardService.fetchTopCustomerData(dashboardType, rm.getCorpId(),
					topCustListType);
			String TopCustTitle = admDashboardService.fetchTopCustomerTitle(dashboardType);

			// TOP MERCHANT DATA
			List<TopCustomerModel> TopMerchantList = admDashboardService.fetchTopMerchantData(dashboardType,
					rm.getCorpId(), topMerchantListType);
			String TopMerchantTitle = admDashboardService.fetchTopMerchantTitle(dashboardType);

			// DONUT CHART
			PieChartModel DonutChartData = admDashboardService.fetchDonutChartData(dashboardType, rm.getCorpId());
			String DonutChartTitle = admDashboardService.fetchDonutChartTitle(dashboardType);

			// STACKED BAR CHART
			StackedBarChartModel StackedBarChartData = admDashboardService.fetchStackedBarChartData(dashboardType,
					rm.getCorpId());
			String StackedBarChartTitle = admDashboardService.fetchStackedBarChartTitle(dashboardType);
			resp.put("StackedBarChartTitle", StackedBarChartTitle);
			resp.put("StackedBarChartData", StackedBarChartData);

			resp.put("lst", lst);

			resp.put("TopCustomerListTitle", TopCustTitle);
			resp.put("TopCustomerListData", TopCustList);

			resp.put("TopMerchantListTitle", TopMerchantTitle);
			resp.put("TopMerchantListData", TopMerchantList);

			resp.put("DonutChartTitle", DonutChartTitle);
			resp.put("DonutChartData", DonutChartData);

			response.setCode("0000");
			response.setMessage("Success.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9992");
			response.setMessage("Something Went Wrong.");
		}

		resp.put("code", response.getCode());
		resp.put("message", response.getMessage());

		return resp;
	}

	HashMap<Object, Object> retailDashboard(RequestModel rm, String dashboardType) {
		HashMap<Object, Object> resp = new HashMap<Object, Object>();
		ResponseModel response = new ResponseModel();
		try {

			AgLogger.logInfo("Loyalty Retail Dashboard");

			String topMerchantListType = rm.getAdditionalData().containsKey("topMerchantListType")
					? rm.getAdditionalData().get("topMerchantListType").toString()
					: "N/A";

			String selectedChannel = rm.getAdditionalData().containsKey("selectedChannel")
					? rm.getAdditionalData().get("selectedChannel").toString()
					: "N/A";

			String topCustListType = rm.getAdditionalData().containsKey("topCustListType")
					? rm.getAdditionalData().get("topCustListType").toString()
					: "N/A";

			// CARDS
			List<TxnSummaryModel> lst = admDashboardService.fetchTxnSummary(dashboardType, rm.getCorpId(),
					selectedChannel);

			// TOP CUSTOMER DATA
			List<TopCustomerModel> TopCustList = admDashboardService.fetchTopCustomerData(dashboardType, rm.getCorpId(),
					topCustListType);
			String TopCustTitle = admDashboardService.fetchTopCustomerTitle(dashboardType);

			// TOP MERCHANT DATA
			List<TopCustomerModel> TopMerchantList = admDashboardService.fetchTopMerchantData(dashboardType,
					rm.getCorpId(), topMerchantListType);
			String TopMerchantTitle = admDashboardService.fetchTopMerchantTitle(dashboardType);

			// DONUT CHART
			PieChartModel DonutChartData = admDashboardService.fetchDonutChartData(dashboardType, rm.getCorpId());
			String DonutChartTitle = admDashboardService.fetchDonutChartTitle(dashboardType);

			// STACKED BAR CHART
			StackedBarChartModel StackedBarChartData = admDashboardService.fetchStackedBarChartData(dashboardType,
					rm.getCorpId());
			String StackedBarChartTitle = admDashboardService.fetchStackedBarChartTitle(dashboardType);
			resp.put("StackedBarChartTitle", StackedBarChartTitle);
			resp.put("StackedBarChartData", StackedBarChartData);

			resp.put("lst", lst);

			resp.put("TopCustomerListTitle", TopCustTitle);
			resp.put("TopCustomerListData", TopCustList);

			resp.put("TopMerchantListTitle", TopMerchantTitle);
			resp.put("TopMerchantListData", TopMerchantList);

			resp.put("DonutChartTitle", DonutChartTitle);
			resp.put("DonutChartData", DonutChartData);

			response.setCode("0000");
			response.setMessage("Success.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9993");
			response.setMessage("Something Went Wrong.");
		}

		resp.put("code", response.getCode());
		resp.put("message", response.getMessage());

		return resp;
	}

	HashMap<Object, Object> defaultDashboard() {
		HashMap<Object, Object> resp = new HashMap<Object, Object>();
		ResponseModel response = new ResponseModel();
		try {
			System.out.println("DEFAULT DASHBOARD");

			response.setCode("0000");
			response.setMessage("Default Dashboard.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9994");
			response.setMessage("Something Went Wrong.");
		}

		resp.put("code", response.getCode());
		resp.put("message", response.getMessage());

		return resp;
	}

}