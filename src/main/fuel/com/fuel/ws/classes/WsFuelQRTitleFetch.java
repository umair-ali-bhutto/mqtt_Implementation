package com.fuel.ws.classes;

import java.util.HashMap;
import java.util.Map;

import org.main.qr.DecodeEmvQr;
import org.main.qr.EmvQrModel;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.fuel.ws.classes.WsFuelQRTitleFetch")
public class WsFuelQRTitleFetch implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String Qr = (String) rm.getAdditionalData().get("qr");

			EmvQrModel mod = new DecodeEmvQr().getGeneratedQr(Qr);

			Map<Object, Object> mp = new HashMap<Object, Object>();
			mp.put("merchantName", mod.getMerchantName());
			mp.put("mid", mod.getMid());
			mp.put("tid", mod.getTid());
			mp.put("serialNumber", mod.getSerialNumber());
			mp.put("amount", mod.getTransactionAmount());
			mp.put("productName", mod.getMerchantCity().split("\\|")[7]);
			mp.put("productId", mod.getMerchantCity().split("\\|")[0]);
			mp.put("invoiceNumber", mod.getMerchantCity().split("\\|")[3]);

			response.setCode("0000");
			response.setMessage("Success.");

			response.setData(mp);

		} catch (Exception e) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			e.printStackTrace();
		}
		return response;
	}

}
