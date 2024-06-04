package com.metro.ws.classes;

import java.util.HashMap;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProcedureMetro;
import com.ag.db.proc.GenericDbProcModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.metro.services.MetroBatchSummaryService;

@Component("com.metro.ws.classes.WsMetroValidateTopup")
public class WsMetroValidateTopup implements Wisher {

	@Autowired
	MetroBatchSummaryService metroBatchSummary;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			Double cardsNumFrom = Objects.isNull(rm.getAdditionalData().get("cardFrom")) ? null
					: Double.parseDouble(rm.getAdditionalData().get("cardFrom").toString());
			Double cardsNumTo = Objects.isNull(rm.getAdditionalData().get("cardTo")) ? null
					: Double.parseDouble(rm.getAdditionalData().get("cardTo").toString());
			String cardFrom = String.format("%.0f", cardsNumFrom);
			cardFrom = StringUtils.leftPad(cardFrom, 9, "0");
			String cardTo = String.format("%.0f", cardsNumTo);
			cardTo = StringUtils.leftPad(cardTo, 9, "0");

			String customerNum = Objects.isNull(rm.getAdditionalData().get("customerNum")) ? null
					: rm.getAdditionalData().get("customerNum").toString();

			Double topUpAmnt = Objects.isNull(rm.getAdditionalData().get("topUpAmnt")) ? null
					: Double.parseDouble(rm.getAdditionalData().get("topUpAmnt").toString());

			Double totalCards = Objects.isNull(rm.getAdditionalData().get("totalCards")) ? null
					: Double.parseDouble(rm.getAdditionalData().get("totalCards").toString());

			String remarks = Objects.isNull(rm.getAdditionalData().get("remarks")) ? null
					: rm.getAdditionalData().get("remarks").toString();

			String cardType = Objects.isNull(rm.getAdditionalData().get("cardType")) ? null
					: rm.getAdditionalData().get("cardType").toString();

			String shelfLife = Objects.isNull(rm.getAdditionalData().get("shelfLife")) ? null
					: rm.getAdditionalData().get("shelfLife").toString();

			AgLogger.logInfo("shelfLife:" + shelfLife);

			String tCards = String.format("%.0f", totalCards);
			String tAmnt = String.format("%.0f", topUpAmnt);

			if (cardFrom != null && cardTo != null && customerNum != null && remarks != null && topUpAmnt != null
					&& totalCards != null && cardType != null) {

				String[] prefromValidation = prefromValidation(cardType, cardFrom, cardTo, tAmnt, rm.getUserid(),
						tCards, shelfLife);

				if (prefromValidation[0].equals("0000")) {
					String[] execute = executeCalculation(cardsNumFrom, cardsNumTo, totalCards, customerNum, topUpAmnt,
							remarks, shelfLife);

					if (execute[0].equals("0000")) {
						response.setCode("0000");
						response.setMessage("SUCCESS.");
						HashMap<Object, Object> ojh = new HashMap<Object, Object>();
						ojh.put("rejectedCards", execute[2]);
						ojh.put("successCards", execute[3]);
						ojh.put("expiredOn", execute[4]);
						ojh.put("totalTopUpAmnt", execute[5]);
						ojh.put("totalCard", execute[6]);
						ojh.put("cardFrom", cardFrom);
						ojh.put("cardTo", cardTo);
						response.setData(ojh);
					} else {
						response.setCode(execute[0]);
						response.setMessage(execute[1]);
					}
				} else {
					response.setCode("0002");
					response.setMessage(prefromValidation[1]);
				}

			} else {
				response.setCode("0001");
				response.setMessage("Validation Failed, Please Enter Proper Data.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("SERVICE ERROR");
		}

		return response;

	}

	public String[] prefromValidation(String vbin, String vcardFrom, String V_CARD_TO, String v_top_up, String V_MEM_ID,
			String v_tot_cards, String V_SHELF_LIFE) {
		String[] s = new String[2];

		GenericDbProcModel md = DBProcedureMetro.metroTopupValidation(vbin, vcardFrom, V_CARD_TO, v_top_up, V_MEM_ID,
				v_tot_cards, V_SHELF_LIFE);
		s[0] = md.getVrsp_code();
		s[1] = md.getVmessage();
		return s;
	}

	public String[] executeCalculation(Double cardFrom, Double cardTo, Double totalCards, String customerNum,
			Double topUpAmnt, String remarks, String shelfLife) {
		String[] s = new String[7];
		String code = "0000";
		String message = "";
		String rejectedCards = "";
		String successCards = "";
		String expiredOn = "";
		String totalTopUpAmnt = "";
		String totalCard = "";

		// Perform TOtalTOpAMount
		try {
			totalTopUpAmnt = String.format("%.0f", (topUpAmnt * totalCards));
			totalCard = String.format("%.0f", totalCards);
		} catch (Exception e) {
			e.printStackTrace();
			code = "00006";
			message += " Unable to calculate Total Cards.";
		}

		// Perfrom Expires On
		expiredOn = metroBatchSummary.getExpiryDate(shelfLife);

		// Perform SuccessCards;
		successCards = "0";

		// Perform Reject Cards;
		rejectedCards = "0";

		s[0] = code;
		s[1] = message;
		s[2] = rejectedCards;
		s[3] = successCards;
		s[4] = expiredOn;
		s[5] = totalTopUpAmnt;
		s[6] = totalCard;
		return s;
	}

}
