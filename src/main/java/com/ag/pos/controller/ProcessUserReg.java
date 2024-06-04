package com.ag.pos.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.ComplCategory;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.ComplCategoryService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.AssignmentUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.QueueLog;
import com.ag.mportal.services.QueueLogService;

@RestController
public class ProcessUserReg {
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	AuditLogService auditLogService;
	
	@Autowired
	UtilService utilService;
	
	@Autowired
	QueueLogService queueLogService;
	
	@Autowired
	ComplCategoryService complCategoryService;
	
	@Autowired
	ComplaintsService complaintsService;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/serviceuserreg", "/baflserviceuserreg" })
	public JSONObject doProcessUserReg(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();
		String resp = "Acknowledged";
		String respMsg = "SUCCESS";
		String[] m = null;
		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				m = processQueueLog(requestService.toString());
				AgLogger.logInfo("RESPONSE " + m[0] + "|" + m[1] + "|" + m[2]);
				if (m[0].equals("0000")) {
					resp = "Acknowledged";
				}

				else {
					resp = m[1];
					respMsg = m[2];
				}
			} else {
				resp = "FAIL-03";
				respMsg = "INVALID DATA";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp = "FAIL-03";
			respMsg = "EXCEPTION";
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("serviceuserreg");
			adt.setMid(m[2]);
			adt.setTid(m[3]);
			adt.setSerialNum(m[4]);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);

		}
		job.put("Reponsemessage", resp);
		job.put("ResponseStr", respMsg);

		return job;
	}

	@Transactional
	public String[] processQueueLog(String xmls) {
		AgLogger.logInfo("REQUEST DATA: " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);
		String[] kl = new String[6];
		kl[0] = "0000";
		kl[1] = "SUCCESS";
		kl[2] = "User Request Recieved.";
		String auditMid = "N/A";
		String auditTid = "N/A";
		String auditSerial = "N/A";

		QueueLog queueLog = new QueueLog();

		try {
			queueLog.setMid(onj.get("MID").toString());
			auditMid = onj.get("MID").toString();
		} catch (Exception e) {

		}

		try {
			queueLog.setTid(onj.get("TID").toString());
			auditTid = onj.get("TID").toString();
		} catch (Exception e) {

		}

		try {
			queueLog.setModel(onj.get("Model").toString());
		} catch (Exception e) {

		}

		try {
			queueLog.setMsdisn(onj.get("Mobile").toString());
		} catch (Exception e) {

		}

		try {
			queueLog.setEmail(onj.get("Email").toString());
		} catch (Exception e) {

		}
		try {
			queueLog.setSerialNum(onj.get("SerialNumber").toString());
			auditSerial = onj.get("SerialNumber").toString();
		} catch (Exception e) {

		}
		kl[3] = auditMid;
		kl[4] = auditTid;
		kl[5] = auditSerial;
		queueLog.setEntryDate(new Timestamp(new java.util.Date().getTime()));

		String[] validateQueueLog = new UtilAccess().validateQueueLog(queueLog);
		AgLogger.logInfo("validateQueueLog RESPONSE : " + validateQueueLog[0] + " | " + validateQueueLog[1]);
		if (validateQueueLog[0].equals("00")) {
			AgLogger.logInfo("validateQueueLog SUCCESS : ");
			String[] validateMIDTID = utilService.fetchMerchantTerminalUpd(queueLog.getMid(), queueLog.getTid());

			AgLogger.logInfo("validateMIDTID REPSONSE : " + validateMIDTID[0] + " | " + validateMIDTID[1] + " | "
					+ validateMIDTID[2] + " | " + validateMIDTID[3]);

			if (validateMIDTID[0].equals("0000")) {
				AgLogger.logInfo("validateMIDTID SUCCESS : ");
				UserLogin UserLogin = userLoginService.validetUserWithoutCorpId(queueLog.getMid());

				if (UserLogin != null) {
					AgLogger.logInfo("MID Exists  : " + UserLogin.getMid());
					try {
						queueLog.setMaxHits(0);
						queueLog.setStatus("FAILURE");
						queueLog.setStatusMsg("MID EXISTS");
						kl[0] = "9999";
						kl[1] = "FAILURE";
						kl[2] = "Merchant ID Already Exists.";
						queueLogService.insertLog(queueLog);
					} catch (Exception e) {
						AgLogger.logerror(getClass(), " EXCEPTION  ", e);
					}
				} else {
					AgLogger.logInfo("MID NOT EXISTS/NEW MID  : ");
					QueueLog log = queueLogService.fetchQueueLogDetails(queueLog.getMid());
					if (log != null) {
						AgLogger.logInfo("Record Already In Queue");
						try {
							queueLog.setMaxHits(0);
							queueLog.setStatus("FAILURE");
							queueLog.setStatusMsg("RECORD ALREADY IN QUEUE");
							kl[0] = "9999";
							kl[1] = "FAILURE";
							kl[2] = "Record Already Exists in queue.";
							queueLogService.insertLog(queueLog);
						} catch (Exception e) {
							AgLogger.logerror(getClass(), " EXCEPTION  ", e);
						}
					} else {
						AgLogger.logInfo("NEW RECORD WITH SUCCESS QUEUE LOG");
						try {
							queueLog.setMaxHits(0);
							queueLog.setStatus("NEW_REC");
							kl[0] = "0000";
							kl[1] = "SUCCESS";
							kl[2] = "User Request Recieved.";
							queueLogService.insertLog(queueLog);
							try {

								ComplCategory cs = new ComplCategory();
								cs.setId(1);
								ComplCategory ctd = complCategoryService.fetchByID(cs);
								Complaint cmp = new AssignmentUtil().doInsertAssignment(
										AssignmentUtil.doConvertToJSONUser(queueLog), queueLog.getMid(),
										queueLog.getTid(), ctd,AppProp.getProperty("default.corpid"));

								complaintsService.insertComplaint(cmp);
							} catch (Exception e) {
								AgLogger.logerror(getClass(), " EXCEPTION  ", e);
							}
						} catch (Exception e) {
							AgLogger.logerror(getClass(), " EXCEPTION  ", e);
						}
					}
				}

			} else {
				AgLogger.logInfo("validateMIDTID FAILD : ");
				try {
					queueLog.setMaxHits(0);
					queueLog.setStatus("FAILURE");
					queueLog.setStatusMsg("MID TID VALIDATION FAILD ");
					queueLogService.insertLog(queueLog);
					kl[0] = "9999";
					kl[1] = "FAILURE";
					kl[2] = "MID TID VALIDATION FAILD";
				} catch (Exception e) {
					AgLogger.logInfo("Exception in validateMIDTID FAILD  insertLog :   " + e);
					AgLogger.logerror(getClass(), " EXCEPTION  ", e);
				}
			}

		} else {
			AgLogger.logInfo("validateQueueLog ERROR : ");
			try {
				queueLog.setMaxHits(0);
				queueLog.setStatusMsg(validateQueueLog[1]);
				queueLog.setStatus("FAILURE");
				queueLogService.insertLog(queueLog);
				kl[0] = "9999";
				kl[1] = "FAILURE";
				kl[2] = validateQueueLog[1];

			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);
				kl[0] = "9999";
				kl[1] = "FAILURE";
				kl[2] = "EXCEPTION";
			}

		}
		return kl;

	}
}
