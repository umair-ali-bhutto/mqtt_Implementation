package com.mportal.ws.classes;

import java.util.HashMap;

import org.main.qr.DecodeEmvQr;
import org.main.qr.EmvQrModel;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.mportal.ws.classes.WsSbpEmvQrDecryptor")
public class WsSbpEmvQrDecryptor implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String Qr = (String) rm.getAdditionalData().get("desc");
			AgLogger.logInfo("QR REQUEST RECIEVED @@@@ " + Qr);

			EmvQrModel mod = new DecodeEmvQr().getGeneratedQr(Qr);
			AgLogger.logInfo("Point Of Initiation Method: " + mod.getPointOfInitiationMethod());
			HashMap<Object, Object> obj = new HashMap<Object, Object>();
			if (mod.getPointOfInitiationMethod().equals("12")) {
				obj.put("txnAmount", mod.getTransactionAmount());
				response.setCode("0001");
				response.setMessage("Do not Enter Amount.");
			} else {
				response.setCode("0002");
				response.setMessage("Please Enter Amount.");
			}
			obj.put("merchantName", mod.getMerchantName());
			response.setData(obj);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}