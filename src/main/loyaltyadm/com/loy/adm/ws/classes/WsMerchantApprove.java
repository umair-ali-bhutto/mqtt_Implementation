package com.loy.adm.ws.classes;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.util.AgLogger;

@Component("com.loy.adm.ws.classes.WsMerchantApprove")
public class WsMerchantApprove {

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			response.setCode("0000");
			response.setMessage("SUCCESS");
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}
