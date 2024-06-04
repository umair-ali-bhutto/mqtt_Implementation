package com.ag.controller;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.EncDecUtl;
import com.ag.generic.util.MaskedUtil;
import com.ag.generic.util.UtilAG;
import com.ag.generic.util.UtilAccess;
import com.ag.generic.util.Validator;
import com.google.gson.Gson;

@RestController
public class MportalLoginController {

	@Autowired
	private ApplicationContext context;

	@Autowired
	public UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogServ;

	@GetMapping("/process")
	public ResponseModel doProcessGet() {
		return UtilAccess.generateResponse("9994", "TEST.");
	}

	@CrossOrigin()
	@PostMapping("/login")
	public ResponseModel doProcessLogin(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody String requestService, HttpServletRequest request) {

		if (userAgent != null) {
			if (userAgent.contains("iOS")) {
				try {
					JSONParser parser = new JSONParser();
					org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(requestService);
					requestService = json.get("data").toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		String ipAddress = request.getHeader("IP");
		String clearReqFin = "";
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		ResponseModel rmResponse = UtilAccess.generateResponse("9993", "BAD REQUEST");
		Wisher wisher = null;
		RequestModel rms = new RequestModel();
		try {
			String clearReq = requestService.toString().replaceAll("\\n", "");
			if (clearReq != null) {
				clearReq = EncDecUtl.doDecrypt(
						Base64.getDecoder().decode(requestService.toString().replaceAll("\\n", "")), userAgent);
				clearReqFin = clearReq;
				rms = getConvrsion(clearReq);

				if (UtilAccess.isMsgLogin(rms.getMessage()) == true) {
					rmResponse = new Validator().doValidate(rms);
					if (rmResponse.getCode().equals("0000")) {
						wisher = setupClass(UtilAG.getMesssageType(rms.getMessage()));
						if (!Objects.isNull(wisher)) {
							rmResponse = doCallClass(rms, wisher);
						} else {
							AgLogger.logInfo("CLASS NOT INITIALIZED -> " + UtilAG.getMesssageType(rms.getMessage()));
							rmResponse.setCode("9991");
							rmResponse.setMessage("ERROR OCCURED.");
						}
					} else {
						rmResponse = UtilAccess.generateResponse(rmResponse.getCode(), rmResponse.getMessage());
					}

				} else {
					rmResponse = UtilAccess.generateResponse("AG.00002", "INVALID REQUEST");

				}
			} else {
				rmResponse = UtilAccess.generateResponse("AG.00002", "INVALID REQUEST");

			}
		} catch (Exception e) {
			e.printStackTrace();
			rmResponse = UtilAccess.generateResponse("9993", "BAD REQUEST");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId(rms.getUserid());
			adt.setEntryDate(time);
			adt.setRequest(clearReqFin);
			adt.setResponse(new Gson().toJson(rmResponse));
			adt.setRequestMode("PORTAL");
			adt.setRequestIp(ipAddress);
			adt.setTxnType(rms.getMessage());
			adt.setCorpId(rms.getCorpId());

			try {
				auditLogServ.insertAuditLog(adt);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		AgLogger.logDebug("0", "Request " + clearReqFin + " ~ " + " userAgent: " + userAgent);
		AgLogger.logDebug("0", "Response " + new Gson().toJson(rmResponse));
		return rmResponse;

	}

	RequestModel getConvrsion(String clearReq) {
		RequestModel obj = null;
		try {
			obj = new Gson().fromJson(clearReq, RequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	Wisher setupClass(String className) {
		Wisher wisher = null;
		try {
			wisher = (Wisher) context.getBean(className);
			// AgLogger.logInfo("CLASS SETUP FOR -> " + wisher.getClass());
		} catch (Exception ex) {
			AgLogger.logInfo("SETUP CLASS EXCEPTION: " + ex);
		}
		return wisher;
	}

	ResponseModel doCallClass(RequestModel rms, Wisher wisher) {
		ResponseModel rmks;
		try {
			rmks = wisher.doProcess(rms);
		} catch (Exception e) {
			rmks = new ResponseModel();
			rmks.setCode("9991");
			rmks.setMessage("ERROR OCCURED.");
		}
		return rmks;
	}

}
