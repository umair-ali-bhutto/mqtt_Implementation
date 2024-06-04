package com.generic.ws.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.OtpLogging;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsValidateOTP")
public class WsValidateOTP implements Wisher{
	@Autowired
	OTPLoggingService oTPLoggingService;
	@Autowired
	UserLoginService userLoginService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		UserLogin userLogin = null;

		try {

			userLogin = userLoginService.validetUser(rm.getUserid(),rm.getCorpId());
			if (userLogin != null) {

				String otpPin = "1";
				otpPin = (String) rm.getAdditionalData().get("otpPin");
				// otpPin = rm.getOtp();
				OtpLogging otp = validateOtp(rm.getUserid(), otpPin);
				if (otp != null) {

					AgLogger.logInfo("VALIDATE ");
					response.setCode("0000");
					response.setMessage("SUCCESS");
					AgLogger.logInfo("USER FOUND");
				} else {
					response.setCode("0001");
					response.setMessage("INVALID OTP.");
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

	public OtpLogging validateOtp(String chemistId, String otp) {
		OtpLogging otpd = oTPLoggingService.validateOtp(otp, chemistId);
		return otpd;
	}

}
