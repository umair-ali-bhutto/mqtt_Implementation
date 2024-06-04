package com.fuel.ws.classes;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.model.ChangePinServiceModel;
import com.ag.generic.entity.OtpLogging;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.SendNotifciation;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsFuelAppResetPin")
public class WsFuelAppResetPin implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UtilService utilService;

	@Autowired
	SendNotifciation sendNotifciation;

	@Autowired
	OTPLoggingService loggingService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		try {
			String cardNo = rm.getAdditionalData().get("cardNo").toString();
			String otp = rm.getAdditionalData().get("otp").toString();
			String mobileNumber = rm.getAdditionalData().get("mobileNumber").toString();

			OtpLogging otpRes = loggingService.validateOtp(otp, mobileNumber);

			if (otpRes != null) {
				String pinChangeURL = AppProp.getProperty("changePinUrl") + "card=" + cardNo + "&pin=0000&t=002";
				AgLogger.logInfo(pinChangeURL);
				String respData = new HttpUtil().doGet(pinChangeURL);
				AgLogger.logInfo("Response Data: " + respData);

				ChangePinServiceModel tempModel = new Gson().fromJson(respData, ChangePinServiceModel.class);
				if (null != tempModel.getResponce() && tempModel.getResponce().equals("0000")) {

					UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

					SendNotificationModel sdm = new SendNotificationModel();
					sdm.setAccountOpeningDate("N/A");
					sdm.setClosedBy("N/A");
					sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
					sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
					sdm.setComplaintNum("N/A");
					sdm.setMerchantName(ukl.getFirstName() + " " + ukl.getLastName());
					sdm.setPass("N/A");
					sdm.setResolution("N/A");
					sdm.setUserName(ukl.getUserCode());
					sdm.setReciverId(ukl.getUserId());
					AgLogger.logInfo("SMS/EMAIL NOT SENT FOR NOW");
					// sendNotifciation.doTask("001", "0002", "00001", sdm, ukl.getUserId());

					utilService.doSendAppNotificationOnly(Integer.parseInt(rm.getUserid()),
							AppProp.getProperty("reset.pin.notif"), AppProp.getProperty("reset.pin.notif"));

					response.setCode("0000");
					response.setMessage("Reset Pin Successful.");
				} else {
					response.setCode("1111");
					response.setMessage("Could Not Reset Pin.");
				}
			} else {
				response.setCode("9991");
				response.setMessage("Invalid OTP.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something went wrong.");
		}

		return response;
	}

}
