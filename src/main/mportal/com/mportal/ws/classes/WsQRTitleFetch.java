package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.Map;

import org.main.qr.DecodeEmvQr;
import org.main.qr.EmvQrModel;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.mportal.ws.classes.WsQRTitleFetch")
public class WsQRTitleFetch implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String Qr = (String) rm.getAdditionalData().get("qr");
			String[] val = validateQR(Qr);

			if (val[0].equals("0000")) {
				EmvQrModel mod = new DecodeEmvQr().getGeneratedQr(Qr);
				System.out.println(mod);
				System.out.println(mod.getMid());

				response.setCode(val[0]);
				response.setMessage(val[1]);

				Map<Object, Object> mp = new HashMap<Object, Object>();
				mp.put(val[2], mod.getMerchantName());
				mp.put(val[3], mod.getMerchantCity());
				mp.put(val[4], mod.getCustomPan());
				mp.put(val[5], mod.getMid());
				mp.put(val[6], mod.getTid());

				response.setData(mp);

			} else {
				response.setCode("0001");
				response.setMessage("Invalid Data");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");

			ex.printStackTrace();
		}
		return response;

	}

	public static String[] validateQR(String QR) {
		String[] res = new String[7];
		res[0] = "0000";
		res[1] = "Success";
		res[2] = "MNAME";
		res[3] = "MCITY";
		res[4] = "PAN";
		res[5] = "MID";
		res[6] = "TID";

		return res;

	}

}
