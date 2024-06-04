package com.fuel.ws.classes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.db.proc.DBProcUtil;
import com.ag.fuel.entity.VwLovMaster;
import com.ag.fuel.services.VwLovMasterService;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

import oracle.jdbc.internal.OracleTypes;

@Component("com.fuel.ws.classes.WsAccountInformation")
public class WsAccountInformation implements Wisher {

	@Autowired
	VwLovMasterService VwlovMasterService;

	@Autowired
	UserScreenService screenService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "INIT":
				response = initRequest(rm);
				break;
			case "FETCH":
				response = fetchRequest(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {

			String route = (rm.getAdditionalData().containsKey("route"))
					? rm.getAdditionalData().get("route").toString()
					: null;

			String corpid = rm.getCorpId();

			UserScreen screen = screenService.fetchScreenIdByRoute(route, corpid);

			List<VwLovMaster> accountNumbers = VwlovMasterService
					.fetchAllByScreenID(String.valueOf(screen.getScreenId()), corpid);

			if (accountNumbers.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();

				obj.put("accountNumbers", accountNumbers);

				response.setData(obj);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
			} else {
				response.setCode("9991");
				response.setMessage("No Record Found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		try {
			String accountInfo = null;

			String entityNo = (rm.getAdditionalData().containsKey("entityNo"))
					? rm.getAdditionalData().get("entityNo").toString()
					: null;
			String screenId = (rm.getAdditionalData().containsKey("screenId"))
					? rm.getAdditionalData().get("screenId").toString()
					: null;
			String corpid = rm.getCorpId();
			String expiry = rm.getAdditionalData().get("expiry").toString() != null
					? rm.getAdditionalData().get("expiry").toString()
					: null;

			HashMap<Object, Object> obj = new HashMap<Object, Object>();

			String[] res = accountInfoProc(screenId, corpid, entityNo, expiry);

			System.out.println(res[0]);
			System.out.println(res[1]);

			if (res[0].equals("00")) {
				accountInfo = res[1];

				obj.put("accountInfo", accountInfo);

				response.setData(obj);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
			} else {
				response.setCode("9992");
				response.setMessage(res[1]);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public static String[] accountInfoProc(String screenId, String corpId, String entity, String expiry) {
		String[] res = new String[2];
		String code = "0";
		String message = null;

		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + screenId + "|" + corpId + "|" + entity + "|" + expiry);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call FUEL_ENTITY_INFO(?,?,?,?,?,?)}");
			cs.setString(1, screenId);
			cs.setString(2, corpId);
			cs.setString(3, entity);
			cs.setString(4, expiry);

			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);

			cs.execute();

			code = cs.getString(5);
			message = cs.getString(6);

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			code = "9999";
			message = "Please Contact Application Administrator.";
		} finally {
			res[0] = code;
			res[1] = message;
			DBProcUtil.closeConnection(con);
		}

		return res;
	}

}
