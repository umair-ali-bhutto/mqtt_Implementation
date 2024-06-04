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

@Component("com.fuel.ws.classes.WsFuelAppCardDetails")
public class WsFuelAppCardDetails implements Wisher {
	
	@Autowired
	UserLoginService userLoginService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		
		HashMap<Object, Object> obj = new HashMap<Object, Object>();
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());

		try {
			
			String mobNumber = rm.getAdditionalData().get("mobileNo").toString();
			String cardNumber = rm.getAdditionalData().get("cardNumber").toString();
			
			
			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
			String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "get_card_details", userModel.getUserId(),
					userModel.getUserCode(), cardNumber, userModel.getGroupCode(), null);

			if (responseProc.equals("9999")) {
				response.setCode("9991");
				response.setMessage("Something Went Wrong.");
			} else {

				FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
				if (mdl.getCode().equals("0000")) {
					response.setCode("0000");
					response.setMessage("Success");
					obj.put("accountNumber",mdl.getData().get("accountNumber").toString());
				    obj.put("accountName",mdl.getData().get("accountName").toString());
				    obj.put("cardStatus",mdl.getData().get("cardStatus").toString());
				    obj.put("cardType",mdl.getData().get("cardType").toString());
				    obj.put("mobileNo",mdl.getData().get("mobileNo").toString());
				    obj.put("email",mdl.getData().get("email").toString());
				    obj.put("product",mdl.getData().get("product").toString());
				    obj.put("limitType",mdl.getData().get("limitType").toString());
				    obj.put("dailyTxnFrequency",mdl.getData().get("dailyTxnFrequency").toString());
				    obj.put("weeklyTxnFrequency",mdl.getData().get("weeklyTxnFrequency").toString());
				    obj.put("perTxnLimit",mdl.getData().get("perTxnLimit").toString());
				    obj.put("dailyLimit",mdl.getData().get("dailyLimit").toString());
				    obj.put("weeklyLimit",mdl.getData().get("weeklyLimit").toString());
				    obj.put("monthlyLimit",mdl.getData().get("monthlyLimit").toString());
				    obj.put("nonFuelMonthlyLimit",mdl.getData().get("nonFuelMonthlyLimit").toString());
				    obj.put("cardNumber",mdl.getData().get("cardNumber").toString());
				    obj.put("validDate", mdl.getData().get("validDate").toString());
				    obj.put("expDate", mdl.getData().get("expDate").toString());
				    obj.put("cardHolder", mdl.getData().get("cardHolder").toString());
					
					response.setData(obj);

				} else {
					response.setCode(mdl.getCode());
					response.setMessage(mdl.getMessage());
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something went wrong.");
		}

		
		return response;
	}

}
