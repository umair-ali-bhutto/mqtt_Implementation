package com.fuel.ws.classes;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ag.fuel.entity.MasFleetCoInfo;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;

@Component("com.fuel.ws.classes.WsAccountEditApprover")
public class WsAccountEditApprover implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			
			if(!Objects.isNull(rm.getAdditionalData()) && !Objects.isNull(rm.getAdditionalData().get("masFleetCoInfo"))) {
				Gson gson = new Gson();
				MasFleetCoInfo masFleetCoInfo = gson.fromJson(rm.getAdditionalData().get("masFleetCoInfo").toString(), MasFleetCoInfo.class);
				System.out.println("");
			}

			response.setCode("0000");
			response.setMessage("SUCCESS");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}
