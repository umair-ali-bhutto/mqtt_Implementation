package com.ag.generic.action.approve;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.ag.mportal.entity.BuildControlConfig;
import com.ag.mportal.entity.MerConfDetails;
import com.ag.mportal.entity.MerConfMaster;
import com.ag.mportal.services.BuildControlConfigService;
import com.ag.mportal.services.MerConfDetailsService;
import com.ag.mportal.services.MerConfMasterService;
import com.ag.mportal.services.impl.BuildControlConfigServiceImpl;

@Component("com.ag.generic.action.approve.WsBuildControllerEdit")
public class WsBuildControllerEdit implements WisherForApprover {
	@Autowired
	BuildControlConfigServiceImpl buildControlConfigServiceImpl;

	@Autowired
	BuildControlConfigService buildControlConfigService;
	
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
			String model = "";
			String selectedSuperUser = "";
			String selectedSystem = "";
			String selectedUser = "";

			String remarks = (rm.getAdditionalData().containsKey("remarks"))
					? rm.getAdditionalData().get("remarks").toString()
					: null;

			String requestedData = md.getRequestedData();

			System.out.println("For EDIT " + requestedData);

			org.json.simple.JSONArray onjArray1 = (org.json.simple.JSONArray) JSONValue.parse(requestedData);
			JSONObject onj1 = (JSONObject) onjArray1.get(0);

			mid = onj1.get("mid").toString();
			tid = onj1.get("tid").toString();
			model = onj1.get("controlModel").toString();

			selectedSuperUser = onj1.get("selectedSuperUser").toString();
			selectedSystem = onj1.get("selectedSystem").toString();
			selectedUser = onj1.get("selectedUser").toString();

			List<Long> superUserList = convert(selectedSuperUser);
			List<Long> systemList = convert(selectedSystem);
			List<Long> userList = convert(selectedUser);

			
			List<Long> mainLst = new ArrayList<>();
			mainLst.addAll(systemList);
			mainLst.addAll(superUserList);
			mainLst.addAll(userList);
			
			System.out.println("Main List " + mainLst);
			
			

			MerConfMaster recordModel = confMasterService.fetchByMidTid(mid, tid, "BUILD_CONTROL");

			if (recordModel != null) {
				
				
				recordModel.setUpdateDate(new Timestamp(new java.util.Date().getTime()));
				System.out.println(recordModel.getUpdateDate());
				recordModel.setUpdatedBy(rm.getUserid().toString());
				confMasterService.update(recordModel);
				long RecID = recordModel.getId();
				
				System.out.println("RecID " + RecID);
				
				confDetailsService.delete(RecID);

				List<BuildControlConfig> resultLst=  buildControlConfigService.getBuildControlConfigsByUserIds(mainLst);
				
				for(BuildControlConfig cfg : resultLst) {
					MerConfDetails details = new MerConfDetails();
					details.setRecId(RecID);
					details.setName(cfg.getControlName());
					details.setValue(cfg.getMappedValue());
					confDetailsService.insert(details);
				}
				
				md.setStatus("APPROVED");
				md.setApprovedBy(Integer.parseInt(rm.getUserid()));
				md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
				md.setApproverRemarks(remarks);
				//mdService.update(md);

				response.setCode("0000");
				response.setMessage("Record Approved.");

			
				
				
				
			} else {

				response.setCode("9999");
				response.setMessage("Record Not Exist With This MID TID.");
				}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}
	
	
	public static List<Long> convert(String input) {
		List<Long> resultList = new ArrayList<>();

		Pattern pattern = Pattern.compile("\\[(.*?)\\]");
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			String[] numbers = matcher.group(1).split(",");
			for (String number : numbers) {
				try {
					Long parsedNumber = Long.parseLong(number.trim());
					resultList.add(parsedNumber);
				} catch (NumberFormatException e) {
					System.err.println("Failed to parse: " + number);
				}
			}
		}

		return resultList;
	}

}