package com.ag.pos.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.TxnLog;

@RestController
public class ProcessProfileDownload {

	@Autowired
	AuditLogService auditLogService;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@GetMapping({ "/serviceprofile", "/baflserviceprofile" })
	@Transactional
	public JSONObject doProcessProfileDownload(@RequestParam("Model") String model,
			@RequestParam("SerialNumber") String serialNumber, @RequestParam("TID") String tid,
			@RequestParam("MID") String mid, @RequestParam("APPNAME") String appName, HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getHeader("IP");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
		}
		JSONObject job = new JSONObject();
		String resp = "Acknowledged";
		List<Object[]> objList = null;
		try {

			if (model != null && mid != null && tid != null && appName != null && serialNumber != null) {
				if (model.length() != 0 && mid.length() != 0 && tid.length() != 0) {
					objList = fetchProfile(model, tid, serialNumber, appName);
					if (objList.size() > 0) {
						resp = "Available";
						job.put("Reponsemessage", resp);
						for (Object[] o : objList) {
							job.put((String) o[0], (String) o[1]);
						}
					} else {
						resp = "Not available";
						job.put("Reponsemessage", resp);
					}
				} else {
					resp = "Not available";
					job.put("Reponsemessage", resp);
				}
			} else {
				resp = "Not available";
				job.put("Reponsemessage", resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			job.put("code", "1111");
			job.put("msg", "Exception.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setMid(mid);
			adt.setRequest(model+"|"+serialNumber+"|"+tid+"|"+mid+"|"+appName);
			adt.setTid(String.valueOf(tid));
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("serviceprofile");
			adt.setCorpId("N/A");

			adt.setSerialNum(serialNumber);
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		TxnLog txnN = UtilAccess.insertTxnLogs(AppProp.getProperty("act.profile"), "PROFILE RESPONSE : " + resp, mid,
				tid, model, serialNumber, null, null, null, null, null, null, null, null, null, null, null);

		entityManager.persist(txnN);

		return job;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> fetchProfile(String model, String tid, String serialNum, String appName) {
		List<Object[]> fileUploadProp = null;
		try {
			String query = DBUtil.fetchProfile();
			query = query.replaceAll("@MODEL", model);
			query = query.replaceAll("@APPNAME", appName);
			query = query.replaceAll("@TID", tid);
			AgLogger.logInfo(" PROFILE QUERY " + query);
			Query cb = entityManager.createNativeQuery(query);
			fileUploadProp = (List<Object[]>) cb.getResultList();
		} catch (Exception e) {
			AgLogger.logerror(null, " EXCEPTION  ", e);
		}
		return fileUploadProp;
	}

}
