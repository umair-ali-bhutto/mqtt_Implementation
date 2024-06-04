package com.mportal.ws.classes;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;

@Component("com.mportal.ws.classes.WsFetchMerchantDetailsWithoutTID")
public class WsFetchMerchantDetailsWithoutTID implements Wisher {

	@Autowired
	UserLoginService userLoginService;
	@Autowired
	UtilService utilService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		String maker = "maker";
		String serialNum = "serialNum";
		String model = "model";
		String message = "message";
		try {
			String mid = null;
			try {
				mid = (rm.getAdditionalData().containsKey("mid")) ? rm.getAdditionalData().get("mid").toString() : null;
			} catch (Exception e) {

			}
			if (mid != null) {
				String[] k = utilService.fetchMerchantTerminalUpd(mid);
				if (k[0].equals("0000")) {
					response.setCode("0000");
					response.setMessage("SUCCESS.");
					HashMap<Object, Object> mapResult = new HashMap<Object, Object>();
					mapResult.put(maker, k[3]);
					mapResult.put(serialNum, k[2]);
					mapResult.put(model, k[1]);
					mapResult.put(message, "");
					response.setData(mapResult);
				} else {
					response.setCode("0002");
					response.setMessage("No Record Found On MID.");

				}

			} else {
				response.setCode("0002");
				response.setMessage("INVALID MID.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}