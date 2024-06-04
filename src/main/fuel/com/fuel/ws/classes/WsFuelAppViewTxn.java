package com.fuel.ws.classes;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.model.TxnModel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsFuelAppViewTxn")
public class WsFuelAppViewTxn implements Wisher {

	@Autowired
	UserLoginService userLoginService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {

		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		TxnModel txn = new TxnModel();

		String cardNumber = rm.getAdditionalData().get("cardNumber").toString();

		try {
			AgLogger.logInfo("card no: " + cardNumber);

			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
			String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "txn_detail", userModel.getUserId(),
					userModel.getUserCode(), cardNumber, userModel.getGroupCode(), null);

			if (responseProc.equals("9999")) {
				response.setCode("9991");
				response.setMessage("Something Went Wrong.");
			} else {

				FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
				if (mdl.getCode().equals("0000")) {

					response.setCode("0000");
					response.setMessage("SUCCESS");
					HashMap<Object, Object> obMap = new HashMap<Object, Object>();
					txn = new Gson().fromJson(new Gson().toJson(mdl.getData().get("txnDetails")), TxnModel.class);

					obMap.put("txnModel", txn);
					response.setData(obMap);

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
