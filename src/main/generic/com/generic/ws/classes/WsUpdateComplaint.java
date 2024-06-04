package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.generic.util.SendNotifciation;

@Component("com.generic.ws.classes.WsUpdateComplaint")
public class WsUpdateComplaint implements Wisher {

	@Autowired
	ComplaintsService complaintsService;
	@Autowired
	ComplAssignmentsService complAssignmentsService;
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	SendNotifciation sendNotifciation; 
	
	@Autowired
	UtilService utilService; 
	
	@Autowired
	UserScreenService userScreenService;
	
	@Autowired
	PortalNotificationUtil portalNotificationUtil;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String compid = (String) rm.getAdditionalData().get("compid");
			String resolution = rm.getAdditionalData().get("resolution").toString();
			String userId = rm.getUserid();
			UserLogin lmkn = userLoginService.validetUserid(Integer.parseInt(userId));
			if(lmkn!=null) {
				AgLogger.logInfo(rm.getUserid(), " UPDATING COMPLAINT ID......" + compid);

				Complaint comp = complaintsService.fetchComplaintById(Integer.parseInt(compid));
				ComplAssignment cms = complAssignmentsService.fetchRecordHavingProcByAndProcDateNull(comp.getId());
				cms.setProcBy(lmkn.getUserName());
				cms.setProcDate(new Timestamp(new java.util.Date().getTime()));
				complAssignmentsService.update(cms);

				comp.setLastUpdated(new Timestamp(new java.util.Date().getTime()));
				comp.setClosureDate(new Timestamp(new java.util.Date().getTime()));
				comp.setStatus("CLOSED");
				comp.setClosedBy(lmkn.getUserId()+"");
				comp.setResolution(resolution);
				comp.setIssueAddressed("Y");
				complaintsService.updateComplaint(comp);

				SendNotificationModel sdm = new SendNotificationModel();
				sdm.setAccountOpeningDate("N/A");
				sdm.setClosedBy(lmkn.getUserName());
				sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setComplaintNum(String.valueOf(compid));
				UserLogin lmk = userLoginService.validetUserid(Integer.parseInt(comp.getEntryBy()));
				sdm.setMerchantName(lmk.getUserName());
				sdm.setPass("N/A");
				sdm.setResolution(resolution);
				sdm.setUserName(lmk.getUserName());
				sdm.setReciverId(lmk.getUserId());
				sendNotifciation.doTask(comp.getCategory(), comp.getType(), comp.getSubType(), sdm, lmk.getUserId());
				utilService.doSendAppNotificationOnly(Integer.parseInt(rm.getUserid()) , AppProp.getProperty("close.compl.notif"), AppProp.getProperty("close.compl.notif"));

				response.setCode("0000");
				response.setMessage("Complaint Updated Successfully.");
				
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				HashMap<Object, Object> mpk = portalNotificationUtil.getNotificationsUpdated(Integer.parseInt(userId),rm.getCorpId());
				map.put("notifications", mpk.get("notifications"));
				map.put("notifications_count", mpk.get("notifications_count"));
				response.setData(map);
			}else {
				response.setCode("0001");
				response.setMessage("Invalid User.");
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9995");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}
	
	public Map<Integer, UserScreen> convertListToMapScreen(List<UserScreen> list) {
		Map<Integer, UserScreen> map = list.stream()
				.collect(Collectors.toMap(UserScreen::getScreenId, Function.identity()));
		return map;
	}
}