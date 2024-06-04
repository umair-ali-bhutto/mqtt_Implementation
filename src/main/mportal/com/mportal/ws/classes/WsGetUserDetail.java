package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;

@Component("com.mportal.ws.classes.WsGetUserDetail")
public class WsGetUserDetail implements Wisher {
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	UserSettingService userSettingService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String province;
			String mdrOnUs;
			String mdrOffUs;
			String userCode = rm.getAdditionalData().get("userCode") == null ? null
					: rm.getAdditionalData().get("userCode").toString();
			String action = rm.getAdditionalData().get("action") == null ? null
					: rm.getAdditionalData().get("action").toString();

			switch (action) {
			case "add":
				UserLogin usl = userLoginService.validetUser(userCode,rm.getCorpId());
				if (usl != null) {
					response = UtilAccess.generateResponse("0000", "USER ALREADY EXISTS");
				} else {
					response = UtilAccess.generateResponse("0001", "In-Valid User ID");
				}
				break;
			case "view":
				UserLogin uslK = userLoginService.validetUserWithoutStatus(userCode,rm.getCorpId());
				if (uslK != null) {
					HashMap<Object, Object> mp = new HashMap<Object, Object>();
					UserSetting userSetting = null;
					userSetting = userSettingService.fetchSettingByIdName(uslK.getUserId(), "MDR_ON_US");
					if (!Objects.isNull(userSetting)) {
						mdrOnUs = userSetting.getPropValue();
					} else {
						mdrOnUs = "";
					}
					userSetting = null;
					userSetting = userSettingService.fetchSettingByIdName(uslK.getUserId(), "MDR_OFF_US");
					if (!Objects.isNull(userSetting)) {
						mdrOffUs = userSetting.getPropValue();
					} else {
						mdrOffUs = "";
					}
					userSetting = null;
					userSetting = userSettingService.fetchSettingByIdName(uslK.getUserId(), "PROVINCE");
					if (!Objects.isNull(userSetting)) {
						province = userSetting.getPropValue();
					}else {
						province = "";
					}
					mp.put("user", uslK);
					mp.put("mdrOnUs", mdrOnUs);
					mp.put("mdrOffUs", mdrOffUs);
					mp.put("province", province);
					response = UtilAccess.generateResponse("0000", "USER EXISTS",mp);
				} else {
					response = UtilAccess.generateResponse("0001", "In-Valid User ID");
				}
				break;
			default:
				response = UtilAccess.generateResponse("9999", "INVALID ACTION.");
				AgLogger.logInfo(rm.getUserid(), "INVALID ACTION.");
			}
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
	
}