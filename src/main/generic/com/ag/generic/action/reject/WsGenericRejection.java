package com.ag.generic.action.reject;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;

@Component("com.ag.generic.action.reject.WsGenericRejection")
public class WsGenericRejection implements WisherForApprover {
	
	@Autowired
	MakerCheckerDataService mdService;

	@Override
	public ResponseModel doProcess(RequestModel rm, MakerCheckerConfig mck, MakerCheckerData md) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String remarks = (rm.getAdditionalData().containsKey("remarks"))
					? rm.getAdditionalData().get("remarks").toString()
					: null;
			md.setStatus("REJECTED");
			md.setRejectedBy(Integer.parseInt(rm.getUserid()));
			md.setRejectedOn(new Timestamp(new java.util.Date().getTime()));
			md.setRejectionRemarsk(remarks);
			mdService.update(md);
			response.setCode("0000");
			response.setMessage("Case Rejected. ");
		} catch (Exception e) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
