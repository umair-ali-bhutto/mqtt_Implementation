package com.mportal.ws.classes;

import java.sql.Timestamp;

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

@Component("com.mportal.ws.classes.WsUserSetting")
public class WsUserSetting implements Wisher {

	@Autowired
	UserLoginService userLoginService;
	@Autowired
	UserSettingService userSettingService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		UserLogin userLogin = null;
		try {
			userLogin = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
			if (userLogin != null) {

				UserSetting usk = userSettingService.fetchSettingByIdName(userLogin.getUserId(),
						UtilAccess.userSettingsDefaultTid);
				String tid = (String) rm.getAdditionalData().get("tid");
				if ((tid.length()) != 0) {
					if (usk != null) {
						usk.setPropValue(tid);
						userSettingService.updateProp(usk);
					} else {
						UserSetting newSetting = new UserSetting();
						newSetting.setPropName(UtilAccess.userSettingsDefaultTid);
						newSetting.setPropValue(tid);
						newSetting.setIsActive(1);
						newSetting.setEntryDate(new Timestamp(new java.util.Date().getTime()));
						newSetting.setUserLoginId(userLogin.getUserId());
						userSettingService.insertProp(newSetting);
					}

					response.setCode("0000");
					response.setMessage("SUCCESS.");

				} else {
					response.setCode("0001");
					response.setMessage("INVALID TID.");
				}
			} else {
				response.setCode("0001");
				response.setMessage("INVALID USER.");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo("EXCEPTION  :  " + this.getClass().getName() + ex);
			ex.printStackTrace();
		}

		return response;
	}

}
