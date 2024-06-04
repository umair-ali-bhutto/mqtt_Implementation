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
import com.ag.mportal.entity.ProvidersConfig;
import com.ag.mportal.services.ProvidersConfigService;

@Component("com.ag.generic.action.approve.WsUpdateDonation")
public class WsUpdateDonation implements WisherForApprover{
	@Autowired
	ProvidersConfigService configService;
	
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
			JSONObject onj = (JSONObject) onjArray.get(0);
			ProvidersConfig pm = configService.fetchAllRecordById(Long.parseLong(onj.get("id").toString()));
			if (pm != null) {
				
				
				
				pm.setAccountTitle(onj.get("accountTitle").toString());
				pm.setAccountNumber(onj.get("accountNumber").toString());
				pm.setMaxAmount(Float.parseFloat(onj.get("maxAmount").toString()));
				pm.setMinAmount(Float.parseFloat(onj.get("minAmount").toString()));
				pm.setTxnMid(onj.get("txnMid").toString());
				pm.setTxnTid(onj.get("txnTid").toString());
				//pm.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				pm.setMid("N/A");
				pm.setTid("N/A");
				pm.setImg(null);
				pm.setIsActive(Integer.parseInt(onj.get("isActive").toString()));
				pm.setIsFixedTid(0);
				pm.setSerialNumber("N/A");
				pm.setSortBy(0);
				pm.setTypeCode("01");
				pm.setTypeDescription("DONATION");
				pm.setDescription(onj.get("description").toString());
				pm.setMerchantName(onj.get("merchantName").toString());
				pm.setMerchantAdd1(onj.get("merchantAdd1").toString());
				pm.setMerchantAdd2(onj.get("merchantAdd2").toString());
				pm.setMerchantAdd3(onj.get("merchantAdd3").toString());
				pm.setMerchantAdd4(onj.get("merchantAdd4").toString());

				configService.update(pm);
				
					md.setStatus("APPROVED");
					md.setApprovedBy(Integer.parseInt(rm.getUserid()));
					md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
					md.setApproverRemarks(remarks);
					mdService.update(md);

				

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
