package com.loy.adm.ws.classes;


import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.service.CorporateMasterService;

@Component("com.loy.adm.ws.classes.WsCorporateView")
public class WsCorporateView implements Wisher {
	@Autowired
	CorporateMasterService corporateMasterService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "FETCH":
				response = fetchRequest(rm);
				break;
			case "INIT":
				response = initRequest(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			List<CorporateMaster> lst = corporateMasterService.fetchAll();
			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("corporates", lst);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Corporates Found.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpid = null;
			if (!rm.getAdditionalData().get("corpid").toString().equals("null")) {
				corpid = rm.getAdditionalData().get("corpid").toString();
			}

			String status = (rm.getAdditionalData().containsKey("statusfilter"))
					? rm.getAdditionalData().get("statusfilter").toString()
					: null;

			List<CorporateMaster> lst = corporateMasterService.fetchByFilter(corpid, status);

			if (lst.size() != 0) {
				HashMap<Object, Object> okj = new HashMap<Object, Object>();
				okj.put("lst", lst);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
				response.setData(okj);
			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}
}
