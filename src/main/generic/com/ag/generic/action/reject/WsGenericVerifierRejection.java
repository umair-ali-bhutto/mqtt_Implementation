package com.ag.generic.action.reject;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.Wisher;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;

@Component("com.ag.generic.action.reject.WsGenericVerifierRejection")
public class WsGenericVerifierRejection implements Wisher {
	
	@Autowired
	MakerCheckerDataService mdService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String remarks = (rm.getAdditionalData().containsKey("remarks"))
					? rm.getAdditionalData().get("remarks").toString()
					: null;
			String id = (rm.getAdditionalData().containsKey("id"))
					? rm.getAdditionalData().get("id").toString()
					: null;
			MakerCheckerData md = mdService.fetchByRecID(Long.parseLong(id));
			md.setStatus("REJECTED_FROM_VERIFIER");
			md.setRejectedBy(Integer.parseInt(rm.getUserid()));
			md.setRejectedOn(new Timestamp(new java.util.Date().getTime()));
			md.setRejectionRemarsk(remarks);
			mdService.update(md);
			response.setCode("0000");
			response.setMessage("Case Rejected.");
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
