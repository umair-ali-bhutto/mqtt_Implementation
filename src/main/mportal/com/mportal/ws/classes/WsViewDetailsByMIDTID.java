package com.mportal.ws.classes;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.mportal.ws.classes.WsViewDetailsByMIDTID")
public class WsViewDetailsByMIDTID implements Wisher{
	@Autowired
	UserLoginService userLoginService;
	

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String mid = rm.getAdditionalData().get("mid") == null ? null: rm.getAdditionalData().get("mid").toString();
			String tid = rm.getAdditionalData().get("tid") == null ? null: rm.getAdditionalData().get("tid").toString();

			String[] k = userLoginService.fetchMerchantTerminalUpd(mid, tid);
			if (k[0].equals("0000")) {

				response.setCode("0000");
				response.setMessage("SUCCESS");
				HashMap<Object, Object> mp = new HashMap<Object, Object>();
				mp.put("maker", k[3]);
				mp.put("serialNum", k[2]);
				mp.put("model", k[1]);
				response.setData(mp);
			} else {

				response.setCode("0001");
				response.setMessage("No Terminal Found.");
			}
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo("EXCEPTION  :  " + this.getClass().getName() + ex);
			ex.printStackTrace();
		}

		return response;
	}

}
