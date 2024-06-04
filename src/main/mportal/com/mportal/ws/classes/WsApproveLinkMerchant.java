package com.mportal.ws.classes;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ComplainDescriptionModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.services.ChainMerchantsService;
import com.google.gson.Gson;

@Component("com.mportal.ws.classes.WsApproveLinkMerchant")
public class WsApproveLinkMerchant implements Wisher {

	@Autowired
	ComplAssignmentsService complAssignmentsService;

	@Autowired
	ComplaintsService complaintsService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	ChainMerchantsService chainMerchantsService;

	@Autowired
	SendNotifciation sendNotifciation;

	@Autowired
	PortalNotificationUtil portalNotificationUtil;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			int compAssignId = Integer.parseInt(rm.getAdditionalData().get("compAssignId").toString());
			int userId = Integer.parseInt(rm.getUserid());
			String corpId = rm.getCorpId();

			ComplAssignment cds = complAssignmentsService.fetchByID(compAssignId);
			Complaint complaint = complaintsService.fetchComplaintById(cds.getCompId());
			if (cds != null) {
				int comlainIdno = cds.getCompId();
				String cno = String.valueOf(comlainIdno);
				String description = complaint.getDescription();
				int ikId = (int) cds.getId();
				String[] resp = executeProcess(ikId, description, cno, userId, corpId);
				if (resp[0].equals("0000")) {
					response.setData(portalNotificationUtil.getNotificationsUpdated(userId, rm.getCorpId()));
				}
				response.setCode(resp[0]);
				response.setMessage(resp[1]);

			}
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String[] executeProcess(int id, String description, String cno, int userId, String corpId) {
		String[] resp = new String[2];

		try {

			ComplainDescriptionModel obj = new Gson().fromJson(description, ComplainDescriptionModel.class);
			ChainMerchant linkMerchant = new ChainMerchant();
			linkMerchant.setMid(obj.getMid());

			ChainMerchant chainMerchant = chainMerchantsService.fetchByID(linkMerchant);
			if (chainMerchant != null) {
				chainMerchant.setChainMerchantMid(obj.getChainMID());
				chainMerchantsService.update(chainMerchant);

				SendNotificationModel sdm = new SendNotificationModel();
				UserLogin ukl = userLoginService.validetUser(obj.getChainMID(), corpId);
				sdm.setAccountOpeningDate("N/A");
				sdm.setClosedBy("N/A");
				sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setComplaintNum(cno);
				sdm.setMerchantName(ukl.getUserName());
				sdm.setPass("N/A");
				sdm.setResolution("N/A");
				sdm.setUserName("N/A");
				sdm.setReciverId(ukl.getUserId());
				sendNotifciation.doTask("001", "0002", "00003", sdm, ukl.getUserId());

				resp[0] = "0000";
				resp[1] = "SUCCESS";

				updateCompleAssignment(id, userId);

			} else {
				resp[0] = "0001";
				resp[1] = "No Record Found";

			}

		} catch (Exception e) {
			resp[0] = "01";
			resp[1] = "ERROR";
			AgLogger.logTrace(getClass(), " EXCEPTION ON ", e);

		}
		return resp;

	}

	void updateCompleAssignment(int assignmentID, int userId) {
		try {

			ComplAssignment cms = complAssignmentsService.fetchByID(assignmentID);
			cms.setProcBy(userId + "");
			cms.setProcDate(new Timestamp(new java.util.Date().getTime()));
			complAssignmentsService.update(cms);

			Complaint cks = complaintsService.fetchComplaintById(cms.getCompId());
			cks.setStatus(UtilAccess.complStatusClosed);
			cks.setClosedBy(userId + "");
			cks.setClosureDate(new Timestamp(new java.util.Date().getTime()));
			cks.setIssueAddressed("Y");
			cks.setReasonFailure("N/A");
			cks.setResolution("LINKED MERCHANT APPROVED.");
			complaintsService.updateComplaint(cks);
		} catch (Exception e) {
			AgLogger.logTrace(getClass(), " EXCEPTION ON ", e);
		}
	}

}