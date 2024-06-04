package com.generic.ws.classes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsAboutUsScreen")
public class WsAboutUsScreen implements Wisher {

	@Autowired
	private UtilService utilService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			response.setCode("0000");
			response.setMessage("SUCCESS");
			Map<Object, Object> mp = new HashMap<Object, Object>();
			mp.put("MWVERSION", AppProp.getProperty("mw.version"));
			if (AppProp.getProperty("mw.show.db.version").equalsIgnoreCase("Y")) {
				mp.put("DBINFORMATION", utilService.getDbDetails());
			} else {
				mp.put("DBINFORMATION", "N/A");
			}
			response.setData(mp);
		} catch (Exception ex) {

			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}