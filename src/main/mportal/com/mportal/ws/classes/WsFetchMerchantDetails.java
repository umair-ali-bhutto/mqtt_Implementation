package com.mportal.ws.classes;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;

@Component("com.mportal.ws.classes.WsFetchMerchantDetails")
public class WsFetchMerchantDetails implements Wisher{

	@Autowired
	UserLoginService userLoginService;
	@Autowired
	UtilService utilService;
	
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String userid = rm.getUserid();
			String tid = rm.getAdditionalData().get("tid").toString();

			String mid = (userLoginService.validetUser(userid,rm.getCorpId())).getMid();
			String[] k = utilService.fetchMerchantTerminalUpd(mid, tid);
			if (k[0].equals("0000")) {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				map.put("maker", k[3]);
				map.put("serial", k[2]);
				map.put("model", k[1]);

				response.setCode("0000");
				response.setMessage("SUCCESS");
				response.setData(map);
			} else {
				response.setCode("0001");
				response.setMessage("NO TERMINAL FOUND.");
			}
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}