package com.fuel.ws.classes;

import java.util.HashMap;

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

@Component("com.fuel.ws.classes.WsFuelAppFetchCardDetails")
public class WsFuelAppFetchCardDetails implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());

		String cardNumber = rm.getAdditionalData().get("cardNumber").toString();

		try {
			AgLogger.logInfo("card no: " + cardNumber);

			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
			String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "allow_status_change",
					userModel.getUserId(), userModel.getUserCode(), cardNumber, userModel.getGroupCode(), null);

			if (responseProc.equals("9999")) {
				response.setCode("9991");
				response.setMessage("Something Went Wrong.");
			} else {

				FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);

				HashMap<Object, Object> obj = new HashMap<Object, Object>();

				obj.put("status", mdl.getData().get("status"));
				obj.put("cardNumber", mdl.getData().get("cardNumber"));
				obj.put("validDate", mdl.getData().get("validDate"));
				obj.put("expDate", mdl.getData().get("expDate"));
				obj.put("cardHolder", mdl.getData().get("cardHolder"));
				obj.put("allowChange", mdl.getData().get("allowChange"));
				obj.put("status", mdl.getData().get("status"));

				response.setData(obj);
				response.setCode(mdl.getCode());
				response.setMessage(mdl.getMessage());

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something went wrong.");
		}

		return response;
	}

}
