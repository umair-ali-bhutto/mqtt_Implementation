package com.loy.adm.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.AwardPoints;
import com.ag.loy.adm.model.AwardUtil;
import com.ag.loy.adm.service.AwardPointsService;

@Component("com.loy.adm.ws.classes.WsAwardView")
public class WsAwardView implements Wisher {

	@Autowired
	AwardPointsService awardPointsService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		//rm.setCorpId("00003");
		try {
			String id = "0";
			id = (rm.getAdditionalData().containsKey("id")) ? rm.getAdditionalData().get("id").toString() : "0";
			String corpId = (rm.getAdditionalData().containsKey("corpId")) ? rm.getAdditionalData().get("corpId").toString() : "N/A";
			id = AwardUtil.getLeadingZero(id);
			AgLogger.logDebug(rm.getUserid(), id);
			ArrayList<AwardPoints> apts = awardPointsService.fetchRecordById(id, corpId);
			if (apts.size() != 0) {
				response.setCode("0000");
				response.setMessage("SUCCESS");
				HashMap<Object, Object> rk = new HashMap<Object, Object>();
				rk.put("awards", apts);
				response.setData(rk);

			} else {
				response.setCode("0001");
				response.setMessage("No Award Points Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
}