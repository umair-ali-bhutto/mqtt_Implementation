package com.ag.generic.action.approve;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.services.MerConfDetailsService;
import com.ag.mportal.services.MerConfMasterService;

@Component("com.ag.generic.action.approve.WsCardLimitEdit")
public class WsCardLimitEdit implements WisherForApprover {

	@Autowired
	MakerCheckerDataService mdService;
	
	@Autowired
	UserLoginService userLoginService;

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
			
			UserLogin userModel = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

			String[] responseProc = AGFuelUtil.fuelAppProcess(rm.getCorpId(), "CARD_LIMIT_UPDATE", "EDIT",
					userModel.getUserId(), userModel.getUserCode(), userModel.getGroupCode(), finalData);

			if (responseProc[0].equals("00")) {
				md.setStatus("APPROVED");
				md.setApprovedBy(Integer.parseInt(rm.getUserid()));
				md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
				md.setApproverRemarks(remarks);
				mdService.update(md);

				response.setCode("0000");
				response.setMessage(responseProc[1]);
			} else {

				md.setStatus("REJECTED");
				md.setRejectedBy(Integer.parseInt(rm.getUserid()));
				md.setRejectedOn(new Timestamp(new java.util.Date().getTime()));
				md.setApproverRemarks(responseProc[1]);
				mdService.update(md);

				response.setCode("9999");
				response.setMessage(responseProc[1]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
