package com.ag.pos.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.JMSSenderService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.TxnLog;

@RestController
public class ProcessSettlment {

	@Autowired
	public UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	JMSSenderService jmsSenderServiceImpl;

	@SuppressWarnings("unchecked")
	@PostMapping({ "/servicesettlement", "/baflservicesettlement" })
	@Transactional
	public JSONObject doProcessSettlment(@RequestBody String requestService, HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getHeader("IP");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
		}
		JSONObject job = new JSONObject();
		String resp = "Acknowledged";
		String ksk = "";
		String mid = "";
		String tid = "";
		String model = "";
		String serialNumber = "";

		String posDateTime = "";
		String imei = "";
		String telco = "";
		String longitude = "";
		String latitude = "";
		String fieldOne = "";
		String fielTwo = "";
		String fieldThree = "";
		String fieldFour = "";
		String fieldFive = "";
		String batchNUmber = "0";
		try {
			if (requestService.length() != 0) {
				
				String[] m = responseServiceSettelment(requestService.toString());

				mid = m[3];
				tid = m[4];
				model = m[5];
				serialNumber = m[6];

				posDateTime = m[7];
				imei = m[8];
				telco = m[9];
				longitude = m[10];
				latitude = m[11];
				fieldOne = m[12];
				fielTwo = m[13];
				fieldThree = m[14];
				fieldFour = m[15];
				fieldFive = m[16];
				// fieldSix = m[17];
				batchNUmber = m[18];
				// iccid = m[19];
				AgLogger.logInfo("RESPONSE " + m[0] + "|" + m[1]);
				if (m[0].equals("0000")) {
					ksk = " SUCCESS";
					resp = "Acknowledged";
					jmsSenderServiceImpl.sendBatch(requestService);
				} else if (m[0].equals("8888")) {
					ksk = " INVALID ATTEMPT";
					resp = "FAIL-04";
				} else {
					ksk = " FAILED";
					resp = "FAIL-02";
				}

			} else {
				resp = "Invalid Credentials";
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp = "Exception.";
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setResponse(resp);
			adt.setRequestMode("POS");
			adt.setRequest(requestService);
			adt.setRequestIp(ipAddress);
			adt.setTxnType("servicesettlement");
			adt.setSerialNum(serialNumber);
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		job.put("Reponsemessage", resp);
		AgLogger.logInfo("RESPONSE: " + resp);

		TxnLog txnLogId = UtilAccess.insertTxnLogs(AppProp.getProperty("act.batch"), "BATCH SETT REQUEST : " + ksk, mid,
				tid, model, serialNumber, posDateTime, imei, telco, longitude, latitude, fieldOne, fielTwo, fieldThree,
				fieldFour, fieldFive, batchNUmber);

		entityManager.persist(txnLogId);

		return job;
	}

	public String[] responseServiceSettelment(String xmls) {
		AgLogger.logInfo("REQUEST DATA: " + xmls);
		JSONObject onj = (JSONObject) JSONValue.parse(xmls);

		JSONArray txnSimDetails = new JSONArray(onj.get("SimData").toString());

		JSONArray txnSummaryDetails = new JSONArray(onj.get("Summary").toString());

		String[] kl = new String[20];
		kl[0] = "0000";
		kl[1] = "SUCCESS";
		kl[2] = "0";
		kl[3] = "N/A";
		kl[4] = "N/A";
		kl[5] = "N/A";
		kl[6] = "N/A";

		String userName = "";
		String password = "";
		String model = "";
		String mid = "";
		String tid = "";
		String serial = "";
		String bacthNumber = "";

		String posDateTime = "";
		String imei = "";
		String telco = "";
		String longitude = "";
		String latitude = "";
		String fieldOne = "";
		String fielTwo = "";
		String fieldThree = "";
		String fieldFour = "";
		String fieldFive = "";
		String fieldSix = "";
		String iccid = "";

		org.json.JSONObject objk = null;
		org.json.JSONObject objkSummary = null;
		org.json.JSONObject objkTxnInfo = null;
		JSONArray txnInfo = null;
		try {
			objk = (org.json.JSONObject) txnSimDetails.get(0);
			objkSummary = (org.json.JSONObject) txnSummaryDetails.get(0);
			txnInfo = new JSONArray(onj.get("TxnInfo").toString());
			if (!Objects.isNull(txnInfo) && !txnInfo.isEmpty()) {
				objkTxnInfo = (org.json.JSONObject) txnInfo.get(0);
			}
		} catch (Exception e) {
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}

		try {
			posDateTime = (String) objkSummary.get("PosDateTime");
		} catch (Exception e) {
		}

		try {
			imei = (String) objk.get("imei");
		} catch (Exception e) {
		}
		try {
			telco = (String) objk.get("telco");
		} catch (Exception e) {
		}
		try {
			longitude = (String) objk.get("longitude");
		} catch (Exception e) {
		}
		try {
			latitude = (String) objk.get("latitude");
		} catch (Exception e) {
		}
		try {
			fieldOne = (String) objk.get("fieldOne");
		} catch (Exception e) {
		}
		try {
			fielTwo = (String) objk.get("fieldTwo");
		} catch (Exception e) {
		}
		try {
			fieldThree = (String) objk.get("fieldThree");
		} catch (Exception e) {
		}
		try {
			fieldFour = (String) objk.get("fieldFour");
		} catch (Exception e) {
		}
		try {
			fieldFive = (String) objk.get("fieldFive");
		} catch (Exception e) {
		}
		try {
			fieldSix = (String) objk.get("fieldSix");
		} catch (Exception e) {
		}
		try {
			iccid = (String) objk.get("iccid");
		} catch (Exception e) {
		}

		try {

			try {
				if (!Objects.isNull(objkTxnInfo)) {
					userName = (String) (txnInfo.getJSONObject(0).get("UserName"));
				} else {
					JSONArray ar = new JSONArray(onj.get("Transaction").toString());
					userName = (String) (ar.getJSONObject(0).get("UserName"));
				}
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			try {
				if (!Objects.isNull(objkTxnInfo)) {
					password = (String) (txnInfo.getJSONObject(0).get("PASSWORD"));
				} else {
					JSONArray ar = new JSONArray(onj.get("Transaction").toString());
					password = (String) (ar.getJSONObject(0).get("PASSWORD"));
				}
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			try {
				JSONArray ar = new JSONArray(onj.get("Transaction").toString());
				serial = (String) (ar.getJSONObject(0).get("SerialNumber"));
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			try {
				JSONArray ar = new JSONArray(onj.get("Transaction").toString());
				model = (String) (ar.getJSONObject(0).get("Model"));
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			try {
				JSONArray ar = new JSONArray(onj.get("Transaction").toString());
				mid = (String) (ar.getJSONObject(0).get("MID"));
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			try {
				JSONArray ar = new JSONArray(onj.get("Transaction").toString());
				tid = (String) (ar.getJSONObject(0).get("TID"));
			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			try {
				JSONArray ar = new JSONArray(onj.get("Transaction").toString());
				bacthNumber = (String) (ar.getJSONObject(0).get("BatchNo"));

			} catch (Exception e) {
				AgLogger.logerror(getClass(), " EXCEPTION  ", e);

			}

			kl[3] = mid;
			kl[4] = tid;
			kl[5] = model;
			kl[6] = serial;

			UserLogin userMdl = userLoginService.validateUserPassword(userName, password);

			if (!Objects.isNull(userMdl)) {
				kl[0] = "0000";
				kl[1] = "SUCCESS";
				kl[2] = "0";
			}

			else {
				kl[0] = "8888";
				kl[1] = "INVALID USER";
				kl[2] = "0";
			}

		} catch (Exception e) {
			kl[0] = "9999";
			kl[1] = e.getMessage();
			kl[2] = "0";
		}

		kl[7] = posDateTime;
		kl[8] = imei;
		kl[9] = telco;
		kl[10] = longitude;
		kl[11] = latitude;
		kl[12] = fieldOne;
		kl[13] = fielTwo;
		kl[14] = fieldThree;
		kl[15] = fieldFour;
		kl[16] = fieldFive;
		kl[17] = fieldSix;
		kl[18] = bacthNumber;
		kl[19] = iccid;

		return kl;

	}

}
