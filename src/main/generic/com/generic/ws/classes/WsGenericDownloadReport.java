package com.generic.ws.classes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProcUtil;
import com.ag.generic.model.KeyValueModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.JasperUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.jdbc.internal.OracleTypes;

@Component("com.generic.ws.classes.WsGenericDownloadReport")
public class WsGenericDownloadReport implements Wisher {

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			String filterListResponse = (rm.getAdditionalData().containsKey("filterListResponse"))
					? rm.getAdditionalData().get("filterListResponse").toString()
					: null;
			String exportOption = (rm.getAdditionalData().containsKey("exportOption"))
					? rm.getAdditionalData().get("exportOption").toString()
					: null;

			String reportId = (rm.getAdditionalData().containsKey("reportId"))
					? rm.getAdditionalData().get("reportId").toString()
					: null;

			String reportPath = (rm.getAdditionalData().containsKey("reportPath"))
					? rm.getAdditionalData().get("reportPath").toString()
					: null;

			String reportName = (rm.getAdditionalData().containsKey("reportName"))
					? rm.getAdditionalData().get("reportName").toString()
					: null;

			TypeToken<List<KeyValueModel>> typeToken = new TypeToken<List<KeyValueModel>>() {
			};
			List<KeyValueModel> list = new Gson().fromJson(filterListResponse, typeToken.getType());

			HashMap<Object, Object> params = defaultParamsForGenericReport(rm.getUserid(), reportId);

			String[] report = JasperUtil.generateGenericReports(reportPath, list, reportName, exportOption, params);

			if (report[0].equals("0000")) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();

				obj.put("fileName", report[1]);
				obj.put("blob", report[2]);

				response.setData(obj);
				response.setCode("0000");
				response.setMessage("Success.");

			} else {
				response.setCode("0002");
				response.setMessage("Report Not Created. " + report[1]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	private HashMap<Object, Object> defaultParamsForGenericReport(String userId, String reportId) {
		HashMap<Object, Object> obj = new HashMap<Object, Object>();
		String pMessage = "N/A";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST | " + userId + " || " + reportId);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call default_param(?,?,?)}");
			cs.setString(1, userId);
			cs.setString(2, reportId);
			cs.registerOutParameter(3, OracleTypes.CLOB);
			cs.execute();
			AgLogger.logInfo("Decrypt RES || " + cs.getString(3));
			pMessage = cs.getString(3);
			if (pMessage != null) {
				if (pMessage.contains(":")) {
					String[] val = pMessage.split(":");
					for (int i = 0; i < val.length; i++) {
						String[] da = val[i].split(",");
						obj.put(da[0], da[1]);
					}
				} else {
					String[] val = pMessage.split(",");
					obj.put(val[0], val[1]);
				}
			}
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DBProcUtil.closeConnection(con);
		}

		return obj;
	}

}
