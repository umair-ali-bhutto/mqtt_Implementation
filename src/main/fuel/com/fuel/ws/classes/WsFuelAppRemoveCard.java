package com.fuel.ws.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsFuelAppRemoveCard")
public class WsFuelAppRemoveCard implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());

		try {
			String cardNo = rm.getAdditionalData().get("cardNo").toString();
			String mobileNo = rm.getAdditionalData().get("mobileNo").toString();
	//		String otpPin = rm.getAdditionalData().get("otpPin").toString();
			AgLogger.logInfo("card no: " + cardNo);
			AgLogger.logInfo("mobile no: " + mobileNo);

			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
			String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "delink_card", userModel.getUserId(),
					userModel.getUserCode(), cardNo, userModel.getGroupCode(), null);

			if (responseProc.equals("9999")) {
				response.setCode("9991");
				response.setMessage("Something Went Wrong.");
			} else {

				FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
				if (mdl.getCode().equals("0000")) {
					response.setCode("0000");
					response.setMessage("Card Delink Successfully.");
				} else {
					response.setCode(mdl.getCode());
					response.setMessage(mdl.getMessage());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something went wrong.");
		}

		return response;
	}

}
