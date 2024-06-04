package com.ag.pos.fuel.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.db.proc.DBProcUtil;
import com.ag.db.proc.DBProceduresFuel;
import com.ag.db.proc.FuelProcModelDetails;
import com.ag.fuel.model.ChangePinServiceModel;
import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.LovDetail;
import com.ag.generic.entity.LovMaster;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.loyalty.model.LovAdvertisment;
import com.ag.generic.loyalty.model.ModulesModel;
import com.ag.generic.loyalty.model.ProductsModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.service.LovService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HSMUtil;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.UtilAccess;
import com.google.gson.Gson;

import oracle.jdbc.internal.OracleTypes;

@RestController
public class FuelChangePin {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogService auditLogService;

	@Autowired
	LovService lovService;

	@PostMapping({ "/FuelCP" })
	public ResponseModel doProcess(@RequestBody JSONObject requestService, HttpServletRequest request) {
		AgLogger.logInfo("Fuel Change Pin");

		return doProcessData(requestService, request);
	}

	public ResponseModel doProcessData(JSONObject requestService, HttpServletRequest request) {
		ResponseModel response = new ResponseModel();
		String ipAddress = request.getHeader("IP");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		try {
			
			System.out.println("REQ: "+requestService);

			String cardNo = requestService.get("cardNo").toString();
			String currentPin = requestService.get("currentPin").toString();
			String newPin = requestService.get("newPin").toString();
			String confirmPin = requestService.get("confirmPin").toString();

			String gj = String.valueOf(System.currentTimeMillis()).substring(2, 13);

			String sk = HSMUtil.doSend(cardNo, currentPin);
			System.out.println(" " + cardNo + " HSM CALL " + sk);
			String trimedText = sk.substring(4, sk.length());
			String res = "0000";
			String resMessage = "-";
			String rrn = "-";
			HashMap<Object, Object> o = new HashMap<Object, Object>();
			if (newPin.equals(confirmPin)) {

				if (trimedText.startsWith("DF00")) {
					String hsmcode = trimedText.substring(4, trimedText.length());

					String tempPinDb = "";
					String responseProc = fuelAppProcess(null, "posoffset", 0, null, cardNo, 0, null);

					FuelProcModelDetails mdl = new Gson().fromJson(responseProc, FuelProcModelDetails.class);
					tempPinDb = mdl.getData().get("offset").toString();

					AgLogger.logInfo(hsmcode + "|" + tempPinDb);
					if (hsmcode.equals(tempPinDb)) {						
						res = "0000";
						resMessage = "PIN Matched";						

						String pinChangeURL = AppProp.getProperty("changePinUrl") + "card=" + cardNo + "&pin=" + newPin
								+ "&t=001";
						AgLogger.logInfo("PIN CHANGE URL: "+pinChangeURL);
						String respData = new HttpUtil().doGet(pinChangeURL);
						AgLogger.logInfo(respData);
						String code = respData;

						ChangePinServiceModel tempModel = new Gson().fromJson(respData, ChangePinServiceModel.class);
						if (null != tempModel.getResponce() && tempModel.getResponce().equals("0000")) {
							rrn = UtilAccess.rrn();
							o.put("rrn", rrn);
							res = "0000";
							resMessage = "Pin Changes Successfully.";

						} else {

							res = "1111";
							resMessage = "Could Not Change Pin";
						}

					} else {
						res = "0001";
						resMessage = "PIN NOT Matched";
					}

				} else {
					res = "1111";
					resMessage = "INVALID HSM CALL";
				}
			} else {
				res = "1111";
				resMessage = "Pin and Confirm New Pin must be same.";
			}
			
			response.setCode(res);
			response.setMessage(resMessage);
			response.setData(o);

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
			adt.setTxnType("Fuel Change Pin");
			adt.setMid(requestService.get("mid").toString());
			adt.setSerialNum(requestService.get("terminalSerial").toString());
			adt.setCorpId("N/A");
			try {
				auditLogService.insertAuditLog(adt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return response;
	}
	
	public static String fuelAppProcess(String corpId, String messageType, int userId, String userCode,
			String cardNumber, int grpCode, String newStatus) {
		String resProc = "9999";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + corpId + "|" + messageType + "|" + userId + "|" + userCode + "|" + cardNumber
					+ "|" + grpCode + "|" + newStatus);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call FUEL_X990(?,?,?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, messageType);
			cs.setInt(3, userId);
			cs.setString(4, userCode);
			cs.setString(5, cardNumber);
			cs.setInt(6, grpCode);
			cs.setString(7, newStatus);

			cs.registerOutParameter(8, OracleTypes.CLOB);
			cs.execute();
		

			resProc = cs.getString(8);
			AgLogger.logInfo("RESPONSE " + resProc);
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resProc = "9999";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return resProc;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private static JSONObject createDataObject(String key, String value) {
		JSONObject dataObject = new JSONObject();
		dataObject.put(key, value);
		return dataObject;
	}
}
