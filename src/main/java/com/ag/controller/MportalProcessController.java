package com.ag.controller;

import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.EncDecUtl;
import com.ag.generic.util.JwtTokenUtil;
import com.ag.generic.util.MaskedUtil;
import com.ag.generic.util.UtilAG;
import com.ag.generic.util.UtilAccess;
import com.ag.generic.util.Validator;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.PosEntryModeConfigService;
import com.ag.mportal.services.RocConfigurationService;
import com.ag.mportal.services.TxnDetailsService;
import com.google.gson.Gson;

@RestController
public class MportalProcessController {

	@Autowired
	private ApplicationContext context;

	@Autowired
	public UserLoginService userLoginService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	AuditLogService auditLogServ;

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	RocConfigurationService rocConfigurationService;

	@Autowired
	PosEntryModeConfigService posEntryModeConfigService;

	@Autowired
	UtilAccess utilAccess;

	@CrossOrigin()
	@PostMapping("/process")
	public ResponseModel doProcess(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestBody String requestService, @RequestHeader("token_auth") String tokenAuth,
			@RequestHeader("user_code") String userCode, @RequestHeader("user_id") int userId,
			HttpServletResponse response, HttpServletRequest request) {

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

		RequestModel rms = new RequestModel();

		String ipAddress = request.getHeader("IP");

		if (ipAddress == null) {

			ipAddress = request.getRemoteAddr();
		}

		UserLogin user = null;
		ResponseModel rmResponse = UtilAccess.generateResponse("9994", "INVALID TOKEN.");
		Wisher wisher = null;
		String clearReq = null;
		try {
			if (tokenAuth != null && tokenAuth.startsWith("Bearer ")) {
				tokenAuth = tokenAuth.substring(7);
				clearReq = requestService.toString().replaceAll("\\n", "");
				if (clearReq == null) {
					rmResponse = UtilAccess.generateResponse("AG.00003", "INVALID REQUEST");
				} else {

					clearReq = EncDecUtl.doDecrypt(
							Base64.getDecoder().decode(requestService.toString().replaceAll("\\n", "")), userAgent);
					rms = getConvrsion(clearReq);
					user = userLoginService.validetUser(userCode, rms.getCorpId());
					boolean isJwtMgtEnable = AppProp.getProperty("enable.jwt.mgt").trim().equals("1");
					boolean isAuth = false;
					
					AgLogger.logInfo(userCode+""+rms.getCorpId());
					
					AgLogger.logInfo(user+"USER@@@@@@");
					
					
					if(user.getToken() == null) {
						user.setToken(tokenAuth);
						user.setTokenGenerationTime(new Timestamp(jwtTokenUtil.getIssuedAtDateFromToken(tokenAuth).getTime()));
						userLoginService.updateUser(user);
					}

					if (isJwtMgtEnable) {
						isAuth = isJwtMgtEnable
								? (user.getToken().equals(tokenAuth) && user.getImeiUuid().equals(rms.getImei())
										&& jwtTokenUtil.validateToken(tokenAuth, userCode, userId))
								: true;
						AgLogger.logDebug("@@@ TOKEN AUTH " + user.getUserCode(),
								user.getToken().equals(tokenAuth) + "..." + user.getImeiUuid().equals(rms.getImei())
										+ "..." + jwtTokenUtil.validateToken(tokenAuth, userCode, userId));
					} else {
						isAuth = true;
					}

					if (isAuth) {
						if (UtilAccess.isMsgLogin(rms.getMessage()) == false) {
							rmResponse = new Validator().doValidate(rms);
							if (rmResponse.getCode().equals("0000")) {
								wisher = setupClass(UtilAG.getMesssageType(rms.getMessage()));
								if (!Objects.isNull(wisher)) {
									rmResponse = doCallClass(rms, wisher);
								} else {
									AgLogger.logDebug("",
											"CLASS NOT INITIALIZED -> " + UtilAG.getMesssageType(rms.getMessage()));
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
						rmResponse = UtilAccess.generateResponse("9994", "INVALID TOKEN.");
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				}
			} else {
				rmResponse = UtilAccess.generateResponse("9993", "AUTHENTICATION FAILED.");
			}

		} catch (Exception e) {
			rmResponse = UtilAccess.generateResponse("9993", "BAD REQUEST");
			e.printStackTrace();
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId(String.valueOf(userId));
			adt.setEntryDate(time);
			adt.setRequest(clearReq);
			if (isSaveRequestMessage(rms)) {
				ResponseModel tempResponse = new ResponseModel();
				tempResponse.setCode(rmResponse.getCode());
				tempResponse.setMessage(rmResponse.getMessage());
				adt.setResponse(new Gson().toJson(tempResponse));
			} else {
				adt.setResponse(new Gson().toJson(rmResponse));
			}
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

		AgLogger.logDebug("0", "Request " + MaskedUtil.getRequest(clearReq) + " ~ tokenAuth: " + tokenAuth
				+ " |@@| userCode: " + userCode + " |@@| userId: " + userId + " |@@| userAgent: " + userAgent);
		AgLogger.logDebug("#######", rms.getMessage() + "|" + isPrintRequestMessage(rms));
		if (!isPrintRequestMessage(rms)) {
			AgLogger.logDebug("0", "Response " + new Gson().toJson(rmResponse));
		}

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

	// nrt - not required token
	@CrossOrigin()
	@PostMapping("/process/nrt")
	public ResponseModel doProcess(@RequestHeader(value = "User-Agent") String userAgent,
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
		// AgLogger.logInfo("NRT CALL");

		RequestModel rms = new RequestModel();

		String ipAddress = request.getHeader("IP");

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		ResponseModel rmResponse = UtilAccess.generateResponse("9994", "INVALID TOKEN.");
		Wisher wisher = null;
		String clearReq = null;
		try {
			clearReq = requestService.toString().replaceAll("\\n", "");
			if (clearReq == null) {
				rmResponse = UtilAccess.generateResponse("AG.00003", "INVALID REQUEST");
			} else {
				clearReq = EncDecUtl.doDecrypt(
						Base64.getDecoder().decode(requestService.toString().replaceAll("\\n", "")), userAgent);
				rms = getConvrsion(clearReq);

				if (UtilAccess.isMsgLogin(rms.getMessage()) == false) {
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
			}

		} catch (Exception e) {
			rmResponse = UtilAccess.generateResponse("9993", "BAD REQUEST");
			e.printStackTrace();
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setEntryDate(time);
			adt.setRequest(clearReq);
			adt.setResponse(new Gson().toJson(rmResponse));
			adt.setRequestMode(rms.getChannel());
			adt.setRequestIp(ipAddress);
			adt.setTxnType(rms.getMessage());
			adt.setCorpId(rms.getCorpId());
			try {
				auditLogServ.insertAuditLog(adt);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		AgLogger.logDebug("0", "Request " + clearReq + " ~ " + " UserAgent: " + userAgent);
		AgLogger.logDebug("0", "Response " + new Gson().toJson(rmResponse));
		return rmResponse;
	}

	@CrossOrigin()
	@GetMapping("/process/RocDown")
	public ResponseEntity<byte[]> doProcessRocImages(@RequestParam(name = "id") String tid,
			@RequestParam(name = "corpId") String corpId, HttpServletRequest request) {
		AgLogger.logInfo("ROC CALL");
		Gson gson = new Gson();
		ResponseModel response = new ResponseModel();

		String ipAddress = request.getHeader("IP");

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		try {

			String tempTId = new String(java.util.Base64.getDecoder().decode(tid));

			ReportModel txnDetail = txnDetailsService.fetchTxnDetailById(tempTId);

			if (Objects.isNull(txnDetail)) {
				response.setCode("0003");
				response.setMessage("Record not found.");
				return ResponseEntity.ok().body(gson.toJson(response).getBytes());
			}

			String reportPath = rocConfigurationService.fetch(txnDetail.getType(), txnDetail.getPosEntryMode(),
					txnDetail.getModel(), corpId);

			PosEntryModeConfig pc = posEntryModeConfigService.fetchByMode(txnDetail.getPosEntryMode());

			response = utilAccess.downloadRocForApp(txnDetail, reportPath, pc);

			if (response.getCode().equals("0000")) {
				InputStreamResource imageFile = new InputStreamResource(
						new FileInputStream(response.getData().get("rocImagePath").toString()));
				byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
				return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
			} else {
				return ResponseEntity.badRequest().build();
			}

			// return ResponseEntity.ok().body(gson.toJson(response).getBytes());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setEntryDate(time);
			adt.setRequest("RocDown");
			adt.setResponse(new Gson().toJson(response));
			adt.setRequestMode("android");
			adt.setRequestIp(ipAddress);
			adt.setTxnType(null);
			adt.setCorpId(corpId);
			try {
				auditLogServ.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Value("${debug.message.type.print}")
	public String debugMessageType;

	public boolean isPrintRequestMessage(RequestModel rm) {
		String[] dSplit = debugMessageType.split(",");
		for (String dpString : dSplit) {
			if (dpString.equals(rm.getMessage())) {
				return true;
			}
		}
		return false;
	}

	@Value("${debug.message.type.log.disable.db}")
	public String debugMessageTypeDB;

	public boolean isSaveRequestMessage(RequestModel rm) {
		String[] dSplit = debugMessageTypeDB.split(",");
		for (String dpString : dSplit) {
			if (dpString.equals(rm.getMessage())) {
				return true;
			}
		}
		return false;
	}
}
