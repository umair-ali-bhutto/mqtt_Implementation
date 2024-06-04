package com.mportal.ws.classes;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.mportal.ws.classes.WsDonationAuthenticateMerchant")
public class WsDonationAuthenticateMerchant implements Wisher {

	@Autowired
	UtilService service;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String mid = rm.getAdditionalData().get("mid") == null ? null
					: rm.getAdditionalData().get("mid").toString();
			String tid = rm.getAdditionalData().get("tid") == null ? null
					: rm.getAdditionalData().get("tid").toString();

			if (mid == null || tid == null) {
				response.setCode("0003");
				response.setMessage("Mid Tid Should Not Be Empty.");
			} else {

				String[] check = service.fetchMerchantTerminalUpd(mid, tid);

				if (check!=null) {
					if (check[0].equals("0000")) {
						String[] fetch = service.fetchMerchantUpd(mid);

						if (fetch[0].equals("0000")) {
							response.setCode("0000");
							response.setMessage("Success.");
							HashMap<Object, Object> obj = new HashMap<Object, Object>();
							obj.put("merchantName", fetch[1]);
							obj.put("merchantAddress1", fetch[2]);
							obj.put("merchantAddress2", fetch[3]);
							obj.put("merchantAddress3", fetch[4]);
							obj.put("merchantAddress4", fetch[5]);

							response.setData(obj);
						} else {
							response.setCode("0001");
							response.setMessage("Invalid Mid Tid.");
						}
					} else {
						response.setCode("0003");
						response.setMessage("Invalid Mid Tid.");
					}

				} else {
					response.setCode("0002");
					response.setMessage("Invalid Mid Tid.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}
