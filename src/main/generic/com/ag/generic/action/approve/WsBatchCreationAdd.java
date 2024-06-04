package com.ag.generic.action.approve;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.services.MerConfDetailsService;
import com.ag.mportal.services.MerConfMasterService;

@Component("com.ag.generic.action.approve.WsBatchCreationAdd")
public class WsBatchCreationAdd implements WisherForApprover {

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

			String requestedData = md.getRequestedData();
			String finalData = new AGFuelUtil().parseData(requestedData,true);
			AgLogger.logInfo("REQUESTED DATA: " + finalData);			
			AgLogger.logInfo("REMARKS: " + remarks);
			
			md.setStatus("APPROVED");
			md.setApprovedBy(Integer.parseInt(rm.getUserid()));
			md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
			md.setApproverRemarks(remarks);
			mdService.update(md);

			response.setCode("0000");
			response.setMessage("Record Approved.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
