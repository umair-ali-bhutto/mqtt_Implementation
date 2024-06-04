package com.ag.generic.action.approve;

import java.sql.Timestamp;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.PreauthMerchant;
import com.ag.mportal.entity.PreauthMerchantPK;
import com.ag.mportal.services.PreauthMerchantService;

@Component("com.ag.generic.action.approve.WsPreauthMerchantParamsUpdate")
public class WsPreauthMerchantParamsUpdate implements WisherForApprover {

	@Autowired
	PreauthMerchantService preauthMerchantService;
	
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
			org.json.simple.JSONArray onjArray = (org.json.simple.JSONArray) JSONValue.parse(requestedData);
			JSONObject onj =  (JSONObject) onjArray.get(0);
			
			PreauthMerchant pm = preauthMerchantService.fetchByID(Long.parseLong(onj.get("paid").toString()));
//			PreauthMerchantPK pkId = new PreauthMerchantPK();
			if(pm!=null) {
				
				
//				pkId.setMid(onj.get("merchantId").toString());
//				pkId.setPaid(Long.parseLong(onj.get("preAuthId").toString()));
//				pm.setCorpid("00000");
//				pm.setCrBy(rm.getUserid()+"");
//				pm.setCrOn(new Timestamp(new java.util.Date().getTime()));
//				pm.setSourceRef(UtilAccess.rrn());
//				pm.setId(pkId);
				
				
				
				
				pm.setActive(onj.get("active").toString());
				pm.setUpdBy(rm.getUserid()+"");
				pm.setUpdOn(new Timestamp(new java.util.Date().getTime()));
		

				
				
				boolean inserted = preauthMerchantService.update(pm);

				if (inserted) {
					md.setStatus("APPROVED");
					md.setApprovedBy(Integer.parseInt(rm.getUserid()));
					md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
					md.setApproverRemarks(remarks);
					mdService.update(md);

				}
				response.setCode("0000");
				response.setMessage("Record Approved.");
				
			}
			else {
				response.setCode("0001");
				response.setMessage("No Record Found on ID.");

			}
			

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
