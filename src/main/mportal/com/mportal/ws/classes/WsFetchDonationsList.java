package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

import com.ag.mportal.entity.ProvidersConfig;
import com.ag.mportal.services.ProvidersConfigService;

@Component("com.mportal.ws.classes.WsFetchDonationsList")
public class WsFetchDonationsList implements Wisher{
	
	@Autowired
	ProvidersConfigService configService;
	
	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			
			String mid = rm.getAdditionalData().get("mid") == null ? null
					: rm.getAdditionalData().get("mid").toString();

			String tid = rm.getAdditionalData().get("tid") == null ? null
					: rm.getAdditionalData().get("tid").toString();
			
			String donationAccount = rm.getAdditionalData().get("donationAccount") == null ? null
					: rm.getAdditionalData().get("donationAccount").toString();
			
			String status = rm.getAdditionalData().get("statusfilter") == null ? null
					: rm.getAdditionalData().get("statusfilter").toString();
			
			
			
			if (mid != null && !mid.equals("null")) {
				mid = StringUtils.leftPad(mid, 15, "0");
			}
			if (tid != null && !tid.equals("null")) {
				tid = StringUtils.leftPad(tid, 8, "0");
			}
		
			List<ProvidersConfig> lst= configService.fetchAllRecordByFilter(mid, tid, donationAccount,status);
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
