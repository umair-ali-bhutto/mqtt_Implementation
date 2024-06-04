package com.ag.generic.action.approve;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
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
import com.ag.mportal.entity.MerConfDetails;
import com.ag.mportal.entity.MerConfMaster;
import com.ag.mportal.model.DonationBinRangesModel;
import com.ag.mportal.services.MerConfDetailsService;
import com.ag.mportal.services.MerConfMasterService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("com.ag.generic.action.approve.WsAddDonationBinRanges")
public class WsAddDonationBinRanges implements WisherForApprover {

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
			String minAmount = "";
			String maxAmount = "";
			String count = "";
			// String description = "";
			String binRanges = "";

			String remarks = (rm.getAdditionalData().containsKey("remarks"))
					? rm.getAdditionalData().get("remarks").toString()
					: null;

			String requestedData = md.getRequestedData();

			AgLogger.logInfo("REQUESTED DATA: " + requestedData);

			JSONArray onjArray1 = (JSONArray) JSONValue.parse(requestedData);
			JSONObject onj1 = (JSONObject) onjArray1.get(0);

			mid = onj1.get("mid").toString();
			tid = onj1.get("tid").toString();
			minAmount = onj1.get("minAmount").toString();
			maxAmount = onj1.get("maxAmount").toString();
			count = onj1.get("count").toString();
			// description = onj1.get("description").toString();

			MerConfMaster recordModel = confMasterService.fetchByMidTid(mid, tid, "DONATION");

			if (recordModel != null) {
				response.setCode("9999");
				response.setMessage("Record Already Exist With Same MID TID.");
			} else {

				Type type = new TypeToken<List<DonationBinRangesModel>>() {
				}.getType();
				List<DonationBinRangesModel> binsList = new Gson().fromJson(onj1.get("binRanges").toString(), type);

				for (DonationBinRangesModel bin : binsList) {
					binRanges += bin.getFromBin() + ":" + bin.getToBin() + ",";
				}

				binRanges = binRanges.substring(0, binRanges.length() - 1);

				MerConfMaster master = new MerConfMaster();
				master.setMid(mid);
				master.setTid(tid);
				master.setType("DONATION");
				master.setIsActive(1);
				master.setEntryDate(new Timestamp(new Date().getTime()));
				master.setEntryBy(rm.getUserid().toString());
				confMasterService.insert(master);
				long RecID = master.getId();

				MerConfDetails details = new MerConfDetails();
				details.setRecId(RecID);
				details.setName("DONATION_CARD_RANGES");
				details.setValue(binRanges);
				confDetailsService.insert(details);

				MerConfDetails details1 = new MerConfDetails();
				details1.setRecId(RecID);
				details1.setName("DONATION_COUNT");
				details1.setValue(count);
				confDetailsService.insert(details1);

				MerConfDetails details2 = new MerConfDetails();
				details2.setRecId(RecID);
				details2.setName("DONATION_MIN_AMOUNT");
				details2.setValue(minAmount);
				confDetailsService.insert(details2);

				MerConfDetails details3 = new MerConfDetails();
				details3.setRecId(RecID);
				details3.setName("DONATION_MAX_AMOUNT");
				details3.setValue(maxAmount);
				confDetailsService.insert(details3);

				md.setStatus("APPROVED");
				md.setApprovedBy(Integer.parseInt(rm.getUserid()));
				md.setApprovedOn(new Timestamp(new Date().getTime()));
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
