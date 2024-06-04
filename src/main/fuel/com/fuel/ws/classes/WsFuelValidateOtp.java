package com.fuel.ws.classes;

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

@Component("com.fuel.ws.classes.WsFuelValidateOtp")
public class WsFuelValidateOtp implements Wisher {
	@Autowired
	OTPLoggingService oTPLoggingService;
	@Autowired
	UserLoginService userLoginService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String otpPin = "1";
			otpPin = (String) rm.getAdditionalData().get("otpPin");
			String mobileNo = rm.getAdditionalData().get("user_reg_id").toString();
			AgLogger.logInfo(mobileNo);
			AgLogger.logInfo(otpPin);
			OtpLogging otp = validateOtp(mobileNo,otpPin,rm.getCorpId(),"FUEL");
			
			if (otp != null) {

				AgLogger.logInfo("VALIDATE ");
				response.setCode("0000");
				response.setMessage("SUCCESS");
			} else {
				response.setCode("0001");
				response.setMessage("INVALID OTP.");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo("EXCEPTION  :  " + this.getClass().getName() + ex);
			ex.printStackTrace();
		}

		return response;
	}

	public OtpLogging validateOtp(String mobileNumber, String otp,String corpId,String productName) {
		AgLogger.logInfo(mobileNumber);
		AgLogger.logInfo(otp);
		OtpLogging otpd = oTPLoggingService.validateOtpFuel(otp,mobileNumber,corpId,productName);
		return otpd;
	}

}
