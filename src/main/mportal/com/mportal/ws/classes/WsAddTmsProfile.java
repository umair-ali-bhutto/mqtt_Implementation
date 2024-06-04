package com.mportal.ws.classes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TmsParameters;
import com.ag.mportal.services.impl.TmsParametersServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.mportal.ws.classes.WsAddTmsProfile")
public class WsAddTmsProfile implements Wisher {

	@Autowired
	TmsParametersServiceImpl tmsParametersServiceImpl;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			Type tmsParametersType = new TypeToken<ArrayList<TmsParameters>>(){}.getType();
			List<TmsParameters> finProfile = (List<TmsParameters>) new Gson().fromJson(rm.getAdditionalData().get("lstTms").toString(),tmsParametersType);
			tmsParametersServiceImpl.insert(finProfile);
			
			response.setCode("0000");
			response.setMessage("Record Successfully Created");

			
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}