package com.fuel.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.BroadCastMessageModel;
import com.ag.generic.model.NotificationModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.BannerTickerService;
import com.ag.generic.service.BroadCastMessageDetailService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsFuelAppDashboard")
public class WsFuelAppDashboard implements Wisher {

	@Autowired
	PortalNotificationUtil portalNotificationUtil;

	@Autowired
	BannerTickerService bannerTickerService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	BroadCastMessageDetailService broadCastMessageDetailService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());

		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
//			HashMap<Object, Object> mpk = portalNotificationUtil
//					.getNotificationsUpdated(Integer.parseInt(rm.getUserid()), rm.getCorpId());

			String bannerVersion = "0.0";
			if (rm.getAdditionalData() != null) {
				if (!Objects.isNull(rm.getAdditionalData().get("bannerVersion"))) {
					bannerVersion = rm.getAdditionalData().get("bannerVersion").toString();
					String latestBannerVersion = AppProp.getProperty("banner.version");
					if (!Objects.isNull(latestBannerVersion) && !latestBannerVersion.isEmpty()
							&& !Objects.isNull(bannerVersion) && !bannerVersion.isEmpty()) {
						if (!latestBannerVersion.equals(bannerVersion)) {
							map.put("IMAGES", bannerTickerService.getBannerTickerByCorpId("BANNER", rm.getCorpId()));
							map.put("bannerVersion", latestBannerVersion);
						} else {
							map.put("bannerVersion", bannerVersion);
						}
					} else {
						map.put("bannerVersion", bannerVersion);
					}
				}

			}

			/* Temp Work */
			List<NotificationModel> notificationModel = new ArrayList<NotificationModel>();
//			NotificationModel n = new NotificationModel();
//			n.setComplaintAssignId(1l);
//			n.setComplaintId(1);
//			n.setNotificationContent("Testing");
//			n.setNotificationHeader("Xyz");
//			n.setNtmDetails(null);
//
//			notificationModel.add(n);

			List<BroadCastMessageModel> broadCastMessageModels = new ArrayList<BroadCastMessageModel>();
			broadCastMessageModels = broadCastMessageDetailService
					.searchBrdMsgByReadDateAndUserId(Integer.parseInt(rm.getUserid()));
			String[] details = fetchDetailsAvb(rm);

			map.put("lLabel", details[0]);
			map.put("lValue", details[1]);
			map.put("rLabel", details[2]);
			map.put("rValue", details[3]);
			map.put("cardNumber", details[4]);
			map.put("totalNotifications", broadCastMessageModels.size());
			map.put("notifications", notificationModel);

			response.setCode("0000");
			response.setMessage("SUCCESS");
			response.setData(map);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something went wrong.");
		}
		return response;
	}

	private String[] fetchDetailsAvb(RequestModel rm) {
		String cardNumber = rm.getAdditionalData().get("cardNumber") == "null"
				|| rm.getAdditionalData().get("cardNumber").toString().length() == 0 ? null
						: rm.getAdditionalData().get("cardNumber").toString();

		UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
		String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "dashboard", userModel.getUserId(),
				userModel.getUserCode(), cardNumber, userModel.getGroupCode(), null);

		String[] responsValues = new String[5];
		responsValues[0] = "N/A";
		responsValues[1] = "0";
		responsValues[2] = "N/A";
		responsValues[3] = "0";
		responsValues[4] = "N/A";

		FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
		if (mdl.getCode().equals("0000")) {
			responsValues[0] = mdl.getData().get("lLabel").toString();
			responsValues[1] = mdl.getData().get("lValue").toString();
			responsValues[2] = mdl.getData().get("rLabel").toString();
			responsValues[3] = mdl.getData().get("rValue").toString();
			responsValues[4] = mdl.getData().get("cardNumber").toString();
		} else {
			responsValues[0] = "N/A";
			responsValues[1] = "0";
			responsValues[2] = "N/A";
			responsValues[3] = "0";
			responsValues[4] = "N/A";
		}

		return responsValues;
	}

}
