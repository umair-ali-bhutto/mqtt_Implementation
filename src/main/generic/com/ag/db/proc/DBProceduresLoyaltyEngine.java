package com.ag.db.proc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ag.generic.util.AgLogger;

import oracle.jdbc.internal.OracleTypes;

public class DBProceduresLoyaltyEngine {
	
	public static RetrieveDiscountModel retrieveDiscount(final String pCorpID, final String pProcCode,
			final String pMID, final String pTID, final Date pTxnDate, final Float pTxnAmount, final String myCurrency,
			final String myCID, final String pCIDType, final String myRef) {
		RetrieveDiscountModel response = null;
		String pRespCode = "00001";
		String pDiscountName = "N/A";
		String pDiscountPerc = "0";
		String pNetAmount = "0";
		String pDiscountAmount = "0";
		String Message = "N/A";
		Connection con = null;
		try {
			AgLogger.logInfo("REQUEST " + pCorpID + "|" + pProcCode + "|" + pMID + "|" + pTID + "|" + pTxnDate + "|"
					+ pMID + "|" + pTxnAmount + "|" + myCurrency + "|" + myCID + "|" + pCIDType + "|" + myRef);
			con = DBProcUtil.getConnection();
			final CallableStatement cs = con.prepareCall("{call PROC_GET_SDISCOUNT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, pCorpID);
			cs.setString(2, pProcCode);
			cs.setString(3, pMID);
			cs.setString(4, pTID);
			cs.setDate(5, pTxnDate);
			cs.setFloat(6, pTxnAmount);
			cs.setString(7, myCurrency);
			cs.setString(8, myCID);
			cs.setString(9, pCIDType);
			cs.setString(10, myRef);
			cs.registerOutParameter(11, 2);
			cs.registerOutParameter(12, 2);
			cs.registerOutParameter(13, 2);
			cs.registerOutParameter(14, 12);
			cs.registerOutParameter(15, 12);
			cs.execute();
			AgLogger.logInfo("PROC_GET_SDISCOUNT RES " + cs.getString(11) + "|" + cs.getString(12) + "|"
					+ cs.getString(13) + "|" + cs.getString(14) + "|" + cs.getString(15));
			pRespCode = cs.getString(15);
			pDiscountName = cs.getString(14);
			pDiscountPerc = cs.getString(13);
			pNetAmount = cs.getString(12);
			pDiscountAmount = cs.getString(11);
			Message = "SUCCESS";
			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
				Message = "SUCCESS";
			} else {
				pRespCode = "9999";
				Message = "ERROR:" + cs.getString(15);
			}
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
			Message = "Exception " + e.getMessage();
			return response;
		} finally {
			response = new RetrieveDiscountModel();
			response.setMessage(Message);
			response.setpDiscountAmount(pDiscountAmount);
			response.setpDiscountName(pDiscountName);
			response.setpDiscountPerc(pDiscountPerc);
			response.setpNetAmount(pNetAmount);
			response.setpRespCode(pRespCode);
			DBProcUtil.closeConnection(con);
		}
		response = new RetrieveDiscountModel();
		response.setMessage(Message);
		response.setpDiscountAmount(pDiscountAmount);
		response.setpDiscountName(pDiscountName);
		response.setpDiscountPerc(pDiscountPerc);
		response.setpNetAmount(pNetAmount);
		response.setpRespCode(pRespCode);
		DBProcUtil.closeConnection(con);
		return response;
	}

	public static ProcPerfromTransactionModel processAwardTxn(final String pCorpID, final String pProcCode,
			final String pPID, final String pFID, final String pCHID, final String pMID, final String pTID,
			final Date pTxnDate, final Float pTxnAmount, final String pCur, final String myCID, final String pCIDType,
			final String myRef, final String batchNumber) {
		ProcPerfromTransactionModel response = null;
		String pRespCode = "00001";
		String pTxnRef = "0";
		String pRetCur = "DEFAULT";
		String pBalance = "0.0";
		String pRetAmount = "0.0";
		String Message = "N/A";
		Connection con = null;
		try {
			AgLogger.logInfo("REQUEST " + pCorpID + "|" + pProcCode + "|" + pPID + "|" + pFID + "|" + pCHID + "|" + pMID
					+ "|" + pTID + "|" + pTxnDate + "|" + pTxnAmount + "|" + pCur + "|" + myCID + "|" + pCIDType + "|"
					+ myRef + "|" + batchNumber);
			con = DBProcUtil.getConnection();
			final CallableStatement cs = con
					.prepareCall("{call PROC_PERFORM_TRANSACTION(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, pCorpID);
			cs.setString(2, pProcCode);
			cs.setString(3, pPID);
			cs.setString(4, pFID);
			cs.setString(5, pCHID);
			cs.setString(6, pMID);
			cs.setString(7, pTID);
			cs.setDate(8, pTxnDate);
			cs.setFloat(9, pTxnAmount);
			cs.setString(10, pCur);
			cs.setString(11, myCID);
			cs.setString(12, pCIDType);
			cs.setString(13, batchNumber);
			cs.setString(14, myRef);
			cs.registerOutParameter(15, 2);
			cs.registerOutParameter(16, 2);
			cs.registerOutParameter(17, 12);
			cs.registerOutParameter(18, 12);
			cs.registerOutParameter(19, 12);
			cs.execute();
			AgLogger.logInfo("RESPONESE " + cs.getString(19));
			AgLogger.logInfo("PROC_PERFORM_TRANSACTION RES " + cs.getString(15) + "|" + cs.getString(16) + "|"
					+ cs.getString(17) + "|" + cs.getString(18) + "|" + cs.getString(19));
			pRespCode = cs.getString(19);
			pTxnRef = cs.getString(18);
			pRetCur = cs.getString(17);
			pBalance = cs.getString(16);
			pRetAmount = cs.getString(15);
			Message = "SUCCESS";
			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
				Message = "SUCCESS";
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
			return response;
		} finally {
			response = new ProcPerfromTransactionModel();
			response.setMessage(Message);
			response.setpBalance(pBalance);
			response.setpRespCode(pRespCode);
			response.setpRetAmount(pRetAmount);
			response.setpRetCur(pRetCur);
			response.setpTxnRef(pTxnRef);
			DBProcUtil.closeConnection(con);
		}
		response = new ProcPerfromTransactionModel();
		response.setMessage(Message);
		response.setpBalance(pBalance);
		response.setpRespCode(pRespCode);
		response.setpRetAmount(pRetAmount);
		response.setpRetCur(pRetCur);
		response.setpTxnRef(pTxnRef);
		DBProcUtil.closeConnection(con);
		return response;
	}

	public static List<StatmentModel> getAccountStatment(final String corpId, final String cId, final String pUserID,
			final String pAccID, final String pSourceRef) {
		List<StatmentModel> lst = null;
		Connection con = null;
		try {
			AgLogger.logInfo("REQUEST " + corpId + "|" + cId + "|" + pUserID + "|" + pAccID + "|" + pSourceRef);
			con = DBProcUtil.getConnection();
			final CallableStatement cs = con.prepareCall("{call PROC_GET_ACC_STATEMENT (?,?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, cId);
			cs.setString(3, pUserID);
			cs.setString(4, pAccID);
			cs.setString(5, pSourceRef);
			cs.registerOutParameter(6, 12);
			cs.registerOutParameter(7, 12);
			cs.execute();
			AgLogger.logInfo("RESPONESE " + cs.getString(6) + "|" + cs.getString(7));
			final String mn = cs.getString(7);
			if (mn.equals("00000")) {
				final String accounts = cs.getString(6);
				final JSONObject json = new JSONObject(accounts);
				final JSONArray jarray = json.getJSONArray("TRANSACTIONS");
				lst = new ArrayList<StatmentModel>();
				for (int k = 0; k < jarray.length(); ++k) {
					final JSONObject jk = new JSONObject(jarray.get(k).toString());
					System.out.println("......." + jk);
					final StatmentModel m = new StatmentModel();
					m.setAmount(String.valueOf(jk.getDouble("AMOUNT")));
					m.setBalance(String.valueOf(jk.getDouble("BALANCE")));
					m.setDate(jk.getString("DATE"));
					m.setCurrency(jk.getString("CURR"));
					m.setOtherAccount(jk.getString("OTHERACC"));
					m.setReason(jk.getString("REASON"));
					m.setsNo(jk.getInt("SNo"));
					m.setType(txnTypeCase(jk.getString("TYPE")));
					lst.add(m);
				}
			}
			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return lst;
		} finally {
			DBProcUtil.closeConnection(con);
		}
		DBProcUtil.closeConnection(con);
		return lst;
	}

	public static String txnTypeCase(final String type) {
		String dflt = "";
		switch (type) {
		case "ADJ": {
			dflt = "Adjusted";
			return dflt;
		}
		case "AWD": {
			dflt = "Awarded";
			return dflt;
		}
		case "RDM": {
			dflt = "Redeemed";
			return dflt;
		}
		default:
			break;
		}
		dflt = "";
		return dflt;
	}

	public static String[] getAccountBalancePoints(String corpId, String cId, String userId, String refId) {
		double d = 0.0;
		double balAmount = 0.0;
		String code = "00000";
		List<AccountsModel> actMoedl = null;
		Connection con = null;
		String[] resp = new String[3];
		AgLogger.logInfo(cId, " Get Account Balance....");
		try {
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_GET_ACC_BAL (?,?,?,?,?,?)}");
			cs.setString(1, corpId);
			cs.setString(2, cId);
			cs.setString(3, userId);
			cs.setString(4, refId);
			cs.registerOutParameter(5, OracleTypes.VARCHAR);
			cs.registerOutParameter(6, OracleTypes.VARCHAR);

			cs.execute();
			AgLogger.logInfo(cId, "GET ACCOUNTS REQ" + corpId + "|" + cId + "|" + userId + "|" + refId);
			AgLogger.logInfo(cId, "GET ACCOUNTS RES" + cs.getString(5) + "|" + cs.getString(6));
			code = cs.getString(6);
			if (cs.getString(6).equals("00000")) {
				String accounts = cs.getString(5);
				JSONObject json = new JSONObject(accounts);
				JSONArray jarray = json.getJSONArray("Accounts");
				actMoedl = new ArrayList<AccountsModel>();
				for (int k = 0; k < jarray.length(); k++) {
					JSONObject jk = new JSONObject(jarray.get(k).toString());
					AccountsModel m = new AccountsModel();
					m.setAccountNumber(jk.getString("ACCOUNTNO"));
					m.setBalance(jk.getDouble("BALANCE"));
					m.setsNo(jk.getInt("SNo"));
					m.setStatus(jk.getString("STATUS"));
					m.setTitle(jk.getString("TITLE"));
					m.setType(jk.getString("TYPE"));
					m.setBalanceAmount(jk.getDouble("BALANCEAMOUNT"));
					actMoedl.add(m);
				}
			}

			if (actMoedl != null) {
				for (AccountsModel aL : actMoedl) {
					d = +aL.getBalance();
				}

				for (AccountsModel aL : actMoedl) {
					balAmount = +aL.getBalanceAmount();
				}

			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			DBProcUtil.closeConnection(con);
			resp[0] = code;
			resp[1] = String.valueOf(d);
			resp[2] = String.valueOf(balAmount);
		}
		return resp;
	}

	public static ProcPerfromTransactionModel processProcUpdateUserStatus(String pCorpID, String myCid, String myUserId,
			String myStatus, String myRef) {
		ProcPerfromTransactionModel response = null;

		String pRespCode = "00001";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + pCorpID + "|" + myCid + "|" + myUserId + "|" + myStatus + "|" + myRef);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_UPDATE_USER_STATUS(?,?,?,?,?,?)}");
			cs.setString(1, pCorpID);
			cs.setString(2, myCid);
			cs.setString(3, myUserId);
			cs.setString(4, myStatus);
			cs.setString(5, myRef);

			cs.registerOutParameter(6, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("RESPONESE " + cs.getString(6));

			AgLogger.logInfo("PROC_UPDATE_USER_STATUS RES " + cs.getString(6));

			pRespCode = cs.getString(6);

			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
			} else {
				pRespCode = cs.getString(6);
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
		} finally {
			response = new ProcPerfromTransactionModel();
			response.setpRespCode(pRespCode);
			DBProcUtil.closeConnection(con);
		}
		return response;
	}

	public static ProcPerfromTransactionModel processProcUpdateAccStatus(String pCorpID, String myCid, String myaccid,
			String myStatus, String myRef) {
		ProcPerfromTransactionModel response = null;

		String pRespCode = "00001";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + pCorpID + "|" + myCid + "|" + myaccid + "|" + myStatus + "|" + myRef);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_UPDATE_ACC_STATUS(?,?,?,?,?,?)}");
			cs.setString(1, pCorpID);
			cs.setString(2, myCid);
			cs.setString(3, myaccid);
			cs.setString(4, myStatus);
			cs.setString(5, myRef);

			cs.registerOutParameter(6, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("RESPONESE " + cs.getString(6));

			AgLogger.logInfo("PROC_UPDATE_USER_STATUS RES " + cs.getString(6));

			pRespCode = cs.getString(6);

			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
			} else {
				pRespCode = "9998";
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
		} finally {
			response = new ProcPerfromTransactionModel();
			response.setpRespCode(pRespCode);
			DBProcUtil.closeConnection(con);
		}
		return response;
	}

	public static ProcPerfromTransactionModel processProcUpdateCustStatus(String pCorpID, String myCid, String myStatus,
			String myRef) {
		ProcPerfromTransactionModel response = null;

		String pRespCode = "00001";
		Connection con = null;
		try {

			AgLogger.logInfo("REQUEST " + pCorpID + "|" + myCid + "|" + myStatus + "|" + myRef);
			con = DBProcUtil.getConnection();
			CallableStatement cs = con.prepareCall("{call PROC_UPDATE_CUST_STATUS(?,?,?,?,?)}");
			cs.setString(1, pCorpID);
			cs.setString(2, myCid);
			cs.setString(3, myStatus);
			cs.setString(4, myRef);

			cs.registerOutParameter(5, OracleTypes.VARCHAR);

			cs.execute();

			AgLogger.logInfo("RESPONESE " + cs.getString(5));

			AgLogger.logInfo("PROC_UPDATE_CUST_STATUS RES " + cs.getString(5));

			pRespCode = cs.getString(5);

			if (pRespCode.equals("00000")) {
				pRespCode = "0000";
			} else {
				pRespCode = "9998";
			}

			if (cs != null) {
				cs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			pRespCode = "9999";
		} finally {
			response = new ProcPerfromTransactionModel();
			response.setpRespCode(pRespCode);
			DBProcUtil.closeConnection(con);
		}
		return response;
	}

}
