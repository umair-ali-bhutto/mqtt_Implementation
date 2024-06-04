package com.ag.generic.action.approve;

import java.sql.Timestamp;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

@Component("com.ag.generic.action.approve.WsMarketSegmentThresholdAdd")
public class WsMarketSegmentThresholdAdd implements WisherForApprover {

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

			String mid = "";
			String tid = "";

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

			mid = onj1.get("mid").toString();
			tid = onj1.get("tid").toString();

			System.out.println("MID " + mid);
			System.out.println("TID " + tid);

			MerConfMaster recordModel = confMasterService.fetchByMidTid(mid, tid, "R1_R2");

			if (recordModel != null) {
				response.setCode("9999");
				response.setMessage("Record Already Exist With Same MID TID.");
			} else {

				MerConfMaster master = new MerConfMaster();
				master.setMid(mid);
				master.setTid(tid);
				master.setType("R1_R2");
				master.setIsActive(1);
				master.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				master.setEntryBy(rm.getUserid().toString());
				confMasterService.insert(master);
				long RecID = master.getId();

				for (MerConfDetails details : detailsList) {
					details.setRecId(RecID);
					confDetailsService.insert(details);
				}

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
