package com.fuel.ws.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.model.ChangePinServiceModel;
import com.ag.generic.entity.OtpLogging;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HSMUtil;
import com.ag.generic.util.HttpUtil;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsFuelAppChangePin")
public class WsFuelAppChangePin implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UtilService utilService;
	@Autowired
	OTPLoggingService oTPLoggingService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());

		try {
			String cardNo = rm.getAdditionalData().get("cardNo").toString();
			String currentPin = rm.getAdditionalData().get("currentPin").toString();
			String otpPin = rm.getAdditionalData().get("otpPin").toString();
			String newPin = rm.getAdditionalData().get("newPin").toString();
			String confirmPin = rm.getAdditionalData().get("confirmPin").toString();
			String mobileNo = rm.getAdditionalData().get("mobileNo").toString();

			String gj = String.valueOf(System.currentTimeMillis()).substring(2, 13);

			String sk = HSMUtil.doSend(cardNo, currentPin);
			System.out.println(" " + cardNo + " HSM CALL " + sk);
			String trimedText = sk.substring(4, sk.length());
			String res = "0000";
			String resMessage = "-";

			OtpLogging otp = validateOtp(mobileNo, otpPin);

			if (otp == null) {
				AgLogger.logInfo("Invalid Otp.");
				res = "0002";
				resMessage = "INVALID OTP.";
			} else {
				if (trimedText.startsWith("DF00")) {
					String hsmcode = trimedText.substring(4, trimedText.length());

					String tempPinDb = "";
					UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
					String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "offset",
							userModel.getUserId(), userModel.getUserCode(), cardNo, userModel.getGroupCode(), null);

					FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
					tempPinDb = mdl.getData().get("offset").toString();

					AgLogger.logInfo(hsmcode + "|" + tempPinDb);
					if (hsmcode.equals(tempPinDb)) {

						res = "0000";
						resMessage = "PIN Matched";

						String pinChangeURL = AppProp.getProperty("changePinUrl") + "card=" + cardNo + "&pin=" + newPin
								+ "&t=001";
						String respData = new HttpUtil().doGet(pinChangeURL);
						AgLogger.logInfo(respData);
						String code = respData;

						// {"responce":"0000","message":"SUCCESS","rrn":"91059016057"}
						ChangePinServiceModel tempModel = new Gson().fromJson(respData, ChangePinServiceModel.class);
						if (null != tempModel.getResponce() && tempModel.getResponce().equals("0000")) {

							res = "0000";
							resMessage = "Pin Changes Successfully.";
							utilService.doSendAppNotificationOnly(Integer.parseInt(rm.getUserid()),
									AppProp.getProperty("change.pin.notif"), AppProp.getProperty("change.pin.notif"));

						} else {

							res = "1111";
							resMessage = "Could Not Change Pin";
						}

					} else {
						res = "0001";
						resMessage = "PIN NOT Matched";
					}

				} else {
					res = "1111";
					resMessage = "INVALID HSM CALL";
				}
			}

			response.setCode(res);
			response.setMessage(resMessage);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Unable to change PIN, Please contact administrator.");
		}

		return response;
	}

	public OtpLogging validateOtp(String chemistId, String otp) {
		OtpLogging otpd = oTPLoggingService.validateOtp(otp, chemistId);
		return otpd;
	}

}
