package com.ag.pos.loyalty.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AssignmentUtil;
import com.ag.generic.util.SendNotifciation;
import com.google.gson.Gson;

@RestController
public class LoyaltyLaunchComplaints {

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	ComplaintsService complaintService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	SendNotifciation sendNotifciation;

	@Autowired
	UtilService utilService;

	@PostMapping({ "/LoyaltyLaunchComplaint" })
	public ResponseModel doProcessFileAuth(@RequestBody JSONObject requestService, HttpServletRequest request) {
		return doProcessData(requestService, request);
	}

	public ResponseModel doProcessData(JSONObject requestService, HttpServletRequest request) {
		ResponseModel response = new ResponseModel();
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		try {
			UserLogin user = userLoginService.validateUserPassword(requestService.get("userName").toString(),
					requestService.get("password").toString());
			if (user != null) {

				String mid = requestService.get("mid").toString();
				String type = requestService.get("type").toString();
				String tid = requestService.get("tid").toString();
				String maker = requestService.get("maker").toString();
				String serialNum = requestService.get("serialNum").toString();
				String model = requestService.get("model").toString();
				String description = requestService.get("description").toString();
				String subtype = requestService.get("subtype").toString();

				UserLogin rm = userLoginService.fetchUserByMid(requestService.get("mid").toString(), user.getCorpId());
				String userid = rm.getUserId() + "";
				if (!type.equalsIgnoreCase("-") && mid.length() != 0 && tid.length() != 0 && maker.length() != 0
						&& serialNum.length() != 0 && model.length() != 0) {
					Complaint c = new Complaint();
					Complaint cTemp = new Complaint();

					c.setAssignedDate(null);
					c.setAssignedTo(null);
					c.setClosureDate(null);
					c.setComplaintDesc(description);

					c.setEntryBy(userid);
					c.setEntryDate(new Timestamp(new java.util.Date().getTime()));
					c.setMaker(maker);
					c.setMid(mid);
					c.setModel(model);

					c.setCategory("002");
					c.setSubType(subtype);
					c.setType(type);
					c.setSerialNumber(serialNum);
					c.setStatus("NEW");
					c.setTid(tid);
					c.setComplaintType(type);
					c.setComplaintSubType(subtype);

					cTemp.setEntryDate(new Timestamp(new java.util.Date().getTime()));
					cTemp.setMaker(maker);
					cTemp.setMid(mid);
					cTemp.setModel(model);
					cTemp.setSerialNumber(serialNum);
					cTemp.setEntryBy(userid);

					String descr = AssignmentUtil.doConvertToJSONComplaint(cTemp);

					c.setDescription(descr);

					try {
						UserLogin ukl = userLoginService.validetUserid(rm.getUserId());

						long g = complaintService.insertComplaint(c);
						SendNotificationModel sdm = new SendNotificationModel();
						sdm.setAccountOpeningDate("N/A");
						sdm.setClosedBy("N/A");
						sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
						sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
						sdm.setComplaintNum(String.valueOf(g));
						sdm.setMerchantName(ukl.getUserName());
						sdm.setPass("N/A");
						sdm.setResolution("N/A");
						sdm.setUserName("N/A");
						sdm.setReciverId(ukl.getUserId());
						sendNotifciation.doTask("002", type, subtype, sdm, ukl.getUserId());
						utilService.doSendAppNotificationOnly(rm.getUserId(), AppProp.getProperty("launch.compl.notif"),
								AppProp.getProperty("launch.compl.notif"));

						response.setCode("0000");
						response.setMessage("Complaint registered successfully. with complaint Id " + g);

					} catch (Exception e) {
						e.printStackTrace();
						response.setCode("8888");
						response.setMessage("Something went wrong.Please try again later.");
					}
				} else {
					response.setCode("8888");
					response.setMessage("Please enter valid data.");
				}

			} else {
				response.setCode("9991");
				response.setMessage("Invalid Credentials.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setTid("N/A");
			adt.setEntryDate(time);
			adt.setRequest(requestService.toString());
			adt.setResponse(new Gson().toJson(response));
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("Loyalty Launch Complaint");
			adt.setMid("N/A");
			adt.setSerialNum("N/A");
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return response;
	}

}
