package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
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
import com.ag.generic.entity.ComplCategory;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.ComplCategoryService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.LovService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.AssignmentUtil;

@RestController
public class ProcessComplaintLunch {
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	AuditLogService auditLogService;
	
	@Autowired
	LovService LovService;
	
	@Autowired
	UtilService utilService;
	
	@Autowired
	ComplaintsService complaintsService;
	
	@Autowired
	ComplCategoryService complCategoryService;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/ServiceComplaintLaunch", "/BaflServiceComplaintLaunch" })
	public JSONObject doProcessComplaintLunch(@RequestBody String requestService, HttpServletRequest request) {
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
				m = responseServiceTxnCompalintLaunch(requestService.toString());
				AgLogger.logInfo("RESPONSE " + m[0] + "|" + m[1]);
				if (m[0].equals("0000")) {
					resp = m[1];
				} else {
					resp = m[1];
				}
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
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("ServiceComplaintLaunch");
			adt.setMid(m[3]);
			adt.setTid(m[4]);
			adt.setSerialNum(m[5]);
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		job.put("Reponsemessage", resp);
		return job;
	}

	public String[] responseServiceTxnCompalintLaunch(String xmls) {
		AgLogger.logInfo("REQUEST DATA COMPLAINT LAUNCH: " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		String[] kl = new String[6];
		kl[0] = "0000";
		kl[1] = "SUCCESS";

		String userName = "";
		String password = "";
		String auditMid = "N/A";
		String auditTid = "N/A";
		String auditSerial = "N/A";

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

				String merchantId = null, terminalId = null, model = null, serialNo = null, maker = null,
						complaintTypeId = null, complainSubTypeId = null, description = null;
				try {
					merchantId = (String) onj.get("merchantId");
					AgLogger.logInfo(" PArameter  " + merchantId);
					auditMid = merchantId;
				} catch (Exception e) {

				}

				try {
					terminalId = (String) onj.get("terminalId");
					AgLogger.logInfo(" PArameter  " + terminalId);
					auditTid = terminalId;
				} catch (Exception e) {

				}

				try {
					complaintTypeId = (String) onj.get("complaintTypeId");
					AgLogger.logInfo(" PArameter  " + complaintTypeId);
				} catch (Exception e) {

				}

				try {
					complainSubTypeId = (String) onj.get("complainSubTypeId");
					AgLogger.logInfo(" PArameter  " + complainSubTypeId);
				} catch (Exception e) {

				}

				try {
					description = (String) onj.get("description");
					AgLogger.logInfo(" PArameter  " + description);
				} catch (Exception e) {

				}

				String[] k = utilService.fetchMerchantTerminalUpd(merchantId, terminalId);

				if (k[0].equals("0000")) {
					maker = k[3];
					serialNo = k[2];
					auditSerial = serialNo;
					model = k[1];

					LovMaster lstLov = LovService.fetchLovsbyId(complaintTypeId, userMdl.getCorpId());
					if (lstLov != null) {
						LovDetail lst = LovService.fetchLovsDetailsByLovIDandLovDetailID(complaintTypeId,
								complainSubTypeId, userMdl.getCorpId());
						if (lst != null) {
							AgLogger.logInfo(" SUCCESSFUL RECORD CHECK  ");

							ComplCategory cs = new ComplCategory();
							cs.setId(2);

							ComplCategory ctd = complCategoryService.fetchByID(cs);
							Complaint c = new Complaint();
							Complaint cTemp = new Complaint();

							c.setAssignedDate(null);
							c.setAssignedTo(null);
							c.setClosureDate(null);
							c.setComplaintDesc(description);

							c.setEntryBy(merchantId);
							c.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							c.setMaker(maker);
							c.setMid(merchantId);
							c.setModel(model);

							if (ctd != null) {
								c.setCategory(ctd.getCode());
								c.setSubType(ctd.getSubTypeCode());
								c.setType(ctd.getTypeCode());
							}

							c.setSerialNumber(serialNo);
							c.setStatus("NEW");
							c.setTid(terminalId);
							c.setComplaintType(complaintTypeId);
							c.setComplaintSubType(complainSubTypeId);

							cTemp.setEntryDate(new Timestamp(new java.util.Date().getTime()));
							cTemp.setMaker(maker);
							cTemp.setMid(merchantId);
							cTemp.setModel(model);
							cTemp.setSerialNumber(serialNo);
							cTemp.setEntryBy(merchantId);

							String descr = AssignmentUtil.doConvertToJSONComplaint(cTemp);

							c.setDescription(descr);
							System.out.println(descr);
							long g = complaintsService.insertComplaint(c);
//							SendNotificationModel sdm = new SendNotificationModel();
//							UserLogin lmk = userLoginService.validetUserid(merchantId);
//							sdm.setAccountOpeningDate("N/A");
//							sdm.setClosedBy("N/A");
//							sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
//							sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
//							sdm.setComplaintNum(String.valueOf(g));
//							sdm.setMerchantName((userLoginService.validetUser(merchantId)).getUserName());
//							sdm.setPass("N/A");
//							sdm.setResolution("N/A");
//							sdm.setUserName("N/A");

//							sdm.setReciverId(lmk.getUserId());
//							sendNotifciation.doTask("002", "0001", "00001", sdm, lmk.getUserId());

							kl[0] = "0000";
							kl[1] = "Complaint Received with ID : " + g;
							kl[2] = "0";
							kl[3] = auditMid;
							kl[4] = auditTid;
							kl[5] = auditSerial;

						} else {
							kl[0] = "0002";
							kl[1] = "INVALID COMPLAIN ID AND COMPLAIN SUB ID";
							kl[2] = "0";
							kl[3] = auditMid;
							kl[4] = auditTid;
							kl[5] = auditSerial;
						}
					} else {
						kl[0] = "0002";
						kl[1] = "INVALID COMPLAIN ID";
						kl[2] = "0";
						kl[3] = auditMid;
						kl[4] = auditTid;
						kl[5] = auditSerial;

					}

				} else {
					kl[0] = "0002";
					kl[1] = "INVALID TERMINAL AND MERCHANT ID";
					kl[2] = "0";
					kl[3] = auditMid;
					kl[4] = auditTid;
					kl[5] = auditSerial;

				}

			}

			else {
				kl[0] = "0001";
				kl[1] = "INVALID CREDENTIALS";
				kl[2] = "0";
				kl[3] = auditMid;
				kl[4] = auditTid;
				kl[5] = auditSerial;
			}

		} catch (Exception e) {
			kl[0] = "0001";
			kl[1] = "SOMETHING WEN WRONG.";
			kl[3] = auditMid;
			kl[4] = auditTid;
			kl[5] = auditSerial;
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);

		}

		return kl;
	}

}
