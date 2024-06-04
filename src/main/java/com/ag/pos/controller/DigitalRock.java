package com.ag.pos.controller;

import java.net.URLEncoder;
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
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.EcrUtil;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ConfigEcrRouting;
import com.ag.mportal.entity.EcrLog;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.model.EcrModel;
import com.ag.mportal.model.EcrRespModel;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.PosEntryModeConfigService;
import com.ag.mportal.services.RocConfigurationService;
import com.ag.mportal.services.TxnDetailsService;
import com.ag.mportal.services.impl.ConfigEcrRoutingServiceImpl;
import com.ag.mportal.services.impl.EcrLogsServiceImpl;
import com.google.gson.Gson;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

@RestController
public class DigitalRock {

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	RocConfigurationService rocConfigurationService;

	@Autowired
	PosEntryModeConfigService posEntryModeConfigService;

	@Autowired
	UtilService utilService;

	@SuppressWarnings("unchecked")
	@PostMapping("/digitalRock")
	public JSONObject GenerateSms(@RequestBody String requestService, HttpServletRequest request) throws Exception {

		AgLogger.logInfo("DIGITAL ROCK CALLED");

		String ipAddress = request.getHeader("IP");
		String mid = "";
		String tid = "";
		String serialNumber = "";
		String mobileNumber = "";
		String batchNumber = "";
		String invoiceNumber = "";

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();

		try {

			JSONObject onj = (JSONObject) JSONValue.parse(requestService);

			AgLogger.logInfo("DIGITAL ROCK REQUEST: " + onj.toString());

			mid = (String) onj.get("mid");
			tid = (String) onj.get("tid");
			serialNumber = (String) onj.get("serialNumber");
			mobileNumber = (String) onj.get("mobileNumber");
			batchNumber = (String) onj.get("batchNumber");
			invoiceNumber = (String) onj.get("invoiceNumber");

			TxnDetail txn = txnDetailsService.fetchTxnDetail(batchNumber, tid, invoiceNumber);
			if (txn != null) {

				ReportModel txnDetail = txnDetailsService.fetchTxnDetailByIdForRoc(String.valueOf(txn.getId()));

				String reportPath = rocConfigurationService.fetch(txnDetail.getType(), txnDetail.getPosEntryMode(),
						txnDetail.getModel(), "00004");

				PosEntryModeConfig pc = posEntryModeConfigService.fetchByMode(txnDetail.getPosEntryMode());

				ResponseModel response = UtilAccess.downloadRoc(txnDetail, reportPath, pc);

				String originalUrl = "https://demo.accessgroup.mobi/fuelrevampdev/imgDownload?url="
						+ response.getData().get("imagePath") + "&id=1";

				String encodedUrl = URLEncoder.encode(originalUrl, "UTF-8");

				String url = new HttpUtil().doGet("https://ulvis.net/api.php?url=" + encodedUrl + "&private=1");

				utilService.doSendSmsOnly(mobileNumber,
						"Dear Customer, Thank you for your transaction. Please visit the following URL to download your receipt:  "
								+ url,
						"default");

				job.put("code", "0000");
				job.put("message", "Success.");
			} else {
				job.put("code", "0001");
				job.put("message", "No Transaction Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			job.put("code", "9999");
			job.put("message", "Something Went Wrong.");
		} finally {
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(new Timestamp(new Date().getTime()));
			adt.setRequest(requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("DIGITAL-ROCK");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serialNumber);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);

		}
		AgLogger.logInfo("DIGITAL ROCK RESPONSE: " + job.toString());
		return job;

	}

}
