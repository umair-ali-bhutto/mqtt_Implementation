package com.loy.adm.ws.classes;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.loy.adm.ws.classes.WsViewCustomerProfile")
public class WsViewCustomerProfile implements Wisher {
	

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			HashMap<Object, Object> map = new HashMap<Object, Object>();
			response.setCode("0000");
			response.setMessage("SUCCESS");
			response.setData(null);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}