package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.metro.model.GenericLovModel;
import com.ag.mportal.entity.PreauthMerchant;
import com.ag.mportal.services.PreauthMasterService;
import com.ag.mportal.services.PreauthMerchantService;

@Component("com.mportal.ws.classes.WsFetchPreAuthMidWiseList")
public class WsFetchPreAuthMidWiseList implements Wisher {
	
	@Autowired
	PreauthMerchantService preauthMerchantService;
	
	@Autowired
	PreauthMasterService preauthMasterService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String merchantId = rm.getAdditionalData().get("merchantId") == null ? null
					: rm.getAdditionalData().get("merchantId").toString();
			String preAuthId = rm.getAdditionalData().get("preAuthId") == null ? null
					: rm.getAdditionalData().get("preAuthId").toString();
			
			List<PreauthMerchant> lst = preauthMerchantService.fetchData(merchantId, preAuthId);
			AgLogger.logInfo(lst.size()+"...");
			if (lst.size() != 0) {
				List<GenericLovModel> lstMdl = preauthMasterService.FetchAllConfig();
				response.setCode("0000");
				response.setMessage("Success.");
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("lst", lst);
				obj.put("pMasterList", lstMdl);
				response.setData(obj);
				}
			else {
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
