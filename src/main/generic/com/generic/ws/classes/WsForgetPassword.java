package com.generic.ws.classes;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.OtpLogging;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.PasswordGenerator;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;

@Component("com.generic.ws.classes.WsForgetPassword")
public class WsForgetPassword implements Wisher {
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	OTPLoggingService oTPLoggingService;
	@Autowired
	SendNotifciation sendNotifciation;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String userId = rm.getUserid();
			String passwordnEW = PasswordGenerator.generateStrongPassword();
			UserLogin ukl = userLoginService.validetUser(rm.getUserid(), rm.getCorpId());
			if (ukl != null) {
				if (ukl.getUserType().equals("internal")) {
					String otpPin = "1";
					otpPin = (String) rm.getAdditionalData().get("otpPin");
					OtpLogging otp = validateOtp(userId, otpPin);
					if (otp != null) {
						ukl.setPassword(UtilAccess.md5Java(passwordnEW));
						ukl.setForceLogin(1);
						ukl.setTempPass(passwordnEW);
						AgLogger.logInfo(rm.getUserid(),
								"PASS RESET TO " + passwordnEW + " WITH USER " + ukl.getUserCode());
						userLoginService.updateUser(ukl);
						createNotification(ukl.getUserCode(), passwordnEW, rm.getCorpId());

						response.setCode("0000");
						response.setMessage("Password Successfully Changed.");
					} else {
						response.setCode("0001");
						response.setMessage("INVALID OTP.");
					}

				} else {
					String otpPin = "";
					otpPin = (String) rm.getAdditionalData().get("otpPin");
					OtpLogging otp = validateOtp(rm.getUserid(), otpPin);
					if (otp != null) {
						String[] k = doUpdatePassword(ukl.getUserCode(), passwordnEW);
						if (k[0].equals("0000")) {
							ukl.setTempPass(passwordnEW);
							ukl.setForceLogin(1);
							AgLogger.logInfo(rm.getUserid(),
									"PASS RESET TO " + passwordnEW + " WITH USER " + ukl.getUserCode());
							userLoginService.updateUser(ukl);
							createNotification(ukl.getUserCode(), passwordnEW, rm.getCorpId());
							response.setCode("0000");
							response.setMessage("Password Successfully Changed.");

						} else {
							response.setCode("0002");
							response.setMessage(k[0] + " " + k[1]);

						}

					} else {
						response.setCode("0001");
						response.setMessage("INVALID OTP.");
					}

				}
			} else {
				System.out.println("6");
				response.setCode("0001");
				response.setMessage("INVALID USER CRENDTIALS.");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo("EXCEPTION  :  " + this.getClass().getName() + ex);
			ex.printStackTrace();
		}

		return response;
	}

	public static String[] doUpdatePassword(String id, String pass) {

		String[] slm = new String[2];
		String url = AppProp.getProperty("ldap.url.change.pwd");
		url = url.replaceAll("@PARUSER", id);
		url = url.replaceAll("@PARPASS", pass);

		try {
			System.out.println(url);
			String sk = new HttpUtil().doGet(url);
			System.out.println(sk);
			Map<String, String> ms = UtilAccess.convertMap(sk);
			String kskl = ms.get("code");
			slm[1] = ms.get("{msg");

			slm[0] = kskl.replaceAll("}", "");

		} catch (Exception e) {
			AgLogger.logInfo(" EXCEPTION  " + e);
			slm[0] = "9999";
			slm[1] = e.getMessage();
		}
		return slm;

	}

	public void deactiveOtp(String chemistId) {
		oTPLoggingService.deactiveOtp(chemistId);
	}

	public OtpLogging validateOtp(String chemistId, String otp) {
		OtpLogging otpd = oTPLoggingService.validateOtp(otp, chemistId);
		return otpd;
	}

	public void createNotification(String userCode, String password, String corpId) {
		UserLogin user = userLoginService.validetUser(userCode, corpId);

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

}
