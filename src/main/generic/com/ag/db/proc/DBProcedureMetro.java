package com.ag.db.proc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.ag.generic.util.AgLogger;
import com.ag.metro.model.GenericLovModel;

import oracle.jdbc.internal.OracleTypes;

public class DBProcedureMetro {

	public static MetroGetCardBalanceModel metroGetCardBalance(String vcard, String vbin) {
		MetroGetCardBalanceModel response = null;
		String vrsp_code = "00001";
		String vmessage = "N/A";
		String vstatus = "N/A";
		String vbalance = "0.0";
		List<GenericLovModel> vallow_status = new ArrayList<GenericLovModel>();
		String tempAllowsStatus = "";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + vcard + "|" + vbin);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call METRO_CARD_BALANCE(?,?,?,?,?,?,?)}");
			cs.setString(1, vcard);
			cs.setString(2, vbin);

			cs.registerOutParameter(3, OracleTypes.VARCHAR);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("METRO_CARD_BALANCE RES " + cs.getString(3) + "|" + cs.getString(4) + "|" + cs.getString(5)
					+ "|" + cs.getString(6) + "|" + cs.getString(7));

			vrsp_code = cs.getString(3);
			vmessage = cs.getString(4);
			if (vrsp_code.equals("00")) {
				vrsp_code = "0000";
				vmessage = "SUCCESS";
				vstatus = cs.getString(5);
				vbalance = cs.getString(6);
				tempAllowsStatus = cs.getString(7);

				for (String s : tempAllowsStatus.split(",")) {
					GenericLovModel gl = new GenericLovModel();
					String[] sk = s.split("\\:");
					gl.setId(sk[0]);
					gl.setValue(sk[1]);
					vallow_status.add(gl);
				}

			} else {
				vrsp_code = "9999";
				vmessage = cs.getString(4);
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			vrsp_code = "9999";
			vmessage = "Exception " + e.getMessage();
		} finally {
			response = new MetroGetCardBalanceModel();
			response.setVbalance(vbalance);
			response.setVmessage(vmessage);
			response.setVrsp_code(vrsp_code);
			response.setVstatus(vstatus);
			response.setVallow_status(vallow_status);
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static GenericDbProcModel metroChangeCardStatus(String vcard, String vbin, String userId, String voldStatus,
			String vNewStatus, String remarks, String vMemeberId) {
		GenericDbProcModel response = null;
		String vrsp_code = "00001";
		String vmessage = "N/A";

		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + vcard + "|" + vbin + "|" + userId + "|" + voldStatus + "|" + vNewStatus + "|"
					+ remarks + "|" + vMemeberId);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call METRO_CARD_STATUS_CHANGE(?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, vcard);
			cs.setString(2, vbin);
			cs.setString(3, userId);
			cs.setString(4, voldStatus);
			cs.setString(5, vNewStatus);
			cs.setString(6, remarks);
			cs.setString(7, vMemeberId);

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("METRO_CARD_STATUS_CHANGE RES " + cs.getString(8) + "|" + cs.getString(9));

			vrsp_code = cs.getString(8);
			vmessage = cs.getString(9);
			if (vrsp_code.equals("00")) {
				vrsp_code = "0000";
				vmessage = cs.getString(9);
			} else {
				vrsp_code = "9999";
				vmessage = cs.getString(9);
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			vrsp_code = "9999";
			vmessage = "Exception " + e.getMessage();
		} finally {
			response = new GenericDbProcModel();
			response.setVmessage(vmessage);
			response.setVrsp_code(vrsp_code);
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static GenericDbProcModel metroTopupValidation(String vbin, String vcardFrom, String V_CARD_TO,
			String v_top_up, String V_MEM_ID, String v_tot_cards, String V_SHELF_LIFE) {
		GenericDbProcModel response = null;
		String vrsp_code = "00001";
		String vmessage = "N/A";

		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + vbin + "|" + vcardFrom + "|" + V_CARD_TO + "|" + v_top_up + "|" + V_MEM_ID
					+ "|" + v_tot_cards + "|" + V_SHELF_LIFE);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call METRO_VALIDATION(?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, vbin);
			cs.setString(2, vcardFrom);
			cs.setString(3, V_CARD_TO);
			cs.setString(4, v_top_up);
			cs.setString(5, V_MEM_ID);
			cs.setString(6, v_tot_cards);
			cs.setString(7, V_SHELF_LIFE);

			cs.registerOutParameter(8, OracleTypes.VARCHAR);
			cs.registerOutParameter(9, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("METRO_VALIDATION RES " + cs.getString(8) + "|" + cs.getString(9));

			vrsp_code = cs.getString(8);
			vmessage = cs.getString(9);
			if (vrsp_code.equals("00")) {
				vrsp_code = "0000";
				vmessage = cs.getString(9);
			} else {
				vrsp_code = "9999";
				vmessage = cs.getString(9);
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			vrsp_code = "9999";
			vmessage = "Exception " + e.getMessage();
		} finally {
			response = new GenericDbProcModel();
			response.setVmessage(vmessage);
			response.setVrsp_code(vrsp_code);
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static MetroTopupProcessModel metroTopupProcess(String cardBin, String metroBatchSummaryId,
			String batchNumber, String userId) {
		MetroTopupProcessModel response = null;
		String vrsp_code = "0000";
		String vmessage = "SUCCESS";
		String vsuccess = "0";
		String vreject = "0";

		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + cardBin + "|" + metroBatchSummaryId + "|" + batchNumber + "|" + userId);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call METRO_TOPUP(?,?,?,?,?,?)}");
			cs.setString(1, cardBin);
			cs.setString(2, metroBatchSummaryId);
			cs.setString(3, batchNumber);
			cs.setString(4, userId);

			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("METRO_TOPUP RES " + cs.getString(5) + "|" + cs.getString(6));

			vrsp_code = "0000";
			vmessage = "SUCCESS";
			vsuccess = cs.getString(5);
			vreject = cs.getString(6);

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			vrsp_code = "9999";
			vmessage = "Exception " + e.getMessage();
		} finally {
			response = new MetroTopupProcessModel();
			response.setVmessage(vmessage);
			response.setVrsp_code(vrsp_code);
			response.setSuccess(vsuccess);
			response.setRejected(vreject);
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

}
