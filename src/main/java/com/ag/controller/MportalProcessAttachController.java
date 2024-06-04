package com.ag.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.JwtTokenUtil;
import com.ag.generic.util.MaskedUtil;
import com.ag.generic.util.UtilAG;
import com.ag.generic.util.UtilAccess;
import com.ag.generic.util.Validator;
import com.google.gson.Gson;

@RestController
public class MportalProcessAttachController {

	@Autowired
	private ApplicationContext context;

	@Autowired
	public UserLoginService userLoginService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	AuditLogService auditLogServ;

	@CrossOrigin()
	@PostMapping("/process/attachments")
	public ResponseModel doProcessAttachments(@RequestParam(value = "file") MultipartFile file,
			@RequestHeader("token_auth") String tokenAuth, @RequestHeader("user_code") String userCode,
			@RequestHeader("user_id") int userId, @RequestParam(value = "data") String requestService,
			HttpServletResponse response, HttpServletRequest request) {
		RequestModel rms = new RequestModel();
		String ipAddress = request.getHeader("IP");

		if (ipAddress == null) {

			ipAddress = request.getRemoteAddr();
		}
		System.out.println(ipAddress);

		ResponseModel rmResponse = UtilAccess.generateResponse("9994", "INVALID TOKEN.");
		Wisher wisher = null;
		try {
			byte[] fileBytes = file.getBytes();
			if (tokenAuth != null && tokenAuth.startsWith("Bearer ")) {
				tokenAuth = tokenAuth.substring(7);

				String clearReq = requestService.toString().replaceAll("\\n", "");
				rms = getConvrsion(clearReq);
				if (clearReq == null) {
					rmResponse = UtilAccess.generateResponse("AG.00003", "INVALID REQUEST");
				} else {
					UserLogin user = userLoginService.validetUser(userCode, rms.getCorpId());
					boolean isJwtMgtEnable = AppProp.getProperty("enable.jwt.mgt").trim().equals("1");

					boolean isAuth = false;
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
							rms.getAdditionalData().put("file", fileBytes);
							rmResponse = new Validator().doValidate(rms);
							if (rmResponse.getCode().equals("0000")) {
								wisher = setupClass(UtilAG.getMesssageType(rms.getMessage()));
								if (!Objects.isNull(wisher)) {
									rmResponse = doCallClass(rms, wisher);
								} else {
									AgLogger.logInfo(
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
			adt.setRequest(requestService);
			adt.setResponse(new Gson().toJson(rmResponse));
			adt.setRequestMode("PORTAL");
			adt.setRequestIp(ipAddress);
			adt.setCorpId(rms.getCorpId());

			auditLogServ.insertAuditLog(adt);

		}

		AgLogger.logDebug("0", "Request " + requestService + " ~ tokenAuth: " + tokenAuth + " |@@| userCode: "
				+ userCode + " |@@| userId: " + userId);
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
