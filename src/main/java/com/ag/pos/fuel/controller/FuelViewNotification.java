package com.ag.pos.fuel.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.BroadCastMessageModel;
import com.ag.generic.model.NotificationModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;

@RestController
public class FuelViewNotification {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@PostMapping({ "/FuelViewNotification" })
	public ResponseModel doProcessFileAuth(@RequestBody JSONObject requestService, HttpServletRequest request) {
		AgLogger.logInfo("Fuel View Notification");
		return doProcessData(requestService, request);
	}

	public ResponseModel doProcessData(JSONObject requestService, HttpServletRequest request) {
		ResponseModel response = new ResponseModel();
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		try {
			UserLogin user = userLoginService.validateUserPassword(requestService.get("userName").toString(),
					requestService.get("password").toString());
			if (user != null) {
				String mid = requestService.get("mid").toString();
				String tid = requestService.get("tid").toString();

				Date tempDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String date = sdf.format(tempDate);

				List<BroadCastMessageModel> notificationList = new ArrayList<BroadCastMessageModel>();

				// object 1
				BroadCastMessageModel b1 = new BroadCastMessageModel();
				b1.setBROADCAST_DETAIL_ID("1001");
				b1.setMESSAGE("Get 50% on latest bundle offers.");
				b1.setMESSAGE_TITLE("Message");
				b1.setMESSAGE_DETIAL("This is a testing message.");
				b1.setENTRY_DATE(date);
				
				notificationList.add(b1);
				
				// object 2
				BroadCastMessageModel b2 = new BroadCastMessageModel();
				b2.setBROADCAST_DETAIL_ID("1001");
				b2.setMESSAGE("Get 100% Discount on latest the latest bundle offers present to see more details kindly see this notification.");
				b2.setMESSAGE_TITLE("Message 2");
				b2.setMESSAGE_DETIAL("This is a second testing message.");
				b2.setENTRY_DATE(date);
				
				notificationList.add(b2);

				if (notificationList.size() != 0) {
					HashMap<Object, Object> map = new HashMap<Object, Object>();
					map.put("notifications", notificationList);

					response.setCode("0000");
					response.setMessage("SUCCESS");
					response.setData(map);
				} else {
					response.setCode("0001");
					response.setMessage("No Data Found.");
				}

			} else {
				response.setCode("9991");
				response.setMessage("Invalid Credentials.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		} finally {
			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setTid(requestService.get("tid").toString());
			adt.setEntryDate(time);
			adt.setRequest(requestService.toString());
			adt.setResponse(new Gson().toJson(response));
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("Fuel View Notification");
			adt.setMid(requestService.get("mid").toString());
			adt.setSerialNum("N/A");
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return response;
	}

}
