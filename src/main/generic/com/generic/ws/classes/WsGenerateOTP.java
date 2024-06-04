package com.generic.ws.classes;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.SendNotifciation;

@Component("com.generic.ws.classes.WsGenerateOTP")
public class WsGenerateOTP implements Wisher {
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	OTPLoggingService oTPLoggingService;
	@Autowired
	UtilService utilService;
	@Autowired
	SendNotifciation sendNotifciation;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		UserLogin userLogin = null;
		try {
			userLogin = userLoginService.validetUser(rm.getUserid(), rm.getCorpId());
			if (userLogin != null) {
				String otp = generateOtp(rm.getImei(), userLogin.getMsisdn(), rm.getUserid(), userLogin.getCorpId());
				AgLogger.logInfo(userLogin.getMsisdn() + " OTP Generated");
				createNotification(userLogin.getUserCode(), rm.getCorpId(), otp);
				response.setCode("0000");
				response.setMessage("SUCCESS.");

			} else {
				response.setCode("0001");
				response.setMessage("INVALID USER.");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo(rm.getUserid(), "EXCEPTION  :  " + this.getClass().getName() + ex);
			ex.printStackTrace();
		}

		return response;
	}

	public String generateOtp(String imei, String mobileNo, String chemistId, String corpId) {
		Random ran = new Random(System.currentTimeMillis());
		int random = ran.nextInt(999999);
		String o = String.format("%06d", random);
		oTPLoggingService.generateOtpWithCorpId(imei, mobileNo, chemistId, o, corpId, "MPORTAL");
		return o;

	}

	public void createNotification(String userCode, String corpId, String otp) {
		UserLogin user = userLoginService.validetUser(userCode, corpId);

		SendNotificationModel sdm = new SendNotificationModel();

		sdm.setPass("N/A");
		sdm.setUserName(user.getUserCode());
		sdm.setMerchantName(user.getUserName());
		sdm.setReciverId(user.getUserId());

		sdm.setClosedBy(null);
		sdm.setRequestComplDate(null);
		sdm.setClosureDate(null);
		sdm.setComplaintNum(null);
		sdm.setResolution(null);
		sdm.setAccountOpeningDate(null);
		sdm.setOtp(otp);

		sendNotifciation.doTask("999", "0001", "00007", sdm, user.getUserId());
	}

}
