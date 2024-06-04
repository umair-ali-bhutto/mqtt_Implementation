package com.metro.ws.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProcedureMetro;
import com.ag.db.proc.MetroGetCardBalanceModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.metro.ws.classes.WsMetroGetCardBalance")

public class WsMetroGetCardBalance implements Wisher {

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		Map<Object, Object> reponseData = new HashMap<Object, Object>();

		try {
			Double cardsNum = Objects.isNull(rm.getAdditionalData().get("cardNo")) ? null
					: Double.parseDouble(rm.getAdditionalData().get("cardNo").toString());
			String cardType = Objects.isNull(rm.getAdditionalData().get("cardType")) ? null
					: rm.getAdditionalData().get("cardType").toString();

			String cardNumber = String.format("%.0f", cardsNum);
			cardNumber = StringUtils.leftPad(cardNumber, 9, "0");
			AgLogger.logInfo(cardNumber);

			if (cardNumber != null) {
				if (cardType != null) {
					MetroGetCardBalanceModel mtd = DBProcedureMetro.metroGetCardBalance(cardNumber, cardType);
					if (mtd.getVrsp_code().equals("0000")) {
						reponseData.put("status", mtd.getVstatus());
						reponseData.put("balance", mtd.getVbalance());
						reponseData.put("allowedStatus", mtd.getVallow_status());
						response.setCode("0000");
						response.setMessage("SUCCESS");
						response.setData(reponseData);
					} else {
						response.setCode("0001");
						response.setMessage(mtd.getVmessage());
					}

				} else {

					response.setCode("9999");
					response.setMessage("CARD TYPE REQUIRED");

				}

			} else {
				response.setCode("9999");
				response.setMessage("CARD NUMBER REQUIRED");

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("SERVICE ERROR");
		}

		return response;

	}
}
