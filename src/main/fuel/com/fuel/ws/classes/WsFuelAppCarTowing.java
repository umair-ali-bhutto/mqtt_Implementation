package com.fuel.ws.classes;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.fuel.ws.classes.WsFuelAppCarTowing")
public class WsFuelAppCarTowing implements Wisher {

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED Additional Data: " + rm.getAdditionalData());

		try {
			
			response.setCode("0000");
			response.setMessage("SUCCESS");
			
			
		}catch(Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something went wrong.");
		}

		
		return response;
	}

}
