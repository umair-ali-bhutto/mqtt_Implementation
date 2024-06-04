package com.ag.pos.controller;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ag.db.proc.DBProcUtil;
import com.ag.generic.entity.AuditLog;
import com.ag.generic.service.AuditLogService;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.jdbc.internal.OracleTypes;

@RestController
public class ProcessPosApiConfiguration {

	@Autowired
	AuditLogService auditLogService;

	@SuppressWarnings("unchecked")
	@GetMapping("/posApiConfiguration")
	public JSONObject retrieveDiscountRate(@RequestParam("SerialNumber") String serial, @RequestParam("MID") String mid,
			@RequestParam("TID") String tid, HttpServletRequest request) throws Exception {
		String ipAddress = request.getHeader("IP");
		List<ProcedureModel> lstResult = null;
		String[] resp = new String[2];
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		JSONObject job = new JSONObject();

		try {

			lstResult = process(mid, tid);
			if (lstResult != null) {
				AgLogger.logDebug("", lstResult.size() + "........");
				if (lstResult.size() != 0) {
					resp[0] = "0000";
					resp[1] = "SUCCESS";
				} else {
					resp[0] = "0003";
					resp[1] = "No Record Found.";
				}
			} else {
				resp[0] = "0004";
				resp[1] = "No Record Found.";
			}

		} catch (Exception e) {
			AgLogger.logerror(ProcessPosApiConfiguration.class, " EXCEPTION  ", e);
			resp[0] = "9999";
			resp[1] = "Failed";
		} finally {
			job.put("code", resp[0]);
			job.put("message", resp[1]);
			if (lstResult != null) {
				JSONArray jarray = new JSONArray();
				for (ProcedureModel d : lstResult) {
					JSONObject jObj = new JSONObject();
					jObj.put("mid", d.getMID());
					jObj.put("tid", d.getTID());
					jObj.put("apiId", d.getAPIID());
					jObj.put("apiName", d.getAPINAME());
					jObj.put("bundleId", d.getUID());
					jObj.put("userName", d.getUSERID());
					jObj.put("password", d.getPASSWORD_hash());
					jObj.put("version", d.getAPIVER());
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
			adt.setTxnType("retrievePosService");
			adt.setMid(mid);
			adt.setTid(tid);
			adt.setSerialNum(serial);
			adt.setCorpId("N/A");
			auditLogService.insertAuditLog(adt);
		}

		return job;

	}

	public static List<ProcedureModel> process(String mid, String tid) {
		List<ProcedureModel> response = new ArrayList<ProcedureModel>();

		String pRespCode = "00001";
		String Message = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + mid + "|" + tid);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_API_ACCESS(?,?,?,?,?,?,?)}");
			cs.setString(1, "00000");
			cs.setString(2, mid);
			cs.setString(3, tid);
			cs.setDate(4, new java.sql.Date(new Date().getTime()));
			cs.setString(5, "1234678");

			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("RESPONESE " + cs.getString(7) + "|" + cs.getString(6));

			pRespCode = cs.getString(7);
			Message = cs.getString(6);

			if (pRespCode.equals("AP000")) {
				pRespCode = "0000";
				String tMessage = cs.getString(6);
				Type userListType = new TypeToken<ArrayList<ProcedureModel>>() {
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

	public class ProcedureModel {
		private String RIGHT_TYPE;
		private String MID;
		private String TID;
		private String DEV_TYPE;
		private String UID;
		private String APIID;
		private String APINAME;
		private String APIVER;
		private String APITYPE;
		private String USERID;
		private String PASSWORD_hash;

		public String getRIGHT_TYPE() {
			return RIGHT_TYPE;
		}

		public void setRIGHT_TYPE(String rIGHT_TYPE) {
			RIGHT_TYPE = rIGHT_TYPE;
		}

		public String getMID() {
			return MID;
		}

		public void setMID(String mID) {
			MID = mID;
		}

		public String getTID() {
			return TID;
		}

		public void setTID(String tID) {
			TID = tID;
		}

		public String getDEV_TYPE() {
			return DEV_TYPE;
		}

		public void setDEV_TYPE(String dEV_TYPE) {
			DEV_TYPE = dEV_TYPE;
		}

		public String getUID() {
			return UID;
		}

		public void setUID(String uID) {
			UID = uID;
		}

		public String getAPIID() {
			return APIID;
		}

		public void setAPIID(String aPIID) {
			APIID = aPIID;
		}

		public String getAPINAME() {
			return APINAME;
		}

		public void setAPINAME(String aPINAME) {
			APINAME = aPINAME;
		}

		public String getAPIVER() {
			return APIVER;
		}

		public void setAPIVER(String aPIVER) {
			APIVER = aPIVER;
		}

		public String getAPITYPE() {
			return APITYPE;
		}

		public void setAPITYPE(String aPITYPE) {
			APITYPE = aPITYPE;
		}

		public String getUSERID() {
			return USERID;
		}

		public void setUSERID(String uSERID) {
			USERID = uSERID;
		}

		public String getPASSWORD_hash() {
			return PASSWORD_hash;
		}

		public void setPASSWORD_hash(String pASSWORD_hash) {
			PASSWORD_hash = pASSWORD_hash;
		}
	}

}
