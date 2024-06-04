package com.ag.fuel.util;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.ag.db.proc.DBProcUtil;
import com.ag.fuel.model.IdValueModel;
import com.ag.generic.util.AgLogger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import oracle.jdbc.internal.OracleTypes;

public class AGFuelUtil {

	public String parseData(String data, boolean sendDropDownKeyValue) {

		try {
			ArrayList<IdValueModel> list = new ArrayList<IdValueModel>();

			Type keyValueType = new TypeToken<List<IdValueModel>>() {
			}.getType();
			list = new Gson().fromJson(data, keyValueType);
			StringBuilder sb = new StringBuilder();

			for (IdValueModel a : list) {
				if (!a.getId().equals("Remarks For Approver:")) {
					if (!sendDropDownKeyValue && a.getValue().contains("-")) {
						String tempVar = a.getValue().split("-")[0];
						sb.append(parser(a.getId()) + ":" + tempVar + ",");
					} else {
						sb.append(parser(a.getId()) + ":" + a.getValue() + ",");
					}

				}
			}

			String dsb = sb.toString().substring(0, sb.lastIndexOf(","));

			AgLogger.logInfo(dsb);
			return dsb;
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}

	}

	public String parser(String valueMapper) {
		try {
			valueMapper = valueMapper.replaceAll("[^a-zA-Z0-9]", "");
			return valueMapper;
		} catch (Exception e) {
			return valueMapper;
		}
	}

	public static String[] fuelAppProcess(String corpId, String screenMessage, String messageType, int userId,
			String userCode, int groupCode, String request) {

		String[] response = new String[3];

		response[0] = "9999";
		response[1] = "Failed";
		response[2] = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + corpId + "|" + screenMessage + "|" + messageType + "|" + userId + "|"
					+ userCode + "|" + groupCode + "|" + request);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_FUEL_MW_REVEMP(?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, screenMessage);
			cs.setString(3, messageType);
			cs.setInt(4, userId);
			cs.setString(5, userCode);
			cs.setInt(6, groupCode);
			cs.setString(7, request);

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.registerOutParameter(10, OracleTypes.CLOB);
			cs.execute();

			response[0] = cs.getString(8);
			response[1] = cs.getString(9);
			response[2] = cs.getString(10);
			AgLogger.logInfo("RESPONSE " + response[0] + "|" + response[1] + "|" + response[2]);
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "9999";
			response[1] = "Something Went Wrong.";
			response[2] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static String[] fuelAccountProcess(String corpId, String screenMessage, String messageType, int userId,
			String userCode, int groupCode, String request) {

		String[] response = new String[3];

		response[0] = "9999";
		response[1] = "Failed";
		response[2] = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + corpId + "|" + screenMessage + "|" + messageType + "|" + userId + "|"
					+ userCode + "|" + groupCode + "|" + request);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_LOV(?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, screenMessage);
			cs.setString(3, messageType);
			cs.setInt(4, userId);
			cs.setString(5, userCode);
			cs.setInt(6, groupCode);
			cs.setString(7, request);

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.registerOutParameter(10, OracleTypes.CLOB);
			cs.execute();

			response[0] = cs.getString(8);
			response[1] = cs.getString(9);
			response[2] = cs.getString(10);
			AgLogger.logInfo("RESPONSE " + response[0] + "|" + response[1] + "|" + response[2]);
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "9999";
			response[1] = "Something Went Wrong.";
			response[2] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static String[] fuelGetData(String corpId, String screenMessage, String messageType, int userId,
			String userCode, int groupCode, String request) {

		String[] response = new String[3];

		response[0] = "9999";
		response[1] = "Failed";
		response[2] = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + corpId + "|" + screenMessage + "|" + messageType + "|" + userId + "|"
					+ userCode + "|" + groupCode + "|" + request);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_DATA(?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, screenMessage);
			cs.setString(3, messageType);
			cs.setInt(4, userId);
			cs.setString(5, userCode);
			cs.setInt(6, groupCode);
			cs.setString(7, request);

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.registerOutParameter(10, OracleTypes.CLOB);
			cs.execute();

			response[0] = cs.getString(8);
			response[1] = cs.getString(9);
			response[2] = cs.getString(10);
			AgLogger.logInfo("RESPONSE " + response[0] + "|" + response[1] + "|" + response[2]);
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "9999";
			response[1] = "Something Went Wrong.";
			response[2] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static String[] fuelValidateProductData(String corpId, String fileId) {

		String[] response = new String[3];

		response[0] = "9999";
		response[1] = "Failed";
		response[2] = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + corpId + "|" + fileId);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call proc_fuel_price_validate(?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, fileId);

			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.execute();

			response[0] = cs.getString(3);
			response[1] = cs.getString(4);
			AgLogger.logInfo("RESPONSE " + response[0] + "|" + response[1]);
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "9999";
			response[1] = "Something Went Wrong.";
			response[2] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static String[] fuelApproveProductData(String corpId, String fileId, String date, String time) {

		String[] response = new String[3];

		response[0] = "9999";
		response[1] = "Failed";
		response[2] = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + corpId + "|" + fileId + "|" + date + "|" + time);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_FUEL_PRICE_GENERATE_FILE(?,?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, fileId);
			cs.setString(3, date);
			cs.setString(4, time);

			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.CLOB);
			cs.execute();

			response[0] = cs.getString(5);
			response[1] = cs.getString(6);
			response[2] = cs.getString(7);
			AgLogger.logInfo("RESPONSE " + response[0] + "|" + response[1] + "|" + response[2]);
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "9999";
			response[1] = "Something Went Wrong.";
			response[2] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static String[] fuelProcValidate(String corpId, String message, String request) {

		String[] response = new String[3];

		response[0] = "99";
		response[1] = "Something Went Wrong.";
		response[2] = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + corpId + "|" + message + "|"+ request);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_VALIDATE(?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, message);
			cs.setString(3, request);

			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.execute();

			response[0] = cs.getString(4);
			response[1] = cs.getString(5);
			AgLogger.logInfo("RESPONSE " + response[0] + "|" + response[1]);
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "99";
			response[1] = "Something Went Wrong.";
			response[2] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return response;
	}
	
	public static String[] fuelProcAutoFetch(String corpId, String screenMessage, String messageType, int userId,
			String userCode, int groupCode, String request) {

		String[] response = new String[3];

		response[0] = "9999";
		response[1] = "Failed";
		response[2] = "";
		Connection con = null;
		try {

			AgLogger.logInfo("PROC_AUTO_FETCH REQUEST " + corpId + "|" + screenMessage + "|" + messageType + "|" + userId + "|"
					+ userCode + "|" + groupCode + "|" + request);

			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_AUTO_FETCH(?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, screenMessage);
			cs.setString(3, messageType);
			cs.setInt(4, userId);
			cs.setString(5, userCode);
			cs.setInt(6, groupCode);
			cs.setString(7, request);

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.registerOutParameter(10, OracleTypes.CLOB);
			cs.execute();

			response[0] = cs.getString(8);
			response[1] = cs.getString(9);
			response[2] = cs.getString(10);
			AgLogger.logInfo("PROC_AUTO_FETCH RESPONSE " + response[0] + "|" + response[1] + "|" + response[2]);
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			response[0] = "9999";
			response[1] = "Something Went Wrong.";
			response[2] = "";
		} finally {
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

}
