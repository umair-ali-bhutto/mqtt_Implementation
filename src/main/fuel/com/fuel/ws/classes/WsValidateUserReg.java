package com.fuel.ws.classes;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.SendNotifciation;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsValidateUserReg")
public class WsValidateUserReg implements Wisher {

	@Autowired
	OTPLoggingService wsGenerateOTP;
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	SendNotifciation sendNotifciation;

	@Override
	public ResponseModel doProcess(RequestModel rm) {

		HashMap<Object, Object> obj = new HashMap<Object, Object>();
		ResponseModel response = new ResponseModel();
		try {
			AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
			AgLogger.logInfo(rm.getUserid(), " CLASS CALLED USER ID: " + rm.getAdditionalData().get("user_id"));
			AgLogger.logInfo(rm.getUserid(), " CLASS CALLED USER REG ID: " + rm.getAdditionalData().get("user_reg_id"));

			String userRegId = rm.getAdditionalData().get("user_reg_id").toString();
			String userId = rm.getAdditionalData().get("user_id").toString();

			String mPattern = "^0([3]{1})([0123456]{1})([0-9]{8})$";
			boolean validRegId = Pattern.matches(mPattern, userRegId);

			if (validRegId) {
				String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "registration", 0, userRegId,
						null, 0, null);

				if (responseProc.equals("9999")) {
					response.setCode("9991");
					response.setMessage("Something Went Wrong.");
				} else {

					FuelProcModel mdl = new Gson().fromJson(responseProc, FuelProcModel.class);
					if (!mdl.getCode().equals("0000")) {
						response.setCode(mdl.getCode());
						response.setMessage(mdl.getMessage());
					} else {
						String otp = generateOtp(rm.getImei(), userRegId, userId, rm.getCorpId());

						AgLogger.logInfo(userRegId + " OTP is:*****");
						createNotification(userRegId, rm.getCorpId(), otp, userId);

						obj.put("user_reg_id", userRegId);
						response.setCode("0000");
						response.setMessage("Success.");
						response.setData(obj);
					}

				}

			} else {

				response.setCode("0002");
				response.setMessage("Invalid User Registration Id.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

	public String generateOtp(String imei, String mobileNo, String chemistId, String corpId) {
		Random ran = new Random(System.currentTimeMillis());
		int random = ran.nextInt(999999);
		String o = String.format("%06d", random);
		wsGenerateOTP.generateOtpWithCorpId(imei, mobileNo, chemistId, o, corpId, "FUEL");
		return o;

	}

	public void createNotification(String mobile, String corpId, String otp, String userId) {

		SendNotificationModel sdm = new SendNotificationModel();

		sdm.setPass("N/A");
		sdm.setUserName("N/A");
		sdm.setMerchantName("N/A");
		sdm.setReciverId(Integer.parseInt(userId));

		sdm.setClosedBy(null);
		sdm.setRequestComplDate(null);
		sdm.setClosureDate(null);
		sdm.setComplaintNum(null);
		sdm.setResolution(null);
		sdm.setAccountOpeningDate(null);
		sdm.setOtp(otp);

		sendNotifciation.doTaskUnRegUser("999", "0001", "00008", sdm, mobile, "N/A", Integer.parseInt(userId),corpId);

	}
}
