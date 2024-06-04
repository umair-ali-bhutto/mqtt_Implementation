package com.ag.pos.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.FileDownload;

@RestController
public class ProcessAppLists {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	AuditLogService auditLogService;

	@SuppressWarnings("unchecked")
	@GetMapping("/fetchApps")
	public JSONObject fetchApps(@RequestParam("SerialNumber") String serial, @RequestParam("MID") String mid,
			@RequestParam("TID") String tid, HttpServletRequest request) throws Exception {
		String ipAddress = request.getHeader("IP");
		List<FileDownload> dfList = null;
		String[] resp = new String[2];
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();

		try {

			dfList = fetchFilesAppStore();
			AgLogger.logInfo("", dfList.size() + ".......");
			if (dfList.size() != 0) {
				resp[0] = "0000";
				resp[1] = "SUCCESS";
			} else {
				resp[0] = "0001";
				resp[1] = "No Apps Found.";
			}

		} catch (Exception e) {
			AgLogger.logerror(ProcessAppLists.class, " EXCEPTION  ", e);
			resp[0] = "9999";
			resp[1] = "Failed";
		} finally {
			job.put("code", resp[0]);
			job.put("message", resp[1]);
			if (dfList.size() != 0) {
				JSONArray jarray = new JSONArray();
				for (FileDownload d : dfList) {
					JSONObject jObj = new JSONObject();
					jObj.put("isDependent", d.getIsDependent());
					jObj.put("bundle", d.getBundleId());
					jObj.put("id", d.getId());
					jObj.put("name", d.getAppName());
					byte[] fileContent1 = FileUtils.readFileToByteArray(new File(d.getAppIcon()));
					String encodedImg1 = Base64.getEncoder().encodeToString(fileContent1);
					jObj.put("icon", encodedImg1);
					jObj.put("url",
							new String(java.util.Base64.getEncoder().encode(String.valueOf(d.getId()).getBytes())));

					jarray.add(jObj);
				}

				job.put("data", jarray);
			}

			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(mid + "|" + tid + "|" + serial);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("Fetch APIS");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}

		return job;

	}

	public List<FileDownload> fetchFilesAppStore() {

		try {
			String query = "SELECT * FROM FILE_DOWNLOAD WHERE RELEASE_TYPE = 'APP_STORE'";

			AgLogger.logInfo("FETCH VERSION QUERY " + query);

			Query cb = entityManager.createNativeQuery(query, FileDownload.class);
			List<FileDownload> f = (List<FileDownload>) cb.getResultList();
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logerror(getClass(), " EXCEPTION  ", e);
		}
		return null;
	}

}
