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
import com.ag.generic.model.DataWrapper;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;

@Component("com.generic.ws.classes.WsAssignComplaint")
public class WsAssignComplaint implements Wisher {

	@Autowired
	ComplaintsService complaintsService;
	@Autowired
	ComplAssignmentsService complAssignmentsService;
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	SendNotifciation sendNotifciation;
	@Autowired
	UserScreenService userScreenService;
	@Autowired
	PortalNotificationUtil portalNotificationUtil;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String compid = (String) rm.getAdditionalData().get("compid").toString();
			String userIdToAssign = rm.getAdditionalData().get("userIdToAssign").toString();
			String userId = rm.getUserid();
			UserLogin uml = userLoginService.validetUserid(Integer.parseInt(userId));
			if(uml!=null) {
				AgLogger.logInfo(rm.getUserid(), " UPDATING COMPLAINT ID......" + compid);

				Complaint complain = complaintsService.fetchComplaintById(Integer.parseInt(compid));

				ComplAssignment complAssign = complAssignmentsService.fetchRecordHavingProcByAndProcDateNull(complain.getId());

				complAssign.setProcBy(uml.getUserName());
				complAssign.setProcDate(new Timestamp(new java.util.Date().getTime()));
				complAssignmentsService.update(complAssign);
				ComplAssignment newComplAssign = new ComplAssignment();
				
				Double parser = Double.parseDouble(userIdToAssign);
				
				UserLogin usl = userLoginService.validetUserid(parser.intValue());
				String dbText = AppProp.getProperty("user.dashboard.assign.comp.text");
				DataWrapper dwp = new DataWrapper(complain.getDescription(), complAssign.getCategory(),
						complAssign.getType(), complAssign.getSubType(), usl.getUserName(), dbText);
				newComplAssign.setCategory(complAssign.getCategory());
				newComplAssign.setCompId(Integer.parseInt(compid));
				newComplAssign.setDescription(dwp.getDashBoardText());
				newComplAssign.setEntryBy(userId);
				newComplAssign.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				newComplAssign.setMid(complAssign.getMid());
				newComplAssign.setPriority(complAssign.getPriority() + "");
				newComplAssign.setScreenId(complAssign.getScreenId());
				newComplAssign.setSubType(complAssign.getSubType());
				newComplAssign.setType(complAssign.getType());
				newComplAssign.setUserId(parser.intValue());
				newComplAssign.setClassName(complAssign.getClassName());

				complAssignmentsService.insert(newComplAssign);
				// End - Created new complain Assignment Request

				// Start - Updated Previous Complain
				complain.setLastUpdated(new Timestamp(new java.util.Date().getTime()));
				complain.setStatus(UtilAccess.complStatusAssigned);
				complain.setAssignedDate(new Timestamp(new java.util.Date().getTime()));
				complain.setAssignedTo(parser.intValue() + "");

				complaintsService.updateComplaint(complain);
				// End - Updated Previous Complain

				UserLogin lmk = userLoginService.validetUserid(Integer.parseInt(complain.getEntryBy()));
				// Start - Send Notification
				SendNotificationModel sdm = new SendNotificationModel();
				sdm.setAccountOpeningDate("N/A");
				sdm.setClosedBy(uml.getUserName());
				sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setComplaintNum(String.valueOf(compid));
				sdm.setMerchantName(lmk.getUserName());
				sdm.setPass("N/A");
				sdm.setResolution("N/A");

				sdm.setUserName(lmk.getUserName());
				sdm.setReciverId(lmk.getUserId());
				sendNotifciation.doTask("999", "0001", "00002", sdm, lmk.getUserId());
				// End - Send Notification
				
				

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