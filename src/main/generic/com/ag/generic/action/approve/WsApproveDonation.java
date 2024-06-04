package com.ag.generic.action.approve;

import java.sql.Timestamp;
import java.util.List;

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

@Component("com.ag.generic.action.approve.WsApproveDonation")
public class WsApproveDonation implements WisherForApprover {

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
			
			
				ProvidersConfig pc = new ProvidersConfig();
				String requestedData = md.getRequestedData();
				org.json.simple.JSONArray onjArray = (org.json.simple.JSONArray) JSONValue.parse(requestedData);
				JSONObject onj = (JSONObject) onjArray.get(0);

				
				pc.setAccountTitle(onj.get("accountTitle").toString());
				pc.setAccountNumber(onj.get("accountNumber").toString());
				pc.setMaxAmount(Float.parseFloat(onj.get("maxAmount").toString()));
				pc.setMinAmount(Float.parseFloat(onj.get("minAmount").toString()));
				pc.setTxnMid(onj.get("txnMid").toString());
				pc.setTxnTid(onj.get("txnTid").toString());
				pc.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				pc.setMid("N/A");
				pc.setTid("N/A");
				pc.setImg(null);
				pc.setIsActive(1);
				pc.setIsFixedTid(0);
				pc.setSerialNumber("N/A");
				pc.setSortBy(0);
				pc.setTypeCode("01");
				pc.setTypeDescription("DONATION");
				pc.setDescription(onj.get("description").toString());
				pc.setMerchantName(onj.get("merchantName").toString());
				pc.setMerchantAdd1(onj.get("merchantAdd1").toString());
				pc.setMerchantAdd2(onj.get("merchantAdd2").toString());
				pc.setMerchantAdd3(onj.get("merchantAdd3").toString());
				pc.setMerchantAdd4(onj.get("merchantAdd4").toString());

				long id = configService.save(pc);
				if (id != 0l) {
					md.setStatus("APPROVED");
					md.setApprovedBy(Integer.parseInt(rm.getUserid()));
					md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
					md.setApproverRemarks(remarks);
					mdService.update(md);

				}

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
