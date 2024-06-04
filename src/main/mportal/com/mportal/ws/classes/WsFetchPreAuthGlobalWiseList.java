package com.mportal.ws.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.PreauthMaster;
import com.ag.mportal.services.PreauthMasterService;

@Component("com.mportal.ws.classes.WsFetchPreAuthGlobalWiseList")
public class WsFetchPreAuthGlobalWiseList implements Wisher {
	
	@Autowired
	PreauthMasterService masterService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
//			SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
//			Date dateFrom = rm.getAdditionalData().get("dateFrom") == null ? null
//					: sd.parse(rm.getAdditionalData().get("dateFrom").toString());
//
//			Date dateTo = rm.getAdditionalData().get("dateTo") == null ? null
//					: sd.parse(rm.getAdditionalData().get("dateTo").toString());
			String cardEntry = rm.getAdditionalData().get("cardEntry") == null ? null
					: rm.getAdditionalData().get("cardEntry").toString();

			String status = rm.getAdditionalData().get("status") == null ? null
					: rm.getAdditionalData().get("status").toString();
			
			List<PreauthMaster> lst= masterService.fetchData(cardEntry, status);
			if (lst.size() != 0) {
				response.setCode("0000");
				response.setMessage("Success.");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("lst", lst);
				response.setData(obj);
			} else {
				response.setCode("0002");
				response.setMessage("No Record Found.");
			}
			
		}

		catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}
