package com.generic.ws.classes;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.PasswordGenerator;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;

@Component("com.generic.ws.classes.WsResetPassword")
public class WsResetPassword implements Wisher {

	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	SendNotifciation sendNotifciation; 

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String password = PasswordGenerator.generateStrongPassword();
			String userCode = rm.getAdditionalData().get("userCode").toString();

			if (Objects.isNull(userCode) || userCode.isEmpty()) {
				response.setCode("00001");
				response.setMessage("Enter Valid User Id.");
				return response;
			}
			UserLogin user = userLoginService.validetUserWithoutStatus(userCode,rm.getCorpId());
			if (user != null) {
				if (user.getUserType().equals("internal")) {
					user.setPassword(UtilAccess.md5Java(password));
					user.setForceLogin(1);
					user.setTempPass(password);
					AgLogger.logInfo(rm.getUserid(), "PASS RESET TO " + password + " WITH USER " + user.getUserCode());
					userLoginService.updateUser(user);
					createNotification(userCode, password,rm.getCorpId());

					response.setCode("0000");
					response.setMessage("Password Successfully Changed.");

				} else {
					String[] k = doUpdatePassword(userCode, password);
					if (k[0].equals("0000")) {
						user.setTempPass(password);
						user.setForceLogin(1);
						AgLogger.logInfo(rm.getUserid(), "PASS RESET TO " + password + " WITH USER " + user.getUserCode());
						userLoginService.updateUser(user);
						createNotification(userCode, password,rm.getCorpId());
						response.setCode("0000");
						response.setMessage("Password Successfully Changed.");

					} else {
						response.setCode("0002");
						response.setMessage(k[0] + " " + k[1]);

					}

				}
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public void createNotification(String userCode, String password,String corpId) {
		UserLogin user = userLoginService.validetUser(userCode,corpId);

		SendNotificationModel sdm = new SendNotificationModel();

		sdm.setPass(user.getTempPass());
		sdm.setUserName(user.getUserCode());
		sdm.setMerchantName(user.getUserName());
		sdm.setReciverId(user.getUserId());

		sdm.setClosedBy(null);
		sdm.setRequestComplDate(null);
		sdm.setClosureDate(null);
		sdm.setComplaintNum(null);
		sdm.setResolution(null);
		sdm.setAccountOpeningDate(null);

		sendNotifciation.doTask("999", "0001", "00004", sdm, user.getUserId());
	}

	public String[] doUpdatePassword(String id, String pass) {

		String[] slm = new String[2];
		String url = AppProp.getProperty("ldap.url.change.pwd");
		url = url.replaceAll("@PARUSER", id);
		url = url.replaceAll("@PARPASS", pass);

		try {
			System.out.println(url);
			String sk = new HttpUtil().doGet(url);
			System.out.println(sk + "......");
			Map<String, String> ms = UtilAccess.convertMap(sk);
			String kskl = ms.get("code");
			slm[1] = ms.get("{msg");
			slm[0] = kskl.replaceAll("}", "");

		} catch (Exception e) {
			e.printStackTrace();
			slm[0] = "9999";
			slm[1] = e.getMessage();
		}
		return slm;

	}

}