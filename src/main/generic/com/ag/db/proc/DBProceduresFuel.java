package com.ag.db.proc;

import java.sql.CallableStatement;
import java.sql.Connection;

import com.ag.generic.util.AgLogger;

import oracle.jdbc.internal.OracleTypes;

public class DBProceduresFuel {

	public static String[] fetchInformation(String screenId, String corpId, String entity, String expiry) {
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
			// message = "Exception " + e.getMessage();
		} finally {
			res[0] = code;
			res[1] = message;

			DBProcUtil.closeConnection(con);
		}

		return res;
	}

	public static String fuelAppProcess(String corpId, String messageType, int userId, String userCode,
			String cardNumber, int grpCode, String newStatus) {
		String resProc = "9999";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + corpId + "|" + messageType + "|" + userId + "|" + userCode + "|" + cardNumber
					+ "|" + grpCode + "|" + newStatus);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call FUEL_MOBILE_APP(?,?,?,?,?,?,?,?)}");
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

}
