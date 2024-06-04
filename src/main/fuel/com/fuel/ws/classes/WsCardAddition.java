package com.fuel.ws.classes;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.fuel.ws.classes.WsCardAddition")
public class WsCardAddition implements Wisher {

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			response.setCode("0000");
			response.setMessage("Success.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}