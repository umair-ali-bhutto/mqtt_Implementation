package com.ag.generic.action.approve;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.MerConfDetails;
import com.ag.mportal.entity.MerConfMaster;
import com.ag.mportal.services.MerConfDetailsService;
import com.ag.mportal.services.MerConfMasterService;

@Component("com.ag.generic.action.approve.WsOfflineSaleMerchantDataEdit")
public class WsOfflineSaleMerchantDataEdit implements WisherForApprover {

	@Autowired
	MerConfMasterService confMasterService;

	@Autowired
	MerConfDetailsService confDetailsService;

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
			String[] splittedData = requestedData.split("@@@@");

			System.out.println(requestedData);
			System.out.println(splittedData[0]);
			System.out.println(splittedData[1]);

			// Master object
			org.json.simple.JSONArray onjArray1 = (org.json.simple.JSONArray) JSONValue.parse(splittedData[0]);
			JSONObject onj1 = (JSONObject) onjArray1.get(0);

			// Details Object
			String jsonArrayString = splittedData[1];
			Gson gson = new Gson();
			TypeToken<List<MerConfDetails>> token = new TypeToken<List<MerConfDetails>>() {
			};
			List<MerConfDetails> detailsList = gson.fromJson(jsonArrayString, token.getType());

			System.out.println("MID " + onj1.get("mid").toString());
			System.out.println("TID " + onj1.get("tid").toString());

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			Date parsedDate = dateFormat.parse(onj1.get("entryDate").toString());
			long timeInMillis = parsedDate.getTime();
			Timestamp timestamp = new Timestamp(timeInMillis);

			MerConfMaster master = new MerConfMaster();
			master.setId(Long.parseLong(onj1.get("id").toString()));
			master.setMid(onj1.get("mid").toString());
			master.setTid(onj1.get("tid").toString());
			master.setType("OFFLINE_SALE");
			master.setIsActive(1);

			master.setEntryDate(timestamp);
			System.out.println(master.getEntryDate());
			System.out.println(onj1.get("entryBy").toString());
			master.setEntryBy(onj1.get("entryBy").toString());
			master.setUpdateDate(new Timestamp(new java.util.Date().getTime()));
			System.out.println(master.getUpdateDate());
			master.setUpdatedBy(rm.getUserid().toString());
			confMasterService.update(master);

			for (MerConfDetails details : detailsList) {
				System.out.println(details.getId());
				System.out.println(details.getName());
				System.out.println(details.getValue());
				confDetailsService.update(details);
			}

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
