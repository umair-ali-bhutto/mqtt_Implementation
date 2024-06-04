package com.fuel.ws.classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.model.FuelStationModel;
import com.ag.fuel.model.KeyValueModel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.fuel.ws.classes.WsFuelAppFuelStations")
public class WsFuelAppFuelStations implements Wisher {
	
	@Autowired
	UserLoginService userLoginService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
	
		HashMap<Object, Object> obj = new HashMap<Object, Object>();
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());
		
		try {
			
			
			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
			String responseProc = DBProceduresFuel.fuelAppProcess(rm.getCorpId(), "fuel_stations", userModel.getUserId(),
					userModel.getUserCode(), null, userModel.getGroupCode(), null);

			if (responseProc.equals("9999")) {
				response.setCode("9991");
				response.setMessage("Something Went Wrong.");
			} else {
				FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
				if (mdl.getCode().equals("0000")) {
					ArrayList<FuelStationModel> fuelStationList = new ArrayList<FuelStationModel>();
					
					Type listcardList = new TypeToken<List<FuelStationModel>>() {
					}.getType();
					fuelStationList = new Gson().fromJson(new Gson().toJson(mdl.getData().get("fuelStations")), listcardList);

					obj.put("lst", fuelStationList);
					response.setData(obj);
					response.setCode("0000");
					response.setMessage("SUCCESS");

					
					response.setCode("0000");
					response.setMessage("Card Added Successfully.");
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
