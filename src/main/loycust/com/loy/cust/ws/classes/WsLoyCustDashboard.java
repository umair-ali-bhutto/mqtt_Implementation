package com.loy.cust.ws.classes;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresLoyaltyEngine;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.BannerTickerService;
import com.ag.generic.service.Wisher;
import com.ag.generic.service.impl.UserLoginServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.cust.service.impl.LoyCustDashboardServiceImpl;

@Component("com.loy.cust.ws.classes.WsLoyCustDashboard")
public class WsLoyCustDashboard implements Wisher {

	@Autowired
	PortalNotificationUtil portalNotificationUtil;
	
	@Autowired
	BannerTickerService bannerTickerService;
	
	@Autowired
	UserLoginServiceImpl userLoginServiceImpl;
	
	@Autowired
	LoyCustDashboardServiceImpl loyCustDashboardService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			UserLogin ukl = userLoginServiceImpl.validetUserid(Integer.parseInt(rm.getUserid())); 
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			response.setCode("0000");
			response.setMessage("SUCCESS");
			HashMap<Object, Object> mpk = portalNotificationUtil
					.getNotificationsUpdated(Integer.parseInt(rm.getUserid()), rm.getCorpId());
			map.put("notifications", mpk.get("notifications"));
			map.put("notifications_count", mpk.get("notifications_count"));
			
			String bannerVersion = "0.0";
			if (rm.getAdditionalData() != null) {
				if (!Objects.isNull(rm.getAdditionalData().get("bannerVersion"))) {
					bannerVersion = rm.getAdditionalData().get("bannerVersion").toString();
					String latestBannerVersion = AppProp.getProperty("banner.version");
					if (!Objects.isNull(latestBannerVersion) && !latestBannerVersion.isEmpty()
							&& !Objects.isNull(bannerVersion) && !bannerVersion.isEmpty()) {
						if (!latestBannerVersion.equals(bannerVersion)) {
							map.put("IMAGES", bannerTickerService.getBannerTickerByCorpId("BANNER",rm.getCorpId()));
							map.put("bannerVersion", latestBannerVersion);
						} else {
							map.put("bannerVersion", bannerVersion);
						}
					} else {
						map.put("bannerVersion", bannerVersion);
					}
				}

			}

			String cid = loyCustDashboardService.getCidFromAccountUser(rm.getCorpId(), ukl.getUserCode());
			String[] bal = DBProceduresLoyaltyEngine.getAccountBalancePoints(rm.getCorpId(),cid, ukl.getUserCode(), UtilAccess.rrn());
			map.put("balance", bal[1]);
			response.setData(map);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}