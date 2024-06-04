package com.metro.ws.classes;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProcedureMetro;
import com.ag.db.proc.GenericDbProcModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.metro.ws.classes.WsMetroUpdateCardStatus")
public class WsMetroUpdateCardStatus implements Wisher{

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			
			Double cardsNum = Objects.isNull(rm.getAdditionalData().get("cardNo")) ? null
					: Double.parseDouble(rm.getAdditionalData().get("cardNo").toString());
			
			String cardNumber = String.format("%.0f", cardsNum);
			cardNumber = StringUtils.leftPad(cardNumber, 9, "0");
			String newStatus = Objects.isNull(rm.getAdditionalData().get("newStatus")) ? null
					: rm.getAdditionalData().get("newStatus").toString();

			String oldStatus = Objects.isNull(rm.getAdditionalData().get("oldStatus")) ? null
					: rm.getAdditionalData().get("oldStatus").toString();

			String remarks = Objects.isNull(rm.getAdditionalData().get("remarks")) ? null
					: rm.getAdditionalData().get("remarks").toString();

			String cardType = Objects.isNull(rm.getAdditionalData().get("cardType")) ? null
					: rm.getAdditionalData().get("cardType").toString();
			GenericDbProcModel mtd = DBProcedureMetro.metroChangeCardStatus(cardNumber, cardType, rm.getUserid() + "",
					oldStatus, newStatus, remarks, "");
			if (mtd.getVrsp_code().equals("0000")) {
				response.setCode("0000");
				response.setMessage(mtd.getVmessage());
			} else {
				response.setCode("0001");
				response.setMessage(mtd.getVmessage());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("SERVICE ERROR");
		}

		return response;

	}
}
