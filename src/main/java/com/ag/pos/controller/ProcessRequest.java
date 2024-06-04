package com.ag.pos.controller;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ag.db.proc.DBProcUtil;
import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.PosRequestCalls;
import com.ag.mportal.model.PosRequestIpModel;
import com.ag.mportal.model.PosResponseOpModel;
import com.ag.mportal.services.PosRequestCallsService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.jdbc.OracleTypes;

@RestController
public class ProcessRequest {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	PosRequestCallsService posRequestCallsService;

	@PostMapping("/processRequest")
	public PosResponseOpModel processRequest(@RequestBody String requestService, HttpServletRequest request,
			@RequestParam("messageType") String messageType) throws Exception {
		String ipAddress = request.getHeader("IP");
		String userName = "";
		String password = "";
		PosResponseOpModel response = new PosResponseOpModel();

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		try {
			org.json.JSONObject onj = new org.json.JSONObject(requestService);
			if (messageType != null) {
				PosRequestCalls prt = posRequestCallsService.fetchByMessageType(messageType);
				if (prt != null) {
					boolean isValid = false;
					if (prt.getAuthEnable().equals("Y")) {

						try {
							userName = (String) onj.get(prt.getAuthIdParam());
						} catch (Exception e) {

						}

						try {
							password = (String) onj.get(prt.getAuthPassParam());
						} catch (Exception e) {

						}
						UserLogin userMdl = userLoginService.validateUserPassword(userName, password);

						if (!Objects.isNull(userMdl)) {
							isValid = true;
						} else {
							isValid = false;
						}

					} else {
						isValid = true;
					}

					// Proceed With Callable Statement
					if (isValid) {
						Type userListType = new TypeToken<ArrayList<PosRequestIpModel>>() {
						}.getType();
						ArrayList<PosRequestIpModel> userInputArray = new Gson().fromJson(prt.getFunctionIpParams(),
								userListType);
						ArrayList<PosRequestIpModel> userOutPutArray = new Gson().fromJson(prt.getFunctionOpParams(),
								userListType);

						if (userInputArray != null && userOutPutArray != null) {
							// Callable statment
							Connection con = null;
							try {
								con = DBProcUtil.getConnection();
								StringBuilder sb = new StringBuilder();
								sb.append("{call ");
								sb.append(prt.getFuncationName());
								sb.append("(");
								int length = userInputArray.size() + userOutPutArray.size();
								for (int i = 1; i <= length; i++) {
									sb.append("?");
									if (i != length) {
										sb.append(",");
									}
								}
								sb.append(")}");
								AgLogger.logInfo(sb.toString());
								CallableStatement cs = con.prepareCall(sb.toString());
								CallableStatement c = doProccessInputCS(cs, userInputArray, userOutPutArray, onj);
								c.execute();

								AgLogger.logInfo(prt.getFuncationName() + " RES ");
								int i = userInputArray.size() + 1;
								for (PosRequestIpModel lm : userOutPutArray) {
									AgLogger.logInfo(cs.getString(i++));
								}
								int responseCodeIndex = userInputArray.size() + 2;
								int responseDataIndex = userInputArray.size() + 1;
								String responseCode = cs.getString(responseCodeIndex);
								if (responseCode.equals(prt.getFunctionSuccessCode())) {
									response.setCode(responseCode);
									response.setMessage("Success");
									JSONParser parser = new JSONParser();
									try {
										response.setData((JSONObject) parser.parse(cs.getString(responseDataIndex)));
									} catch (ClassCastException cd) {
										response.setDataValue((JSONArray) parser.parse(cs.getString(responseDataIndex)));
									}

								} else {
									response.setCode(responseCode);
									response.setMessage("Something went wrong.");
								}

							} catch (Exception e) {
								e.printStackTrace();
								response.setCode("9996");
								response.setMessage("Something went wrong.");
							} finally {
								DBProcUtil.closeConnection(con);
							}

						} else {
							response.setCode("9995");
							response.setMessage("Something went wrong.");
						}
					} else {
						response.setCode("9993");
						response.setMessage("Authentication Failed.");
					}

				} else {
					response.setCode("9992");
					response.setMessage("Invalid Message Type.");

				}
			} else {
				response.setCode("9991");
				response.setMessage("Invalid Message Type.");

			}

		} catch (Exception e) {
			AgLogger.logerror(AckDiscountAvailed.class, " EXCEPTION  ", e);
			response.setCode("9999");
			response.setMessage("Failed");
		} finally {

			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(response.getCode() + "|" + response.getMessage());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("processPos");
			adt.setMid("");
			adt.setTid("");
			adt.setSerialNum("");
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}

		return response;

	}

	private CallableStatement doProccessInputCS(CallableStatement cs, ArrayList<PosRequestIpModel> input,
			ArrayList<PosRequestIpModel> output, org.json.JSONObject onj) throws JSONException, SQLException {
		int i = 1;
		for (PosRequestIpModel ls : input) {
			switch (ls.getType()) {
			case "varchar":
				String value = "na";
				if (ls.getValue().equals("DEFAULT")) {
					value = ls.getExtra();
				} else if (ls.getValue().equals("SERVICE")) {
					value = (onj.has(ls.getName()) ? onj.getString(ls.getName()) : "na");
				} else if (ls.getValue().equals("SYSTEM")) {
					value = "na";
				}
				cs.setString(i, value);
				AgLogger.logInfo("cs.setString(" + i + ", " + value + ")");
				i++;
				break;
				
			case "float":
				Float valueFloat = 0f;
				if (ls.getValue().equals("DEFAULT")) {
					valueFloat = Float.parseFloat(ls.getExtra());
				} else if (ls.getValue().equals("SERVICE")) {
					valueFloat = Float.parseFloat((onj.has(ls.getName()) ? onj.getString(ls.getName()) : "0"));
				} else if (ls.getValue().equals("SYSTEM")) {
					valueFloat = 0f;
				}
				cs.setFloat(i, valueFloat);
				AgLogger.logInfo("cs.setFloat(" + i + ", " + valueFloat + ")");
				i++;
				break;	
				
			case "number":
				int valueNumber = 0;
				if (ls.getValue().equals("DEFAULT")) {
					valueNumber =  Integer.parseInt(ls.getExtra());
				} else if (ls.getValue().equals("SERVICE")) {
					valueNumber = Integer.parseInt((onj.has(ls.getName()) ? onj.getString(ls.getName()) : "0"));
				} else if (ls.getValue().equals("SYSTEM")) {
					valueNumber = 0;
				}
				cs.setInt(i, valueNumber);
				AgLogger.logInfo("cs.setInt(" + i + ", " + valueNumber + ")");
				i++;
				break;		
				

			case "date":
				java.sql.Date dt = new java.sql.Date(new java.util.Date().getTime());
				cs.setDate(i, dt);
				AgLogger.logInfo("cs.setDate(" + i + ", " + dt + ")");
				i++;
				break;
			}

		}
		for (PosRequestIpModel ls : output) {
			switch (ls.getType()) {
			case "varchar":
				cs.registerOutParameter(i, OracleTypes.VARCHAR);
				AgLogger.logInfo("cs.registerOutParameter(" + i + ", OracleTypes.VARCHAR)");
				i++;
				break;
			}
		}
		return cs;
	}

}
