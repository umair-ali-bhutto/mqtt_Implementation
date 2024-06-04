package com.ag.generic.util;

import java.util.HashMap;
import java.util.Map;

import com.ag.generic.model.RequestModel;
import com.ag.mportal.model.TapNPayModel;
import com.ag.mportal.util.TapNPayUtil;
import com.google.gson.Gson;

public class MaskedUtil {
	


	public static String getRequest(String request) {
		try {
			RequestModel rm = new Gson().fromJson(request, RequestModel.class);
			if (rm.getMessage().equals("TAP_N_PAY") && rm.getAdditionalData().containsKey("request")) {
				TapNPayModel tap = new Gson().fromJson(rm.getAdditionalData().get("request").toString(),
						TapNPayModel.class);
				
				if(tap.getCardNumber()!=null) {
					String tempCard = tap.getCardNumber();
					tap.setCardNumber(TapNPayUtil.mask(tempCard));
				}
				Map<Object, Object> additionalDataD = new HashMap<Object, Object>();
				additionalDataD.put("request", tap);
				rm.setAdditionalData(additionalDataD);
				return new Gson().toJson(rm);
			} else {
				return request;
			}

		} catch (Exception e) {
			return request;
		}
	}

}
