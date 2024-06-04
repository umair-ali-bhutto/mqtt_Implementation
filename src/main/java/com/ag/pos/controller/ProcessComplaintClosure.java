package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.SendNotifciation;

@RestController
public class ProcessComplaintClosure {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	ComplaintsService complaintsService;

	@Autowired
	ComplAssignmentsService complAssignmentsService;

	@Autowired
	SendNotifciation sendNotifciation;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/servicecomplaintclosure", "/baflservicecomplaintclosure" })
	public JSONObject doProcessComplaintClosure(@RequestBody String requestService, HttpServletRequest request) {
		String ipAddress = request.getHeader("IP");
		String[] m = null;

		if (ipAddress == null) {

			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		String resp = "Acknowledged";
		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				m = responseServiceTxnCompalint(requestService.toString());
				AgLogger.logInfo("RESPONSE " + m[0] + "|" + m[1]);
				if (m[0].equals("0000")) {
					resp = m[1];
				} else {
					resp = m[1];
				}
				job.put("Reponsemessage", resp);
			} else {
				job.put("Reponsemessage", "Invalid Credentials");
			}
		} catch (Exception e) {
			e.printStackTrace();
			job.put("Reponsemessage", "Exception.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(resp);
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("servicecomplaintclosure");
			adt.setMid(m[3]);
			adt.setTid(m[4]);
			adt.setSerialNum(m[5]);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}
		
		return job;
	}

	public String[] responseServiceTxnCompalint(String xmls) {
		AgLogger.logInfo("REQUEST DATA COMPLAINT: " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		String[] kl = new String[6];
		kl[0] = "0000";
		kl[1] = "SUCCESS";
		kl[2] = "0";
		String auditMid = "N/A";
		String auditTid = "N/A";
		String auditSerial = "N/A";

		String userName = "";
		String password = "";

		try {

			try {
				userName = (String) onj.get("UserName");
			} catch (Exception e) {

			}

			try {
				password = (String) onj.get("PASSWORD");
			} catch (Exception e) {

			}

			UserLogin userMdl = userLoginService.validateUserPassword(userName, password);

			if (!Objects.isNull(userMdl)) {

				String complaintId = "0", mid = null, tid = null, resolution = null, fieldOffice = null,
						fieldOfficeName = null, fieldOfficeUid = null, fieldOfficePass = null;
				try {
					complaintId = (String) onj.get("complaintId");
				} catch (Exception e) {

				}

				try {
					mid = (String) onj.get("mid");
					auditMid = mid;
				} catch (Exception e) {

				}

				try {
					tid = (String) onj.get("tid");
					auditTid = tid;
				} catch (Exception e) {

				}

				try {
					resolution = (String) onj.get("Resolution");
				} catch (Exception e) {

				}

				try {
					fieldOffice = (String) onj.get("fieldOfficeId");
				} catch (Exception e) {

				}

				try {
					fieldOfficeName = (String) onj.get("fieldOfficeName");
				} catch (Exception e) {

				}

				try {
					fieldOfficeUid = (String) onj.get("fieldOfficeUid");
				} catch (Exception e) {

				}

				try {
					fieldOfficePass = (String) onj.get("fieldOfficePass");
				} catch (Exception e) {

				}

				UserLogin userMdlFi = userLoginService.validateUserPassword(fieldOfficeUid, fieldOfficePass);

				if (userMdlFi != null) {

					Complaint comp = complaintsService.fetchComplaintById(Integer.parseInt(complaintId));
					if (comp != null) {
						if (comp.getMid().equals(mid) && comp.getTid().equals(tid)) {

							if (!comp.getStatus().equals("CLOSED")) {
								ComplAssignment cms = complAssignmentsService.fetchByComplID(comp.getId());
								cms.setProcBy(userMdlFi.getUserId()+"");
								cms.setProcDate(new Timestamp(new java.util.Date().getTime()));
								complAssignmentsService.update(cms);

								comp.setLastUpdated(new Timestamp(new java.util.Date().getTime()));
								comp.setClosureDate(new Timestamp(new java.util.Date().getTime()));
								comp.setStatus("CLOSED");
								comp.setClosedBy(userMdlFi.getUserId()+"");
								comp.setResolution(resolution);
								complaintsService.updateComplaint(comp);

								SendNotificationModel sdm = new SendNotificationModel();
								UserLogin lmk = userLoginService.validetUserid(Integer.parseInt(comp.getEntryBy()));
								sdm.setAccountOpeningDate("N/A");
								sdm.setClosedBy(userMdlFi.getUserName()+"");
								sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
								sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
								sdm.setComplaintNum(String.valueOf(comp.getId()));
								sdm.setMerchantName(lmk.getUserName());
								sdm.setPass("N/A");
								sdm.setResolution(resolution);
								sdm.setUserName("N/A");

								sdm.setReciverId(lmk.getUserId());
								sendNotifciation.doTask("002", "0002", "00001", sdm, lmk.getUserId());

								kl[0] = "0000";
								kl[1] = "COMPLAINT CLOSED.";
								kl[3] = auditMid;
								kl[4] = auditTid;
								kl[5] = auditSerial;

							} else {
								SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
								kl[0] = "6666";
								kl[1] = "COMPLAINT ALREADY CLOSED ON." + sdf.format(comp.getClosureDate());
								kl[3] = auditMid;
								kl[4] = auditTid;
								kl[5] = auditSerial;
							}

						} else {
							kl[0] = "7777";
							kl[1] = "COMPLAINT ID IS NOT TAGGED WITH THIS MID AND TID.";
							kl[3] = auditMid;
							kl[4] = auditTid;
							kl[5] = auditSerial;
						}
					} else {
						kl[0] = "8888";
						kl[1] = "COMPLAINT NOT FOUND.";
						kl[3] = auditMid;
						kl[4] = auditTid;
						kl[5] = auditSerial;
					}

				} else {
					kl[0] = "5555";
					kl[1] = "FIELD OFFICER CREDENTIALS INVALID.";
					kl[2] = "0";
					kl[3] = auditMid;
					kl[4] = auditTid;
					kl[5] = auditSerial;
				}

			}

			else {
				kl[0] = "9999";
				kl[1] = "VALIDATION FAILED " + userName + "|" + password;
				kl[2] = "0";
				kl[3] = auditMid;
				kl[4] = auditTid;
				kl[5] = auditSerial;
			}

		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
			kl[0] = "9999";
			kl[1] = e.getMessage();
			kl[2] = "0";
			kl[3] = auditMid;
			kl[4] = auditTid;
			kl[5] = auditSerial;
		}

		return kl;
	}

}
