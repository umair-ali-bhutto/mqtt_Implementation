package com.mportal.ws.classes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.ComplAssignmentsCustom;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.QueueLog;
import com.ag.mportal.services.QueueLogService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component("com.mportal.ws.classes.WsRejectNewUsers")
public class WsRejectNewUsers implements Wisher{

	@Autowired
	QueueLogService queueLogService;
	
	@Autowired
	UtilService utilService;
	
	@Autowired
	SendNotifciation sendNotifciation; 
	
	@Autowired
	ComplAssignmentsService complAssignmentsService;
	
	@Autowired
	ComplaintsService complaintsService;
	
	@Autowired
	UserScreenService userScreenService;
	
	@Autowired
	PortalNotificationUtil portalNotificationUtil;
	
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String[] respString = null;
			String[] resp = new String[2];
			resp[0] = "0000";
			resp[1] = "REJECTED";

			String json = new Gson().toJson(rm.getAdditionalData().get("selectedComp"));
			String userId = rm.getUserid();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			ArrayList<ComplAssignmentsCustom> selectedComplaint = gson.fromJson(json,
					new TypeToken<ArrayList<ComplAssignmentsCustom>>() {
					}.getType());

			if (selectedComplaint.size() != 0) {
				respString = new String[selectedComplaint.size()];
				int i = 0;
				for (ComplAssignmentsCustom com : selectedComplaint) {
					QueueLog queueLog = new Gson().fromJson(com.getDescription(), QueueLog.class);
					queueLog.setStatus("REJECTED");
					queueLog.setRemarks(com.getMid() + " is rejected by " + userId);
					try {
						queueLogService.update(queueLog);
						updateCompleAssignment(com.getCompId(), userId);
						resp[1] = "REJECTED";
						createNotification(queueLog.getMid(), queueLog.getMsdisn(), queueLog.getEmail(),rm.getCorpId());
					} catch (Exception e) {
						e.printStackTrace();
						resp[1] = "FAILED";
					}
					respString[i] = com.getMid() + ":" + resp[1];
					i++;
				}
				response.setCode("0000");
				response.setMessage("SUCCESS." + Arrays.toString(respString));
				
				
				
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				HashMap<Object, Object> mpk = portalNotificationUtil.getNotificationsUpdated(Integer.parseInt(userId),rm.getCorpId());
				map.put("notifications", mpk.get("notifications"));
				map.put("notifications_count", mpk.get("notifications_count"));
				response.setData(map);
				
				
				
			} else {
				response.setCode("8888");
				response.setMessage("No User List Selected.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public void createNotification(String mid, String mobile, String email, String corpId) {
		SendNotificationModel sdm = new SendNotificationModel();

		String[] k = utilService.fetchMerchantUpd(mid);

		if (Objects.equals(k[0], "0000")) {
			sdm.setUserName(k[1]);
			sdm.setMerchantName(k[1]);
		} else {
			sdm.setUserName(null);
			sdm.setMerchantName(null);
		}

		sdm.setPass("N/A");
		sdm.setReciverId(0);
		sdm.setClosedBy(null);
		sdm.setRequestComplDate(null);
		sdm.setClosureDate(null);
		sdm.setComplaintNum(null);
		sdm.setResolution(null);
		sdm.setAccountOpeningDate(null);

		sendNotifciation.doTaskUnRegUser("999", "0001", "00005", sdm, mobile, email,0,corpId);
	}

	void updateCompleAssignment(int assignmentID, String userId) {
		// Update complaint Assignment
		ComplAssignment cms = complAssignmentsService.fetchByComplID(assignmentID);
		cms.setProcBy(userId);
		cms.setProcDate(new Timestamp(new java.util.Date().getTime()));
		complAssignmentsService.update(cms);

		// Update complaint
		Complaint cks = complaintsService.fetchComplaintById(cms.getCompId());
		cks.setStatus(UtilAccess.complStatusClosed);
		cks.setClosedBy(userId);
		cks.setClosureDate(new Timestamp(new java.util.Date().getTime()));
		cks.setIssueAddressed("Y");
		cks.setReasonFailure("N/A");
		cks.setResolution("NEW USER REQUEST REJECTED.");
		complaintsService.updateComplaint(cks);
	}
	
	
	public Map<Integer, UserScreen> convertListToMapScreen(List<UserScreen> list) {
		Map<Integer, UserScreen> map = list.stream()
				.collect(Collectors.toMap(UserScreen::getScreenId, Function.identity()));
		return map;
	}


}