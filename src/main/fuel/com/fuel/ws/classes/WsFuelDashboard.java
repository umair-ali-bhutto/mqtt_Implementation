package com.fuel.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.model.DealerProductsModel;
import com.ag.fuel.model.FuelConfigModel;
import com.ag.fuel.model.LineChartModel;
import com.ag.fuel.model.PieChartModel;
import com.ag.fuel.model.TopDealerModel;
import com.ag.fuel.services.FuelDashboardService;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;

@Component("com.fuel.ws.classes.WsFuelDashboard")
public class WsFuelDashboard implements Wisher {

	@Autowired
	PortalNotificationUtil portalNotificationUtil;

	@Autowired
	FuelDashboardService dashboardService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			HashMap<Object, Object> resp = new HashMap<Object, Object>();

			String dashboardType = rm.getAdditionalData().containsKey("dashboardType")
					? rm.getAdditionalData().get("dashboardType").toString()
					: "N/A";

			String currentMonth = rm.getAdditionalData().containsKey("currentMonth")
					? rm.getAdditionalData().get("currentMonth").toString()
					: "N/A";

			String previousMonth = rm.getAdditionalData().containsKey("previousMonth")
					? rm.getAdditionalData().get("previousMonth").toString()
					: "N/A";

			String topDealerType = rm.getAdditionalData().containsKey("topDealerType")
					? rm.getAdditionalData().get("topDealerType").toString()
					: "N/A";

			String groupCode = rm.getAdditionalData().containsKey("groupCode")
					? rm.getAdditionalData().get("groupCode").toString()
					: "N/A";

			String userCode = rm.getAdditionalData().containsKey("userCode")
					? rm.getAdditionalData().get("userCode").toString()
					: "N/A";

			String date = rm.getAdditionalData().containsKey("date") ? rm.getAdditionalData().get("date").toString()
					: "N/A";

			switch (dashboardType) {
			case "1":
				resp = OmcDashboard(rm, groupCode, userCode, dashboardType, currentMonth, previousMonth, topDealerType,
						date);
				break;
			case "2":
				resp = CustomerDashboard(rm, groupCode, userCode, dashboardType, currentMonth, previousMonth,
						topDealerType, date);
				break;
			case "3":
				resp = DealerDashboard(rm, groupCode, userCode, dashboardType, currentMonth, previousMonth,
						topDealerType, date);
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

	HashMap<Object, Object> OmcDashboard(RequestModel rm, String groupCode, String userCode, String dashboardType,
			String currentMonth, String previousMonth, String topDealerType, String date) {
		HashMap<Object, Object> resp = new HashMap<Object, Object>();

		try {
			System.out.println("OMC DASHBOARD");

			// PRODUCTS
			List<FuelConfigModel> products = dashboardService.fetchProducts(dashboardType, rm.getCorpId(), userCode);

			// CURRENT MONTH LINE CHART
			String currentLineChartTitle = dashboardService.fetchCurrentLineChartTitle(dashboardType);
			LineChartModel currentLineChartData = dashboardService.fetchCurrentLineChartData(dashboardType,
					rm.getCorpId(), currentMonth, userCode);

			// PREVIOUS MONTH LINE CHART
			String previousLineChartTitle = dashboardService.fetchPreviousLineChartTitle(dashboardType);
			LineChartModel previousLineChartData = dashboardService.fetchPreviousLineChartData(dashboardType,
					rm.getCorpId(), previousMonth, userCode);

			// DONUT CHART
			String donutChartTitle = dashboardService.fetchDonutChartTitle(dashboardType);
			PieChartModel donutChartData = dashboardService.fetchDonutChartData(dashboardType, rm.getCorpId(), date,
					userCode);

			// TOP DEALER
			String topDealerTitle = dashboardService.fetchTopDealerTitle(dashboardType);
			List<TopDealerModel> topDealerList = dashboardService.fetchTopDealerData(dashboardType, rm.getCorpId(),
					topDealerType, userCode);

////			// PIE CHART
////			String pieChartTitle = dashboardService.fetchPieChartTitle(dashboardType);
////			PieChartModel pieChartData = dashboardService.fetchPieChartData(dashboardType, rm.getCorpId(),userCode);
////
////			//PRODUCTS PRICES 
////			String productPriceTitle = dashboardService.fetchProductPriceTitle(dashboardType);
////			List<DealerProductsModel> productPriceList = dashboardService.fetchProductPriceList(dashboardType,
////					rm.getCorpId());
////			resp.put("pieChartTitle", pieChartTitle);
////			resp.put("pieChartData", pieChartData);
////
////			resp.put("productPriceTitle", productPriceTitle);
////			resp.put("productPriceList", productPriceList);

			resp.put("products", products);

			resp.put("currentLineChartTitle", currentLineChartTitle);
			resp.put("currentLineChartData", currentLineChartData);

			resp.put("previousLineChartTitle", previousLineChartTitle);
			resp.put("previousLineChartData", previousLineChartData);

			resp.put("donutChartTitle", donutChartTitle);
			resp.put("donutChartData", donutChartData);

			resp.put("topDealerTitle", topDealerTitle);
			resp.put("topDealerList", topDealerList);

			resp.put("code", "0000");
			resp.put("message", "Success.");

		} catch (Exception e) {
			e.printStackTrace();
			resp.put("code", "9991");
			resp.put("message", "Something Went Wrong.");
		}

		return resp;
	}

	HashMap<Object, Object> CustomerDashboard(RequestModel rm, String groupCode, String userCode, String dashboardType,
			String currentMonth, String previousMonth, String topDealerType, String date) {
		HashMap<Object, Object> resp = new HashMap<Object, Object>();
		try {
			System.out.println("CORPORATE DASHBOARD");

			// PRODUCTS
			List<FuelConfigModel> products = dashboardService.fetchProducts(dashboardType, rm.getCorpId(), userCode);

			// CURRENT MONTH LINE CHART
			String currentLineChartTitle = dashboardService.fetchCurrentLineChartTitle(dashboardType);
			LineChartModel currentLineChartData = dashboardService.fetchCurrentLineChartData(dashboardType,
					rm.getCorpId(), currentMonth, userCode);

			// PREVIOUS MONTH LINE CHART
			String previousLineChartTitle = dashboardService.fetchPreviousLineChartTitle(dashboardType);
			LineChartModel previousLineChartData = dashboardService.fetchPreviousLineChartData(dashboardType,
					rm.getCorpId(), previousMonth, userCode);

			// DONUT CHART
			String donutChartTitle = dashboardService.fetchDonutChartTitle(dashboardType);
			PieChartModel donutChartData = dashboardService.fetchDonutChartData(dashboardType, rm.getCorpId(), date,
					userCode);

			// PIE CHART
			String pieChartTitle = dashboardService.fetchPieChartTitle(dashboardType);
			PieChartModel pieChartData = dashboardService.fetchPieChartData(dashboardType, rm.getCorpId(), userCode);

			// TOP DEALER
			String topDealerTitle = dashboardService.fetchTopDealerTitle(dashboardType);
			List<TopDealerModel> topDealerList = dashboardService.fetchTopDealerData(dashboardType, rm.getCorpId(),
					topDealerType, userCode);

////			//PRODUCTS PRICES
////			String productPriceTitle = dashboardService.fetchProductPriceTitle(dashboardType);
////			List<DealerProductsModel> productPriceList = dashboardService.fetchProductPriceList(dashboardType,
////					rm.getCorpId());
////			resp.put("productPriceTitle", productPriceTitle);
////			resp.put("productPriceList", productPriceList);

			resp.put("products", products);

			resp.put("currentLineChartTitle", currentLineChartTitle);
			resp.put("currentLineChartData", currentLineChartData);

			resp.put("previousLineChartTitle", previousLineChartTitle);
			resp.put("previousLineChartData", previousLineChartData);

			resp.put("donutChartTitle", donutChartTitle);
			resp.put("donutChartData", donutChartData);

			resp.put("topDealerTitle", topDealerTitle);
			resp.put("topDealerList", topDealerList);

			resp.put("pieChartTitle", pieChartTitle);
			resp.put("pieChartData", pieChartData);

			resp.put("code", "0000");
			resp.put("message", "Success.");

		} catch (Exception e) {
			e.printStackTrace();
			resp.put("code", "9992");
			resp.put("message", "Something Went Wrong.");
		}

		return resp;
	}

	HashMap<Object, Object> DealerDashboard(RequestModel rm, String groupCode, String userCode, String dashboardType,
			String currentMonth, String previousMonth, String topDealerType, String date) {
		HashMap<Object, Object> resp = new HashMap<Object, Object>();

		try {
			System.out.println("RETAIL DASHBOARD");

			// PRODUCTS
			List<FuelConfigModel> products = dashboardService.fetchProducts(dashboardType, rm.getCorpId(), userCode);

			// CURRENT MONTH LINE CHART
			String currentLineChartTitle = dashboardService.fetchCurrentLineChartTitle(dashboardType);
			LineChartModel currentLineChartData = dashboardService.fetchCurrentLineChartData(dashboardType,
					rm.getCorpId(), currentMonth, userCode);

			// PREVIOUS MONTH LINE CHART
			String previousLineChartTitle = dashboardService.fetchPreviousLineChartTitle(dashboardType);
			LineChartModel previousLineChartData = dashboardService.fetchPreviousLineChartData(dashboardType,
					rm.getCorpId(), previousMonth, userCode);

			// DONUT CHART
			String donutChartTitle = dashboardService.fetchDonutChartTitle(dashboardType);
			PieChartModel donutChartData = dashboardService.fetchDonutChartData(dashboardType, rm.getCorpId(), date,
					userCode);

			// PRODUCTS PRICES
			String productPriceTitle = dashboardService.fetchProductPriceTitle(dashboardType);
			List<DealerProductsModel> productPriceList = dashboardService.fetchProductPriceList(dashboardType,
					rm.getCorpId(), userCode);

////			// PIE CHART
////			String pieChartTitle = dashboardService.fetchPieChartTitle(dashboardType);
////			PieChartModel pieChartData = dashboardService.fetchPieChartData(dashboardType, rm.getCorpId(),userCode);
////
////			// TOP DEALER
////			String topDealerTitle = dashboardService.fetchTopDealerTitle(dashboardType);
////			List<TopDealerModel> topDealerList = dashboardService.fetchTopDealerData(dashboardType, rm.getCorpId(),
////					topDealerType);
////			resp.put("topDealerTitle", topDealerTitle);
////			resp.put("topDealerList", topDealerList);
////
////			resp.put("pieChartTitle", pieChartTitle);
////			resp.put("pieChartData", pieChartData);

			resp.put("products", products);

			resp.put("currentLineChartTitle", currentLineChartTitle);
			resp.put("currentLineChartData", currentLineChartData);

			resp.put("previousLineChartTitle", previousLineChartTitle);
			resp.put("previousLineChartData", previousLineChartData);

			resp.put("donutChartTitle", donutChartTitle);
			resp.put("donutChartData", donutChartData);

			resp.put("productPriceTitle", productPriceTitle);
			resp.put("productPriceList", productPriceList);

			resp.put("code", "0000");
			resp.put("message", "Success.");

		} catch (Exception e) {
			e.printStackTrace();
			resp.put("code", "9993");
			resp.put("message", "Something Went Wrong.");
		}

		return resp;
	}

	HashMap<Object, Object> defaultDashboard() {
		HashMap<Object, Object> resp = new HashMap<Object, Object>();
		try {
			System.out.println("DEFAULT DASHBOARD");

			resp.put("code", "0000");
			resp.put("message", "Success.");

		} catch (Exception e) {
			e.printStackTrace();
			resp.put("code", "9994");
			resp.put("message", "Something Went Wrong.");
		}

		return resp;
	}

}