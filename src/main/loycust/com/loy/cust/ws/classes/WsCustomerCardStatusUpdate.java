package com.loy.cust.ws.classes;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProceduresLoyaltyEngine;
import com.ag.db.proc.ProcPerfromTransactionModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.adm.service.LoyaltyErrorMappingService;

@Component("com.loy.cust.ws.classes.WsCustomerCardStatusUpdate")
public class WsCustomerCardStatusUpdate implements Wisher {

	@Autowired
	LoyaltyErrorMappingService errorMappingService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String corpId = rm.getCorpId();
			String cid = Objects.isNull(rm.getAdditionalData().get("cid")) ? null
					: rm.getAdditionalData().get("cid").toString().trim();
			String userId = Objects.isNull(rm.getAdditionalData().get("userId")) ? null
					: rm.getAdditionalData().get("userId").toString().trim();

			String Newstatus = AppProp.getProperty("loy.cust.new.card.status." + corpId);

			AgLogger.logInfo("New Status: " + Newstatus);

			if (!Newstatus.equals("N/A")) {
				ProcPerfromTransactionModel responseProc = DBProceduresLoyaltyEngine.processProcUpdateUserStatus(corpId,
						cid, userId, Newstatus, UtilAccess.rrn());
				if (responseProc.getpRespCode().equals("0000")) {
					response.setCode("0000");
					response.setMessage("Card Status Updated Successfully.");
				} else {
					response.setCode(responseProc.getpRespCode());
					String message = errorMappingService.fetchErrorMessage(responseProc.getpRespCode());
					response.setMessage(message);
				}
			} else {
				response.setCode("0002");
				response.setMessage("New Status Cannot Be N/A.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;

	}
}
