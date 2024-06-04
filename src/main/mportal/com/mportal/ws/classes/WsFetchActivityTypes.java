package com.mportal.ws.classes;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.mportal.ws.classes.WsFetchActivityTypes")
public class WsFetchActivityTypes implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String lovs = AppProp.getProperty("fetch.activity.types.lov");

			HashMap<Object, Object> obj = new HashMap<Object, Object>();
			obj.put("lovs", lovs);

			response.setCode("0000");
			response.setMessage("Success.");
			response.setData(obj);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}
