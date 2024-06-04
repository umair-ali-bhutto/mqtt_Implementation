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

@Component("com.ag.generic.action.approve.WsPreauthMerchantParamsAdd")
public class WsPreauthMerchantParamsAdd implements WisherForApprover {

	@Autowired
	MakerCheckerDataService mdService;

	@Autowired
	PreauthMerchantService preauthMerchantService;

	@Override
	public ResponseModel doProcess(RequestModel rm, MakerCheckerConfig mck, MakerCheckerData md) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {

			
			
			String remarks = (rm.getAdditionalData().containsKey("remarks"))
					? rm.getAdditionalData().get("remarks").toString()
					: null;
			
			String id=(rm.getAdditionalData().containsKey("preauthid"))
					? rm.getAdditionalData().get("preauthid").toString()
					: null;

			Double dValue = Double.parseDouble(id);
			
			PreauthMerchant pm = new PreauthMerchant();
			String requestedData = md.getRequestedData();
			org.json.simple.JSONArray onjArray = (org.json.simple.JSONArray) JSONValue.parse(requestedData);
			JSONObject onj =  (JSONObject) onjArray.get(0);
			PreauthMerchantPK pkId = new PreauthMerchantPK();
			PreauthMerchant pam= preauthMerchantService.fetchByIDActive(dValue.intValue());
			
			
			
			if(pam != null)
			{
				response.setCode("9999");
				response.setMessage("Record Already Present On Id.");
			}
			else
			{
				pkId.setMid(onj.get("merchantId").toString());
				pkId.setPaid(Long.parseLong(onj.get("preAuthId").toString()));
				pm.setActive("Y");
				pm.setCorpid("00000");
				pm.setCrBy(rm.getUserid()+"");
				pm.setCrOn(new Timestamp(new java.util.Date().getTime()));
				pm.setSourceRef(UtilAccess.rrn());
				pm.setId(pkId);
				
				
				
				boolean inserted = preauthMerchantService.insert(pm);

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
			
			

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
