package com.ag.db.proc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;

import org.apache.axis.encoding.Base64;

import com.ag.generic.util.AgLogger;

import oracle.jdbc.internal.OracleTypes;

public class DBProceduresGeneric {

	public static GenericDbProcModel retrievePreAuth(String pCorpID, String pMID, String pTID, Date entryDate) {
		GenericDbProcModel response = null;
		String pRespCode = "00001";
		String pJson = "N/A";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + pCorpID + "|" + pMID + "|" + pTID + "|");
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_PREAUTH(?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, pCorpID);
			cs.setString(2, pMID);
			cs.setString(3, pTID);
			cs.setString(4, null);
			cs.setDate(5, entryDate);
			cs.setString(6, null);
			cs.setString(7, null);
			cs.setString(8, null);

			cs.registerOutParameter(9, OracleTypes.VARCHAR);
			cs.registerOutParameter(10, OracleTypes.VARCHAR);
			cs.execute();

			AgLogger.logInfo("PROC_GET_PREAUTH RES " + cs.getString(9) + "|" + cs.getString(10));

			pRespCode = cs.getString(10);
			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
				pJson = cs.getString(9);
			} else {
				pRespCode = "9999";
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
		} finally {
			response = new GenericDbProcModel();
			response.setVmessage(pJson);
			response.setVrsp_code(pRespCode);
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static GenericDbProcModel encryptCardNumber(String pCorpID, String pKey, String pCertificate, String pData) {
		GenericDbProcModel response = null;
		String pRespCode = "00001";
		byte[] pJson = null;
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + pCorpID + "|" + pKey + "|" + pCertificate + "|" + pData);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call Encrypt(?,?,?,?,?,?,?)}");
			cs.setString(1, pCorpID);
			cs.setString(2, pKey);
			cs.setString(3, pCertificate);
			cs.setString(4, pData);

			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARBINARY);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.execute();

			AgLogger.logInfo("Encrypt RES " + cs.getString(5) + "|" + cs.getString(6) + "|" + cs.getString(7));

			pRespCode = cs.getString(7);
			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
				pJson = cs.getBytes(6);
			} else {
				pRespCode = "9999";
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
		} finally {
			response = new GenericDbProcModel();
			response.setbData(pJson);
			response.setVrsp_code(pRespCode);
			response.setVmessage(Base64.encode(pJson));
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static GenericDbProcModel decrpytCardNumber(String pCorpID, String pKey, String pCertificate, byte[] pData) {
		GenericDbProcModel response = null;
		String pRespCode = "00001";
		String pJson = "N/A";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + pCorpID + "|" + pKey + "|" + pCertificate + "|" + pData);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call Decrypt(?,?,?,?,?,?,?)}");
			cs.setString(1, pCorpID);
			cs.setString(2, pKey);
			cs.setString(3, pCertificate);
			cs.setBytes(4, pData);

			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			cs.registerOutParameter(7, OracleTypes.VARCHAR);
			cs.execute();

			// AgLogger.logInfo("Decrypt RES " + cs.getString(5) + "|" + cs.getString(6) + "|" + cs.getString(7));

			pRespCode = cs.getString(7);
			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
				pJson = cs.getString(6);
			} else {
				pRespCode = "9999";
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
		} finally {
			response = new GenericDbProcModel();
			response.setVmessage(pJson);
			response.setVrsp_code(pRespCode);
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

	public static GenericDbProcModel fetchGenericReportData(String pType, String pReportId, String pUserId, String pCorpId,
			String pGroupCode) {
		GenericDbProcModel response = null;
		String pRespCode = "0001";
		String pMessage = "N/A";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + pType + "|" + pReportId + "|" + pUserId + "|" + pCorpId + "|" + pGroupCode);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call GET_REPORT(?,?,?,?,?,?,?)}");
			cs.setString(1, pType);
			cs.setString(2, pReportId);
			cs.setString(3, pUserId);
			cs.setString(4, pCorpId);
			cs.setString(5, pGroupCode);

			// rsp_code
			cs.registerOutParameter(6, OracleTypes.VARCHAR);
			// message
			cs.registerOutParameter(7, OracleTypes.CLOB);
			cs.execute();

			AgLogger.logInfo("Decrypt RES " + cs.getString(6) + "|" + cs.getString(7));

			pRespCode = cs.getString(6);
			if (pRespCode.equals("00")) {
				pRespCode = "0000";
				pMessage = cs.getString(7);
			} else {
				pRespCode = "9991";
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
		} finally {
			response = new GenericDbProcModel();
			response.setVmessage(pMessage);
			response.setVrsp_code(pRespCode);
			DBProcUtil.closeConnection(con);
		}

		return response;
	}

}
