package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.MerConfMaster;
import com.ag.mportal.model.ViewOfflineSaleModel;
import com.ag.mportal.services.MerConfMasterService;

@Component("com.mportal.ws.classes.WsFetchOfflineSaleConfiguration")
public class WsFetchOfflineSaleConfiguration implements Wisher {

	@Autowired
	MerConfMasterService masterService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String mid = rm.getAdditionalData().get("mid") == null ? null
					: rm.getAdditionalData().get("mid").toString();

			String tid = rm.getAdditionalData().get("tid") == null ? null
					: rm.getAdditionalData().get("tid").toString();

			AgLogger.logInfo("MID " + mid + " TID" + tid);

			List<ViewOfflineSaleModel> lst = masterService.fetchData(mid, tid);
			
			
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
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

}
