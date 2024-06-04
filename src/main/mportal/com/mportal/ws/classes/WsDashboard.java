package com.mportal.ws.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.BannerTickerService;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.model.PieDataChartModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.model.TxnSummaryModel;
import com.ag.mportal.model.WeeklySalesBarChartModel;
import com.ag.mportal.model.YearlySalesChartModel;
import com.ag.mportal.services.ChainMerchantsService;
import com.ag.mportal.services.DashboardService;
import com.ag.mportal.services.impl.TxnDetailsServiceImpl;

@Component("com.mportal.ws.classes.WsDashboard")
public class WsDashboard implements Wisher {
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	TxnDetailsServiceImpl txnDetailService;
	@Autowired
	ComplAssignmentsService complAssignmentsService;
	@Autowired
	ChainMerchantsService chainMerchantsService;
	@Autowired
	DashboardService dashboardService;
	@Autowired
	UserScreenService userScreenService;
	@Autowired
	BannerTickerService bannerTickerService;
	@Autowired
	PortalNotificationUtil portalNotificationUtil;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		List<TxnSummary> lstTxnSummary;
		SimpleDateFormat sdfN = new SimpleDateFormat("yyyy-MM-dd");
		List<String> lstMerchantList;
		YearlySalesChartModel yearlySaleChart;
		WeeklySalesBarChartModel weeklySalesBarChart;
		PieDataChartModel currentPieDataChart = null;
		int currentPieDataChartSize = 0;
		PieDataChartModel previousPieDataChart = null;
		int previousPieDataChartSize = 0;
		int openComplaintsCount = 0;
		int totalTerminalsCount = 0;
		int totalTmsUpdatesCount = 0;
		int totalProfileDownloadsCount = 0;
		int totalTxnPendingforSettlment = 0;
		int totalNewRequestforApproval = 0;
		String[] dtsVals;
		String defaultVal = "true";
		defaultVal = (rm.getAdditionalData().containsKey("default")) ? rm.getAdditionalData().get("default").toString()
				: "true";
		boolean isDefault = Boolean.parseBoolean(defaultVal);
		String channel = rm.getChannel();
		try {

			// Angular
			if (!channel.equalsIgnoreCase("android") && !channel.equalsIgnoreCase("ios")) {
				// yyyy-mm-dd Date Format to be expected.
				String date = (String) rm.getAdditionalData().get("date");
				String userId = rm.getUserid();
				UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(userId));
				yearlySaleChart = new YearlySalesChartModel();
				Date finDate = sdfN.parse(date);
				UserLogin usl = userLoginService.validetUserid(Integer.parseInt(userId));
				lstMerchantList = fetchMerchantOptions(userId, usl);
				if (usl.getMid().equalsIgnoreCase("all")) {
					lstTxnSummary = txnDetailService.fetchTxnSummaryUpd(null, null, finDate, finDate, null, null, null);
				} else {
					lstTxnSummary = txnDetailService.fetchTxnSummaryUpd(lstMerchantList, null, finDate, finDate, null,
							null, null);
				}

				/* YEAR SLAE CHART START */
				yearlySaleChart = dashboardService
						.getYearSalesChartModel((usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList);
				/* YEAR SLAE CHART END */

				/* WEEKLY SLAE CHART START */
				weeklySalesBarChart = dashboardService.getWeeklySalesChartModel(
						(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, date);
				/* WEEKLY SLAE CHART END */

				if (!isDefault) {

					dtsVals = dashboardService.getCurrentWeekDateAndPreviousWeekDate(date);
					AgLogger.logDebug(rm.getUserid(), Arrays.toString(dtsVals));
					/* Todays PIE CHART */
					HashMap<String, Object> m = dashboardService.getTodayPieChartData(
							(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, dtsVals);
					currentPieDataChart = (PieDataChartModel) m.get("obj");
					currentPieDataChartSize = (int) m.get("obj_count");
					/* Todays PIE CHART END */

					/* Previous PIE CHART */
					HashMap<String, Object> mk = dashboardService.getPreviousDayPieChartData(
							(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, dtsVals);
					previousPieDataChart = (PieDataChartModel) mk.get("obj");
					previousPieDataChartSize = (int) mk.get("obj_count");
					/* Previous PIE CHART */
				} else {

					int dayIndex = (rm.getAdditionalData().containsKey("day"))
							? Integer.parseInt(rm.getAdditionalData().get("day").toString())
							: 0;

//					String chartType = (rm.getAdditionalData().containsKey("chartType"))
//							? rm.getAdditionalData().get("chartType").toString()
//							: "N/A";
					/* Todays PIE CHART */
					dtsVals = dashboardService.getDatesByWeekIndex(date, dayIndex);
					AgLogger.logDebug(rm.getUserid(), Arrays.toString(dtsVals));
					HashMap<String, Object> m = dashboardService.getTodayPieChartData(
							(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, dtsVals);
					currentPieDataChart = (PieDataChartModel) m.get("obj");
					currentPieDataChartSize = (int) m.get("obj_count");
					/* Todays PIE CHART END */

					/* Previous PIE CHART */
					HashMap<String, Object> mk = dashboardService.getPreviousDayPieChartData(
							(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, dtsVals);
					previousPieDataChart = (PieDataChartModel) mk.get("obj");
					previousPieDataChartSize = (int) mk.get("obj_count");
					/* Previous PIE CHART */

				}

				/* OTHER CHARTS START */

				openComplaintsCount = dashboardService.getOpenComplaintsCount(
						(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, userId, ukl.getGroupCode());
				totalTerminalsCount = dashboardService.getTotalTerminalsCount(
						(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, userId);
				totalTmsUpdatesCount = dashboardService.getTotalTmsUpdatesCount(
						(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, userId);
				totalProfileDownloadsCount = dashboardService.getTotalProfileDownloadsCount(
						(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, userId);
				totalTxnPendingforSettlment = dashboardService.getTotalTxnPendingforSettlment(
						(usl.getMid().equalsIgnoreCase("all")) ? null : lstMerchantList, userId);
				totalNewRequestforApproval = dashboardService.getTotalNewRequestforApproval(userId);

				/* OTHER CHARTS END */

				// wrapper txnSummary
				TxnSummaryModel txnSummary = UtilAccess.getTxnSummaryModel(lstTxnSummary);

				HashMap<Object, Object> map = new HashMap<Object, Object>();

				HashMap<Object, Object> mpk = portalNotificationUtil.getNotificationsUpdated(Integer.parseInt(userId),
						rm.getCorpId());
				map.put("notifications", mpk.get("notifications"));
				map.put("notifications_count", mpk.get("notifications_count"));
				map.put("txn_summary_title", "Daily Summary");
				map.put("txn_summary", txnSummary);
				map.put("yearlySalesChart", yearlySaleChart);
				map.put("yearlySalesChart_title", "Yearly Detail");
				map.put("weeklySalesBarChart", weeklySalesBarChart);
				map.put("weeklySalesBarChart_title", "Weekly Detail");

				map.put("currentPieDataChart", currentPieDataChart);
				map.put("currentPieDataChart_title", "Today's Sale (" + dtsVals[0] + ")");
				map.put("currentPieDataChartSize", currentPieDataChartSize);
				map.put("currentPieDataChartSize_title", "Today's Sale Count (" + dtsVals[0] + ")");
				map.put("previousPieDataChart", previousPieDataChart);
				map.put("previousPieDataChart_title", "Previous Day's Sale (" + dtsVals[1] + ")");
				map.put("previousPieDataChartSize", previousPieDataChartSize);
				map.put("previousPieDataChartSize_title", "Previous Day's Sale Count (" + dtsVals[1] + ")");

				map.put("openComplaintsCount", openComplaintsCount);
				map.put("openComplaintsCount_title", "Open Complaints Count");
				map.put("totalTerminalsCount", totalTerminalsCount);
				map.put("totalTerminalsCount_title", "Total Terminals Count");
				map.put("totalTmsUpdatesCount", totalTmsUpdatesCount);
				map.put("totalTmsUpdatesCount_title", "Total Tms Updates Count");
				map.put("totalProfileDownloadsCount", totalProfileDownloadsCount);
				map.put("totalProfileDownloadsCount_title", "Profile Downloads Count");
				map.put("totalTxnPendingforSettlment", totalTxnPendingforSettlment);
				map.put("totalTxnPendingforSettlment_title", "Total Txn Pending for Settlement");
				map.put("totalNewRequestforApproval", totalNewRequestforApproval);
				map.put("totalNewRequestforApproval_title", "Total New Request for Approval");

				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(map);
			} else {
				// Mobile
				String date = (String) rm.getAdditionalData().get("date");
				String userId = rm.getUserid();
				UserLogin usl = userLoginService.validetUserid(Integer.parseInt(userId));
				Date finDate = sdfN.parse(date);

				lstMerchantList = fetchMerchantOptions(userId, usl);
				if (usl.getMid().equalsIgnoreCase("all")) {
					lstTxnSummary = txnDetailService.fetchTxnSummaryUpd(null, null, finDate, finDate, null, null, null);
				} else {
					lstTxnSummary = txnDetailService.fetchTxnSummaryUpd(lstMerchantList, null, finDate, finDate, null,
							null, null);
				}

				// wrapper txnSummary
				TxnSummaryModel txnSummary = UtilAccess.getTxnSummaryModel(lstTxnSummary);

				String totalAmount = (txnSummary != null) ? txnSummary.getTotalAmount() : "0";
				String totalTxnCount = (txnSummary != null)
						? "" + (txnSummary.getCashOutCount() + txnSummary.getRedeemCount() + txnSummary.getRefundCount()
								+ txnSummary.getSaleCount() + txnSummary.getVoidCount())
						: "0";

				HashMap<Object, Object> map = new HashMap<Object, Object>();

				String bannerVersion = "0.0";
				if (rm.getAdditionalData() != null) {
					if (!Objects.isNull(rm.getAdditionalData().get("bannerVersion"))) {
						bannerVersion = rm.getAdditionalData().get("bannerVersion").toString();
						String latestBannerVersion = AppProp.getProperty("banner.version");
						if (!Objects.isNull(latestBannerVersion) && !latestBannerVersion.isEmpty()
								&& !Objects.isNull(bannerVersion) && !bannerVersion.isEmpty()) {
							if (!latestBannerVersion.equals(bannerVersion)) {
								map.put("IMAGES",
										bannerTickerService.getBannerTickerByCorpId("BANNER", rm.getCorpId()));
								map.put("bannerVersion", latestBannerVersion);
							} else {
								map.put("bannerVersion", bannerVersion);
							}
						} else {
							map.put("bannerVersion", bannerVersion);
						}
					}

				}

				HashMap<Object, Object> mpk = portalNotificationUtil.getNotificationsUpdated(Integer.parseInt(userId),
						rm.getCorpId());
				map.put("txnsummary", txnSummary);
				map.put("totalAmount", totalAmount);
				map.put("totalCount", totalTxnCount);
				map.put("totalNotifications", mpk.get("notifications_count"));
				map.put("notifications", mpk.get("notifications"));

				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(map);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public List<String> fetchMerchantOptions(String userID, UserLogin usl) {
		List<String> skl = new ArrayList<String>();
		if (usl != null) {
			if (usl.getMid().equalsIgnoreCase("all")) {
				skl.add("All");
				List<ChainMerchant> cmh = chainMerchantsService.fetchAll();
				for (ChainMerchant c : cmh) {
					skl.add(c.getMid());
				}

			} else {
				List<ChainMerchant> cmh = chainMerchantsService.fetchAllByID(usl.getMid());
				for (ChainMerchant c : cmh) {
					skl.add(String.valueOf(c.getMid()));
				}

			}

			List<String> listWithoutDuplicates = skl.stream().distinct().collect(Collectors.toList());
			if (listWithoutDuplicates.size() == 0) {
				listWithoutDuplicates.add(usl.getMid());
			}

			return listWithoutDuplicates;
		}

		return null;

	}

	public Map<Integer, UserScreen> convertListToMapScreen(List<UserScreen> list) {
		Map<Integer, UserScreen> map = list.stream()
				.collect(Collectors.toMap(UserScreen::getScreenId, Function.identity()));
		return map;
	}

}