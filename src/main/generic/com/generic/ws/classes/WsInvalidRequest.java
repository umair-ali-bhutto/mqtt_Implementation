package com.generic.ws.classes;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;


@Component("com.generic.ws.classes.WsInvalidRequest")
public class WsInvalidRequest implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			response.setCode("9999");
			response.setMessage("INVALID REQUEST TYPE EXECUTED.");
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo(rm.getUserid(), "EXCEPTION  :  " + this.getClass().getName() + ex);
			ex.printStackTrace();
		}

		return response;
	}

}
