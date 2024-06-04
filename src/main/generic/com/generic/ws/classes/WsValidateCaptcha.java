package com.generic.ws.classes;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.Captcha;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.CaptchaService;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.SendNotifciation;

@Component("com.generic.ws.classes.WsValidateCaptcha")
public class WsValidateCaptcha implements Wisher {

	@Autowired
	CaptchaService captchaService;
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	OTPLoggingService oTPLoggingService;
	@Autowired
	UtilService utilService;
	@Autowired
	SendNotifciation sendNotifciation;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {

			String reset = rm.getAdditionalData().get("reset").toString();
			String mobileNumber = rm.getAdditionalData().containsKey("mobileNumber")
					? rm.getAdditionalData().get("mobileNumber").toString()
					: null;
			String UserCode = rm.getUserid();
			String corpId = rm.getCorpId();

			UserLogin user = new UserLogin();
			// AgLogger.logDebug("1", UserCode + "|" + rm.getCorpId());
			if (reset.equals("true")) {
				resetOTP(rm.getImei(), mobileNumber, UserCode, corpId);
				response.setCode("0000");
				response.setMessage("New OTP Generated.");
			} else {
				boolean isCaptcha = false;
				if (rm.getChannel().equalsIgnoreCase("portal")) {
					isCaptcha = true;
				}
				if (isCaptcha) {
					// Captcha Validation here
					Captcha cpt = captchaService.validateCaptcha(rm.getAdditionalData().containsKey("cptId")
							? Integer.parseInt(rm.getAdditionalData().get("cptId").toString())
							: 0, rm.getImei());

					String captAns = rm.getAdditionalData().containsKey("captAns")
							? rm.getAdditionalData().get("captAns").toString()
							: "N/A";

					if (cpt != null) {
						if (cpt.getCaptchaAns().equals(captAns)) {
							user = userLoginService.validateUserByNumber(UserCode, rm.getCorpId(), mobileNumber);
							if (user != null) {
								cpt.setIsActive(0);
								captchaService.updateCaptcha(cpt);
								otp(rm.getImei(), user.getMsisdn(), rm.getUserid(), rm.getCorpId());
								response.setCode("0000");
								response.setMessage("SUCCESS.");
							} else {
								cpt.setIsActive(0);
								captchaService.updateCaptcha(cpt);
								// AgLogger.logInfo(UserCode, "Portal User Not Found in DB..");
								response.setCode("0001");
								response.setMessage("Cannot Log In, Invalid Credentials.");
							}
						} else {
							cpt.setIsActive(0);
							captchaService.updateCaptcha(cpt);
							// AgLogger.logInfo(UserCode, "Portal User Captcha Mismatched......");
							response.setCode("0002");
							response.setMessage("Cannot Log In, Invalid Credentials.");
						}
					} else {
						// AgLogger.logInfo(UserCode, "Portal User Catpcha Is Null....");
						response.setCode("0003");
						response.setMessage("Cannot Log In, Invalid Credentials.");
					}
				}
			}

		} catch (Exception ex) {
			response.setCode("9995");
			response.setMessage("Something Went Wrong, Please try again.");

			// AgLogger.logerror(getClass(), "doProcess ...EXCEPTION : " + ex, ex);
			ex.printStackTrace();
		}

		return response;
	}

	public void resetOTP(String imei, String number, String userid, String corpid) {
		oTPLoggingService.deactiveOtp(userid);
		otp(imei, number, userid, corpid);
	}

	public void otp(String imei, String number, String userId, String corpId) {
		String otp = generateOtp(imei, number, userId, corpId);
		createNotification(userId, corpId, otp);
	}

	public String generateOtp(String imei, String mobileNo, String chemistId, String corpId) {
		Random ran = new Random(System.currentTimeMillis());
		int random = ran.nextInt(999999);
		String o = String.format("%06d", random);
		AgLogger.logInfo("OTP Generated");
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
