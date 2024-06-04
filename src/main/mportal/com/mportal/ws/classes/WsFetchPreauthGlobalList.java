package com.mportal.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.metro.model.GenericLovModel;
import com.ag.mportal.services.PreauthMasterService;

@Component("com.mportal.ws.classes.WsFetchPreauthGlobalList")
public class WsFetchPreauthGlobalList implements Wisher {

	@Autowired
	PreauthMasterService preauthMasterService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			List<GenericLovModel> lst = new ArrayList<GenericLovModel>();
			lst = preauthMasterService.FetchAllActiveConfig();

			if (lst.size() != 0) {
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("lst", lst);
				response.setData(o);
				response.setCode("0000");
				response.setMessage("Success.");
			} else {
				response.setCode("0001");
				response.setMessage("No Data Found.");
			}

		}

		catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}
