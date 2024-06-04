package com.ag.pos.controller;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ag.db.proc.DBProcUtil;
import com.ag.generic.entity.AuditLog;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.impl.AuditLogServiceImpl;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.services.ProvidersConfigService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.jdbc.internal.OracleTypes;

@RestController
public class ProcessAdvertisment {

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	AuditLogServiceImpl auditLogService;

	@Autowired
	ProvidersConfigService ProvidersConfigService;

	@SuppressWarnings("unchecked")
	@PostMapping("/retrieveAdvertisment")
	public JSONObject retrieveAdvertisment(@RequestBody String requestService, HttpServletRequest request)
			throws Exception {
		String ipAddress = request.getHeader("IP");
		String userName = "";
		String password = "";
		String mid = "";
		String tid = "";
		String serial = "";
		String[] resp = new String[2];
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		List<Model> lstResult = new ArrayList<Model>();
		
		JSONObject job = new JSONObject();

		try {
			if (requestService.length() != 0) {
				requestService = java.net.URLDecoder.decode(requestService, StandardCharsets.UTF_8.name());
				JSONObject onj = (JSONObject) JSONValue.parse(requestService);
				userName = (String) onj.get("UserName");
				password = (String) onj.get("PASSWORD");
				mid = (String) onj.get("MID");
				tid = (String) onj.get("TID");
				serial = (String) onj.get("SerialNumber");
				UserLogin user = userLoginService.validateUserPassword(userName, password);
				if (!Objects.isNull(user)) {
					lstResult = process(mid, tid);
					AgLogger.logInfo(lstResult.size()+"..........");
					if (lstResult.size()!=0) {
						resp[0] = "0000";
						resp[1] = "Success.";
					}
					else {
						resp[0] = "0002";
						resp[1] = "No Advertisement Found.";
					}
				} else {
					resp[0] = "0001";
					resp[1] = "Invalid User Name or Password.";
				}
			} else {
				resp[0] = "0004";
				resp[1] = "Invalid Data";
			}
		} catch (Exception e) {
			AgLogger.logerror(AckDiscountAvailed.class, " EXCEPTION  ", e);
			resp[0] = "9999";
			resp[1] = "Failed";
		} finally {
			job.put("code", resp[0]);
			job.put("message", resp[1]);
			if (lstResult.size()!=0) {
				JSONArray jarray = new JSONArray();
				for (Model d : lstResult) {
					JSONObject jObj = new JSONObject();
					jObj.put("ID", d.getID());
					jObj.put("URL", d.getMEDIA_URL());
					jarray.add(jObj);
				}

				job.put("data", jarray);
			}

			Date date = new Date();
			Timestamp time = new Timestamp(date.getTime());
			AuditLog adt = new AuditLog();
			adt.setUserId("0");
			adt.setEntryDate(time);
			adt.setRequest(requestService);
			adt.setResponse(job.toString());
			adt.setRequestMode("POS");
			adt.setRequestIp(ipAddress);
			adt.setTxnType("retrieveProviders");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}
		return job;

	}

	public class Model {
		private String ID;
		private String Version;
		private String AdvType;
		private String NoOfMedia;
		private String BIGINT;
		private String MEDIA_URL;

		public String getID() {
			return ID;
		}

		public void setID(String iD) {
			ID = iD;
		}

		public String getVersion() {
			return Version;
		}

		public void setVersion(String version) {
			Version = version;
		}

		public String getAdvType() {
			return AdvType;
		}

		public void setAdvType(String advType) {
			AdvType = advType;
		}

		public String getNoOfMedia() {
			return NoOfMedia;
		}

		public void setNoOfMedia(String noOfMedia) {
			NoOfMedia = noOfMedia;
		}

		public String getBIGINT() {
			return BIGINT;
		}

		public void setBIGINT(String bIGINT) {
			BIGINT = bIGINT;
		}

		public String getMEDIA_URL() {
			return MEDIA_URL;
		}

		public void setMEDIA_URL(String mEDIA_URL) {
			MEDIA_URL = mEDIA_URL;
		}
	}
	
	public static List<Model> process(String mid, String tid) {
		List<Model> response = new ArrayList<Model>();

		String pRespCode = "00001";
		String Message = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST  00000 | MID:" + mid + "|TID:" + tid + "|DATE:"+new java.sql.Date(new Date().getTime())+"|Y|1234678");
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_ADV(?,?,?,?,?,?,?,?)}");
			cs.setString(1, "00000");
			cs.setString(2, mid);
			cs.setString(3, tid);
			cs.setDate(4, new java.sql.Date(new Date().getTime()));
			cs.setString(5, "Y");
			cs.setString(6, "1234678");

			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.registerOutParameter(8, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("RESPONESE " + cs.getString(7) + "|" + cs.getString(8));

			pRespCode = cs.getString(8);
			Message = cs.getString(7);

			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
				String tMessage = cs.getString(7);
				Type userListType = new TypeToken<ArrayList<Model>>() {
				}.getType();
				response = new Gson().fromJson(tMessage, userListType);

			} else {
				pRespCode = "9998";
				Message = "Unable to Perform Transaction.";
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
			Message = "Exception " + e.getMessage();
		}
		return response;
	}

}
