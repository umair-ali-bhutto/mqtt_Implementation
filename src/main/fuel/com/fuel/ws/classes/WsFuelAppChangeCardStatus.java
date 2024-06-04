package com.fuel.ws.classes;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsFuelAppChangeCardStatus")
public class WsFuelAppChangeCardStatus implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UtilService utilService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());

		String cardStatus = rm.getAdditionalData().get("cardStatus").toString();
		String cardNumber = rm.getAdditionalData().get("cardNumber").toString();

		AgLogger.logInfo("card status 1: " + cardStatus);
		try {
			AgLogger.logInfo("card status: " + cardStatus);

			String cardStatusFin = "";

			if (cardStatus.equalsIgnoreCase("ACTIVE")) {
				cardStatusFin = "Active";

			} else if (cardStatus.equalsIgnoreCase("INACTIVE")) {
				cardStatusFin = "InActive";
			}

			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
			String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "change_card_status",
					userModel.getUserId(), userModel.getUserCode(), cardNumber, userModel.getGroupCode(),
					cardStatusFin);

			if (responseProc.equals("9999")) {
				response.setCode("9991");
				response.setMessage("Something Went Wrong.");
			} else {

				FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
				HashMap<Object, Object> obj = new HashMap<Object, Object>();

				if (mdl.getCode().equals("0000")) {

					utilService.doSendAppNotificationOnly(Integer.parseInt(rm.getUserid()),
							AppProp.getProperty("card.status.change.notif"),
							AppProp.getProperty("card.status.change.notif"));

					obj.put("cardStatus", mdl.getData().get("cardStatus"));
					response.setData(obj);
					response.setCode(mdl.getCode());
					response.setMessage(mdl.getMessage());
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
