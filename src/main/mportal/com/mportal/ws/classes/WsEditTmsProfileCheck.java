package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TmsParameters;
import com.ag.mportal.services.impl.TmsParametersServiceImpl;

@Component("com.mportal.ws.classes.WsEditTmsProfileCheck")
public class WsEditTmsProfileCheck implements Wisher {

	@Autowired
	TmsParametersServiceImpl tmsParametersServiceImpl;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			List<TmsParameters> tmsParamsFin = tmsParametersServiceImpl.fetchByTid(
					rm.getAdditionalData().containsKey("tid") ? rm.getAdditionalData().get("tid").toString() : "N/A");
			
			if (tmsParamsFin.size() != 0) {
				response.setCode("0000");
				response.setMessage("SUCCESS");
				HashMap<Object, Object> i = new HashMap<Object, Object>();
				i.put("editParams", tmsParamsFin);
				response.setData(i);

			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");

				
				
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}