package com.generic.ws.classes;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsCheckConfiguration")
public class WsCheckConfiguration implements Wisher {
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			
			HashMap<Object, Object> mp = new HashMap<Object, Object>();
			String appVersion = AppProp.getProperty("app.version");
			
			if(!Objects.isNull(appVersion) && !appVersion.isEmpty()){
				mp.put("config", appVersion);
				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(mp);
			}else {
				response.setCode("0009");
				response.setMessage("FAILED");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			ex.printStackTrace();
		}

		return response;
	}

}
