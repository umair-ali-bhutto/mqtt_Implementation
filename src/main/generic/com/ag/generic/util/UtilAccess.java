package com.ag.generic.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.stereotype.Component;

import com.ag.generic.entity.ReqCall;
import com.ag.generic.entity.ReqCallsParam;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.model.GroupDeclarModel;
import com.ag.generic.model.KeyValueModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.mportal.entity.FedRates;
import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.MidTidDetailsConfig;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.entity.QueueLog;
import com.ag.mportal.entity.TxnDetail;
import com.ag.mportal.entity.TxnLog;
import com.ag.mportal.entity.TxnLogError;
import com.ag.mportal.model.DMSReportReportModel;
import com.ag.mportal.model.DonationReportModel;
import com.ag.mportal.model.OfflineDetailsReportModel;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.model.TxnSummary;
import com.ag.mportal.model.TxnSummaryModel;

import net.sf.jasperreports.engine.JRException;

@Component("UtilAccess")
public class UtilAccess {

	public static List<ReqCall> requestCallsList = null;
	public static List<ReqCallsParam> requestCallsParametersList = null;
	public static List<GroupDeclarModel> selectProvinceItem;
	public static HashMap<String, String> configPropMap = null;
	public static List<PosEntryModeConfig> lstPemConfig = null;

	public static List<MidTidDetailsConfig> MerchantTerminalDetailsConfigList = new ArrayList<MidTidDetailsConfig>();

	public static final String userSettingsDefaultTid = "DEFAULT_TID";
	public static final String userSettingsDefaultCardNum = "DEFAULT_CARD_NUM";

	public static ResponseModel generateResponse(String code, String message, Map<Object, Object> mp) {
		ResponseModel mdl = new ResponseModel();
		mdl.setData(mp);
		mdl.setCode(code);
		mdl.setMessage(message);
		return mdl;
	}

	public static ResponseModel generateResponse(String code, String message) {
		ResponseModel mdl = new ResponseModel();
		mdl.setCode(code);
		mdl.setMessage(message);
		return mdl;
	}

	public static float amountParser(String amount) {
		try {
			int s = Integer.parseInt(amount);
			return Float.parseFloat((double) s / (double) 100 + "");
		} catch (Exception e) {
			return Float.parseFloat(amount);
		}

	}

	public static String toStringBean(Object bean, boolean showNulls) {
		if (bean == null)
			return null;
		StringBuilder sb = new StringBuilder(bean.getClass().getName()).append("[");
		// new ToStringCreator(this)
		try {
			BeanInfo bi = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] pd = bi.getPropertyDescriptors();
			for (int i = 0; i < pd.length; i++) {
				if (!"class".equals(pd[i].getName())) {
					Object result = pd[i].getReadMethod().invoke(bean);
					if (showNulls || result != null) {
						sb.append(pd[i].getDisplayName()).append("=").append(result);
						if (i == pd.length - 1)
							continue;
						sb.append(",");
					}
				}
			}
		} catch (Exception ex) {
		}

		return sb.append("]").toString();
	}

	public static boolean isMPortalLogin(int group) {
		boolean f = false;
		String grp = String.valueOf(group);
		String ks = AppProp.getProperty("user.code");
		if (ks.contains(",")) {
			String[] sp = ks.split(",");
			for (int k = 0; k < sp.length; k++) {
				if (grp.equals(sp[k])) {
					f = true;
					return f;
				}
			}
		} else {
			if (grp.equals(ks)) {
				f = true;
				return f;
			}
		}
		return f;
	}

	public static boolean isBckOfficeLogin(int group) {
		boolean f = false;
		String grp = String.valueOf(group);
		String ks = AppProp.getProperty("admin.code");
		if (ks.contains(",")) {
			String[] sp = ks.split(",");
			for (int k = 0; k < sp.length; k++) {
				if (grp.equals(sp[k])) {
					f = true;
					return f;
				}
			}
		} else {
			if (grp.equals(ks)) {
				f = true;
				return f;
			}
		}
		return f;
	}

	public static boolean isUserLogin(int group) {
		boolean f = false;
		String grp = String.valueOf(group);
		String ks = AppProp.getProperty("user.code");
		if (ks.contains(",")) {
			String[] sp = ks.split(",");
			for (int k = 0; k < sp.length; k++) {
				if (grp.equals(sp[k])) {
					f = true;
					return f;
				}
			}
		} else {
			if (grp.equals(ks)) {
				f = true;
				return f;
			}
		}
		return f;
	}

	public static boolean isMsgLogin(String group) {
		boolean f = false;
		String ks = AppProp.getProperty("login.messageType");
		if (ks.contains(",")) {
			String[] sp = ks.split(",");
			for (int k = 0; k < sp.length; k++) {
				if (group.equals(sp[k])) {
					f = true;
					return f;
				}
			}
		} else {
			if (group.equals(ks)) {
				f = true;
				return f;
			}
		}
		return f;
	}

	public static boolean isMsgProcess(String group) {
		boolean f = false;
		String ks = AppProp.getProperty("process.messageType");
		if (ks.contains(",")) {
			String[] sp = ks.split(",");
			for (int k = 0; k < sp.length; k++) {
				if (group.equals(sp[k])) {
					f = true;
					return f;
				}
			}
		} else {
			if (group.equals(ks)) {
				f = true;
				return f;
			}
		}
		return f;
	}

	public static String md5Java(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			// converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {
			AgLogger.logerror(UtilAccess.class, " EXCEPTION  " + ex, ex);

		} catch (NoSuchAlgorithmException e) {
			AgLogger.logerror(UtilAccess.class, " EXCEPTION  " + e, e);
		}

		return digest;

	}

	public static List<ReqCall> getRequestCallsList() {
		return requestCallsList;
	}

	public static void setRequestCallsList(List<ReqCall> requestCallsList) {
		UtilAccess.requestCallsList = requestCallsList;
	}

	public static List<ReqCallsParam> getRequestCallsParametersList() {
		return requestCallsParametersList;
	}

	public static void setRequestCallsParametersList(List<ReqCallsParam> requestCallsParametersList) {
		UtilAccess.requestCallsParametersList = requestCallsParametersList;
	}

	public static String getUsersettingsdefaulttid() {
		return userSettingsDefaultTid;
	}

	/* Variable Static */
	public static final String complStatusOpen = "OPENED";
	public static final String complStatusAssigned = "ASSIGNED";
	public static final String complStatusClosed = "CLOSED";
	public static final String complStatusEscalated = "ESCALATED";
	public static final String complStatusResponded = "RESPONDED";

	public static Map<String, String> convertMap(String k) {
		String parts[] = k.split(",");
		Map<String, String> hMapData = new HashMap<String, String>();

		for (String part : parts) {
			String empdata[] = part.split("=");
			String strId = empdata[0].trim();
			String strName = "";

			try {
				if (empdata[1] != null) {
					strName = empdata[1];
				}

			} catch (Exception e) {
				strName = "-";
			}

			// add to map
			hMapData.put(strId, strName);
		}
		return hMapData;
	}

	public static boolean isAllAdminGp(String cctype) {
		boolean f = false;
		String grp = AppProp.getProperty("subadmin.user.group.code") + ","
				+ AppProp.getProperty("subadmin.user.group.code");
		String ks = grp;
		if (ks.contains(",")) {
			String[] sp = ks.split(",");
			for (int k = 0; k < sp.length; k++) {
				if (cctype.equals(sp[k])) {
					f = true;
					return f;
				}
			}
		} else {
			if (cctype.equals(ks)) {
				f = true;
				return f;
			}
		}
		return f;
	}

	public static List<ReportModel> reportList(List<Object[]> lst) {
		List<ReportModel> rmList = new ArrayList<ReportModel>();
		for (Object[] l : lst) {
			ReportModel rm = new ReportModel();
			rm.setTxnDetailId(Objects.isNull(l[0]) ? "" : l[0].toString());
			rm.setType((Objects.isNull(l[1]) ? "" : l[1].toString()));
			rm.setBin(Objects.isNull(l[2]) ? "" : l[2].toString());
			rm.setResponse(Objects.isNull(l[3]) ? "" : l[3].toString());
			rm.setEntryDate(Objects.isNull(l[4]) ? "" : l[4].toString());
			rm.setEntryTime(Objects.isNull(l[5]) ? "" : l[5].toString());
			rm.setStatus(Objects.isNull(l[6]) ? "" : l[6].toString());
			rm.setMerchantId((Objects.isNull(l[7]) ? "" : l[7].toString()));
			rm.setTerminalId(Objects.isNull(l[8]) ? "" : l[8].toString());
			rm.setTxnAmount(Objects.isNull(l[9]) ? "" : l[9].toString());
			rm.setTxnId(Objects.isNull(l[10]) ? "" : l[10].toString());
			rm.setBatchNumber(Objects.isNull(l[11]) ? "" : l[11].toString());
			rm.setReversal(Objects.isNull(l[12]) ? "" : l[12].toString());
			rm.setSetteled(Objects.isNull(l[13]) ? "" : l[13].toString());
			rm.setSetteledDate(Objects.isNull(l[14]) ? "" : l[14].toString());
			rm.setPaid(Objects.isNull(l[15]) ? "" : l[15].toString());
			rm.setPaidDate(Objects.isNull(l[16]) ? "" : l[16].toString());
			rm.setResultStatus(Objects.isNull(l[17]) ? "" : l[17].toString());
			rm.setRefNum(Objects.isNull(l[18]) ? "" : l[18].toString());
			rm.setCardCcType(Objects.isNull(l[19]) ? "" : l[19].toString());
			rm.setReversalDate(Objects.isNull(l[20]) ? "" : l[20].toString());
			rm.setMsg(Objects.isNull(l[21]) ? "" : l[21].toString());
			rm.setAuthId(Objects.isNull(l[22]) ? "" : l[22].toString());
			rm.setOrgRefNumber(Objects.isNull(l[23]) ? "" : l[23].toString());
			rm.setInvoiceNumber(Objects.isNull(l[24]) ? "" : l[24].toString());
			rm.setCustomerId(Objects.isNull(l[25]) ? "" : l[25].toString());
			rm.setPosEntryMode(Objects.isNull(l[26]) ? "" : l[26].toString());
			rm.setModel(Objects.isNull(l[27]) ? "" : l[27].toString());
			rm.setScheme(Objects.isNull(l[28]) ? "" : l[28].toString());
			rm.setTxnSerialNumber(Objects.isNull(l[29]) ? "" : l[29].toString());
			rm.setAuthIdN(Objects.isNull(l[30]) ? "" : l[30].toString());
			rm.setFieldOne(Objects.isNull(l[31]) ? "" : l[31].toString());
			rm.setSettlementResponse(Objects.isNull(l[32]) ? "" : l[32].toString());
			rm.setReason(Objects.isNull(l[33]) ? "" : l[33].toString());
			rm.setAdjAmount(Objects.isNull(l[34]) ? "" : l[34].toString());
			rm.setQuantityType(Objects.isNull(l[35]) ? "" : l[35].toString());
			rm.setPrdName(Objects.isNull(l[36]) ? "" : l[36].toString());
			rm.setPrdCode(Objects.isNull(l[37]) ? "" : l[37].toString());
			rm.setTipAmount(Objects.isNull(l[38]) ? "" : l[38].toString());
			rm.setCurrency(Objects.isNull(l[39]) ? "" : l[39].toString());
			rm.setQuantity(Objects.isNull(l[40]) ? "" : l[40].toString());
			rm.setMdrOnUs(Objects.isNull(l[41]) ? "" : l[41].toString());
			rm.setMdrOffUs(Objects.isNull(l[42]) ? "" : l[42].toString());
			rm.setFedRate(Objects.isNull(l[43]) ? "" : l[43].toString());
			rm.setNetAmount(Objects.isNull(l[44]) ? "" : l[44].toString());
			rm.setTvr(Objects.isNull(l[45]) ? "" : l[45].toString());
			rm.setAid(Objects.isNull(l[46]) ? "" : l[46].toString());
			rm.setCustomerName(Objects.isNull(l[47]) ? "" : l[47].toString());
			rm.setCardType(Objects.isNull(l[48]) ? "" : l[48].toString());
			rm.setCardExpiry(Objects.isNull(l[49]) ? "" : l[49].toString());
			rm.setMerchantName(Objects.isNull(l[50]) ? "" : l[50].toString());
			rm.setMerchantAddress(Objects.isNull(l[51]) ? "" : l[51].toString().replace("||", ","));
			rm.setPem(Objects.isNull(l[52]) ? "" : l[52].toString());
			rm.setPaymentDate(Objects.isNull(l[53]) ? "" : l[53].toString());
			rm.setAddress1(Objects.isNull(l[54]) ? "" : l[54].toString());
			rm.setAddress2(Objects.isNull(l[55]) ? "" : l[55].toString());
			rm.setAddress3(Objects.isNull(l[56]) ? "" : l[56].toString());
			rm.setAddress4(Objects.isNull(l[57]) ? "" : l[57].toString());
			rm.setEntryTimeStamp(Objects.isNull(l[58]) ? "" : l[58].toString());
			rm.setCardNumber(Objects.isNull(l[59]) ? "" : l[59].toString());
			rm.setAdditionalData(Objects.isNull(l[60]) ? "" : l[60].toString());
			try {
				rm.setImsi(Objects.isNull(l[61]) ? "" : l[61].toString());
			} catch (Exception e) {
				rm.setImsi("");
			}
			try {
				rm.setWlan(Objects.isNull(l[62]) ? "" : l[62].toString());
			} catch (Exception e) {
				rm.setWlan("");
			}
			try {
				rm.setSettledDateWithoutTime(Objects.isNull(l[63]) ? "" : l[63].toString());
			} catch (Exception e) {
				rm.setSettledDateWithoutTime("");
			}

			rmList.add(rm);

		}
		return rmList;

	}

	public static List<OfflineDetailsReportModel> offlineReportList(List<Object[]> lst) {
		List<OfflineDetailsReportModel> rmList = new ArrayList<OfflineDetailsReportModel>();
		for (Object[] l : lst) {
			OfflineDetailsReportModel rm = new OfflineDetailsReportModel();
			rm.setTxndetailid(Objects.isNull(l[0]) ? "" : l[0].toString());
			rm.setType(Objects.isNull(l[1]) ? "" : l[1].toString());
			rm.setFieldOne(Objects.isNull(l[2]) ? "" : l[2].toString());
			rm.setMerchantid(Objects.isNull(l[3]) ? "" : l[3].toString());
			rm.setTerminalid(Objects.isNull(l[4]) ? "" : l[4].toString());
			rm.setEntrydate(Objects.isNull(l[5]) ? "" : l[5].toString());
			rm.setTxnamount(Objects.isNull(l[6]) ? "" : l[6].toString());
			rm.setAuthid(Objects.isNull(l[7]) ? "" : l[7].toString());
			rm.setCustomername(Objects.isNull(l[8]) ? "" : l[8].toString());
			rm.setScheme(Objects.isNull(l[9]) ? "" : l[9].toString());
			rm.setModel(Objects.isNull(l[10]) ? "" : l[10].toString());
			rm.setCardType(Objects.isNull(l[11]) ? "" : l[11].toString());
			rm.setCardExpiry(Objects.isNull(l[12]) ? "" : l[12].toString());
			rm.setSerialNumber(Objects.isNull(l[13]) ? "" : l[13].toString());
			rm.setRrn(Objects.isNull(l[14]) ? "" : l[14].toString());
			rm.setMdrOnus(Objects.isNull(l[15]) ? "" : l[15].toString());
			rm.setSettledDate(Objects.isNull(l[16]) ? "" : l[16].toString());
			rm.setMdrOffus(Objects.isNull(l[17]) ? "" : l[17].toString());
			rm.setAdjAmount(Objects.isNull(l[18]) ? "" : l[18].toString());
			rm.setInvoiceNum(Objects.isNull(l[19]) ? "" : l[19].toString());
			rm.setFed(Objects.isNull(l[20]) ? "" : l[20].toString());
			rm.setTimAmount(Objects.isNull(l[21]) ? "" : l[21].toString());
			rm.setPosEntryMode(Objects.isNull(l[22]) ? "" : l[22].toString());
			rm.setNetAmount(Objects.isNull(l[23]) ? "" : l[23].toString());
			rm.setTvr(Objects.isNull(l[24]) ? "" : l[24].toString());
			rm.setAid(Objects.isNull(l[25]) ? "" : l[25].toString());
			rm.setSetteled(Objects.isNull(l[26]) ? "" : l[26].toString());
			rm.setBatchNumber(Objects.isNull(l[27]) ? "" : l[27].toString());
			rm.setFlightNumber(Objects.isNull(l[28]) ? "" : l[28].toString());
			rmList.add(rm);

		}
		return rmList;

	}

	public static List<DMSReportReportModel> dmsReportList(List<Object[]> lst) {
		List<DMSReportReportModel> rmList = new ArrayList<DMSReportReportModel>();
		for (Object[] l : lst) {
			DMSReportReportModel rm = new DMSReportReportModel();
			rm.setTotalDiscountedTxnCount(Objects.isNull(l[0]) ? "" : l[0].toString());
			rm.setTotalOriginalTxnAmount(Objects.isNull(l[1]) ? "" : l[1].toString());
			rm.setTotalDiscountAvailed(Objects.isNull(l[2]) ? "" : l[2].toString());
			rm.setTotalNetAmount(Objects.isNull(l[3]) ? "" : l[3].toString());
			rm.setBudgetAmountTotal(Objects.isNull(l[4]) ? "" : l[4].toString());
			rm.setBudgetAmountAvailable(Objects.isNull(l[5]) ? "" : l[5].toString());
			rm.setDiscAllowedTxtPerDay(Objects.isNull(l[6]) ? "" : l[6].toString());
			rm.setDiscId(Objects.isNull(l[7]) ? "" : l[7].toString());
			rm.setDiscName(Objects.isNull(l[8]) ? "" : l[8].toString());
			rm.setDiscDescription(Objects.isNull(l[9]) ? "" : l[9].toString());
			rm.setDiscStatus(Objects.isNull(l[10]) ? "" : l[10].toString());
			rm.setStartDate(Objects.isNull(l[11]) ? "" : l[11].toString());
			rm.setEndDate(Objects.isNull(l[12]) ? "" : l[12].toString());
			rm.setDiscountAmount(Objects.isNull(l[13]) ? "" : l[13].toString());
			rm.setDiscRate(Objects.isNull(l[14]) ? "" : l[14].toString());
			rmList.add(rm);

		}
		return rmList;
	}

	public List<KeyValueModel> convertObjToKeyValueModel(List<Object[]> objs) {
		List<KeyValueModel> fileUploadProp = null;
		if (!Objects.isNull(objs) && !objs.isEmpty()) {
			fileUploadProp = new ArrayList<KeyValueModel>();
			for (Object[] obj : objs) {
				KeyValueModel txnSummaryModel = new KeyValueModel();
				txnSummaryModel
						.setKey(!Objects.isNull(obj[0]) && !obj[0].toString().isEmpty() ? obj[0].toString() : "N/A");
				txnSummaryModel
						.setValue(!Objects.isNull(obj[1]) && !obj[1].toString().isEmpty() ? obj[1].toString() : "N/A");
				fileUploadProp.add(txnSummaryModel);
			}
		}
		return fileUploadProp;

	}

	public static List<DonationReportModel> donationReportList(List<Object[]> lst) {
		List<DonationReportModel> rmList = new ArrayList<DonationReportModel>();
		for (Object[] l : lst) {
			DonationReportModel rm = new DonationReportModel();
			rm.setTxndetailid(Objects.isNull(l[0]) ? "" : l[0].toString());
			rm.setType(Objects.isNull(l[1]) ? "" : l[1].toString());
			rm.setFieldOne(Objects.isNull(l[2]) ? "" : l[2].toString());
			rm.setMerchantid(Objects.isNull(l[3]) ? "" : l[3].toString());
			rm.setTerminalid(Objects.isNull(l[4]) ? "" : l[4].toString());
			rm.setEntrydate(Objects.isNull(l[5]) ? "" : l[5].toString());
			rm.setTxnamount(Objects.isNull(l[6]) ? "" : l[6].toString());
			rm.setAuthid(Objects.isNull(l[7]) ? "" : l[7].toString());
			rm.setCustomername(Objects.isNull(l[8]) ? "" : l[8].toString());
			rm.setScheme(Objects.isNull(l[9]) ? "" : l[9].toString());
			rm.setModel(Objects.isNull(l[10]) ? "" : l[10].toString());
			rm.setCardType(Objects.isNull(l[11]) ? "" : l[11].toString());
			rm.setRrn(Objects.isNull(l[12]) ? "" : l[12].toString());
			rm.setMdrOnus(Objects.isNull(l[13]) ? "" : l[13].toString());
			rm.setSettledDate(Objects.isNull(l[14]) ? "" : l[14].toString());
			rm.setMdrOffus(Objects.isNull(l[15]) ? "" : l[15].toString());
			rm.setAdjAmount(Objects.isNull(l[16]) ? "" : l[16].toString());
			rm.setInvoiceNum(Objects.isNull(l[17]) ? "" : l[17].toString());
			rm.setFed(Objects.isNull(l[18]) ? "" : l[18].toString());
			rm.setTimAmount(Objects.isNull(l[19]) ? "" : l[19].toString());
			rm.setPosEntryMode(Objects.isNull(l[20]) ? "" : l[20].toString());
			rm.setNetAmount(Objects.isNull(l[21]) ? "" : l[21].toString());
			rm.setTvr(Objects.isNull(l[22]) ? "" : l[22].toString());
			rm.setAid(Objects.isNull(l[23]) ? "" : l[23].toString());
			rm.setImsiNo(Objects.isNull(l[24]) ? "" : l[24].toString());
			rm.setAppVer(Objects.isNull(l[25]) ? "" : l[25].toString());
			rm.setDonationMid(Objects.isNull(l[26]) ? "" : l[26].toString());
			rm.setDonationTid(Objects.isNull(l[27]) ? "" : l[27].toString());
			rm.setDonationAccountTitle(Objects.isNull(l[28]) ? "" : l[28].toString());
			rm.setDonationAccount(Objects.isNull(l[29]) ? "" : l[29].toString());
			rmList.add(rm);

		}
		return rmList;

	}

	public static TxnLog insertTxnLogs(String activityType, String activityRemarks, String mid, String tid,
			String model, String serialNumber, String posDateTime, String imei, String telco, String longitude,
			String latitude, String fieldOne, String fielTwo, String fieldThree, String fieldFour, String fieldFive,
			String fieldSix) {
		TxnLog txn = new TxnLog();
		txn.setActivityRemarks(activityRemarks);
		txn.setActivityType(activityType);
		txn.setActivityDate(new java.sql.Timestamp(new java.util.Date().getTime()));
		txn.setMerchantId(mid);
		txn.setModel(model);
		txn.setScheme("-");
		txn.setSerialNumber(serialNumber);
		txn.setTerminalId(tid);

		txn.setPosdatetime(posDateTime);
		txn.setImei(imei);
		txn.setTelco(telco);
		txn.setLongitude(longitude);
		txn.setLatitude(latitude);
		txn.setFieldone(fieldOne);
		txn.setFieldtwo(fielTwo);
		txn.setFieldthree(fieldThree);
		txn.setFieldfour(fieldFour);
		txn.setFieldfive(fieldFive);
		txn.setFieldsix(fieldSix);

		AgLogger.logInfo("TXN LOGS SAVED " + mid + "|" + tid + "|" + activityType);
		return txn;

	}

	public static List<MerchantTerminalDetail> createAndSaveMerchantTerminalDetails(String mid, String tid,
			String telco, String latitude, String longitude, String fieldOne, String fieldTwo, String fieldThree,
			String fieldFour, String fieldFive, String fieldSix, String model, String serialNumber, String appName,
			String type, String iccid, Integer txnLogsId) {
		try {
			List<MerchantTerminalDetail> lst = new ArrayList<MerchantTerminalDetail>();
			MerchantTerminalDetail mtd;

			if (!Objects.isNull(telco) && !telco.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("TELCO");
				mtd.setDataValue(telco);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(latitude) && !latitude.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("LATITUDE");
				mtd.setDataValue(latitude);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(longitude) && !longitude.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("LONGITUDE");
				mtd.setDataValue(longitude);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(fieldOne) && !fieldOne.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("SIGNAL_STRENGHT");
				mtd.setDataValue(fieldOne);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(fieldTwo) && !fieldTwo.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("MEDIA_INTERFACE");
				mtd.setDataValue(fieldTwo);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(fieldThree) && !fieldThree.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("CPU_MEMORY");
				mtd.setDataValue(fieldThree);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(fieldFour) && !fieldFour.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("BATTERY");
				mtd.setDataValue(fieldFour);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(fieldFive) && !fieldFive.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("OS_ADK_FW");
				mtd.setDataValue(fieldFive);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(fieldSix) && !fieldSix.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("APP_VERSION");
				mtd.setDataValue(fieldSix);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(model) && !model.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("MODEL");
				mtd.setDataValue(model);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(serialNumber) && !serialNumber.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("SERIAL_NUM");
				mtd.setDataValue(serialNumber);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(appName) && !appName.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("APP_NAME");
				mtd.setDataValue(appName);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			if (!Objects.isNull(iccid) && !appName.isEmpty()) {
				mtd = new MerchantTerminalDetail();
				mtd.setEntryDate(new Timestamp(new java.util.Date().getTime()));
				mtd.setMid(mid);
				mtd.setTid(tid);
				mtd.setType(type);
				mtd.setDataName("ICCID");
				mtd.setDataValue(iccid);
				mtd.setTxnLogsId(!Objects.isNull(txnLogsId) ? txnLogsId : 0);
				lst.add(mtd);
			}

			return lst;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static TxnDetail doLogWithCustID(String cardNumber, String merchantID, String terminalID, String txnID,
			String amount, String response, String status, String type, String settled, String paid, String reversal,
			String batchNumber, String finResult, String refNumber, String ccType, int rec, String msg, String authID,
			String orignalRef, String invoiceNumber, String postMode, int custId, Date dateTime, String model,
			String cardScheme, String serialNumber, String authIDN, String fieldOne, String reason, String tipAmount,
			String adjAmount, String quantityType, String prdName, String prdCode, Integer quantity, Double netAmount,
			Double mdrOnUs, Double mdrOffUs, Double fedRates, String currency, String tvr, String aid, Date cardExpiry,
			String cardType, String customerName) {

		TxnDetail ql = new TxnDetail();
		ql.setEntryDate(new Timestamp(dateTime.getTime()));
		ql.setResponse(response);
		ql.setBin(cardNumber);
		ql.setTerminalId(terminalID);
		ql.setAmount(amount);
		ql.setMerchantId(merchantID);
		ql.setTransactionId(txnID);
		ql.setStatus(status);
		ql.setType(type);
		ql.setSetteled(settled);
		ql.setPaid(paid);
		ql.setReversal(reversal);
		ql.setBatchNumber(batchNumber);
		ql.setResultStatus(finResult);
		ql.setRefNum(refNumber);
		ql.setCardCcType(ccType);
		ql.setMsg(msg);
		ql.setBatchSettleRecords(rec);
		ql.setAuthId(authID);
		ql.setOrignalRef(orignalRef);
		ql.setInvoiceNum(invoiceNumber);
		ql.setPosEntryMode(postMode);
		ql.setCustId(custId);
		ql.setModel(model);
		ql.setScheme(cardScheme);
		ql.setSerialNumber(serialNumber);
		ql.setAuthIdN(authIDN);
		ql.setFieldOne(fieldOne);
		ql.setReason(reason);
		ql.setTipAmount(tipAmount);
		ql.setAdjAmount(adjAmount);
		ql.setQuantity(quantity);
		ql.setQuantityType(quantityType);
		ql.setPrdName(prdName);
		ql.setPrdCode(prdCode);
		ql.setNetAmount(String.valueOf(netAmount));
		ql.setMdrOffUs(mdrOffUs);
		ql.setMdrOnUs(mdrOnUs);
		ql.setFedRates(fedRates);
		ql.setCurrency(currency);
		ql.setAid(aid);
		ql.setTvr(tvr);
		ql.setCustomerName(customerName);
		ql.setCardType(cardType);
		ql.setCardExpiry(cardExpiry);
		return ql;
	}

	public static TxnLogError doLogForNonExistTxnRecord(String cardNumber, String merchantID, String terminalID,
			String txnID, String amount, String response, String status, String type, String settled, String paid,
			String reversal, String batchNumber, String finResult, String refNumber, String ccType, int rec, String msg,
			String authID, String orignalRef, String invoiceNumber, String postMode, int custId, Date dateTime,
			String model, String cardScheme, String serialNumber, String authIDN, String fieldOne, String reason,
			String tipAmount, String adjAmount, String quantityType, String prdName, String prdCode, Integer quantity,
			String tvr, String aid, Date cardExpiry, String cardType, String customerName) {

		TxnLogError ql = new TxnLogError();
		ql.setEntryDate(new Timestamp(dateTime.getTime()));
		ql.setResponse(response);
		ql.setBin(cardNumber);
		ql.setTerminalId(terminalID);
		ql.setAmount(amount);
		ql.setMerchantId(merchantID);
		ql.setTransactionId(txnID);
		ql.setStatus(status);
		ql.setType(type);
		ql.setSetteled(settled);
		ql.setPaid(paid);
		ql.setReversal(reversal);
		ql.setBatchNumber(batchNumber);
		ql.setResultStatus(finResult);
		ql.setRefNum(refNumber);
		ql.setCardCcType(ccType);
		ql.setMsg(msg);
		ql.setBatchSettleRecords(rec);
		ql.setAuthId(authID);
		ql.setOrignalRef(orignalRef);
		ql.setInvoiceNum(invoiceNumber);
		ql.setPosEntryMode(postMode);
		ql.setCustId(custId);
		ql.setModel(model);
		ql.setScheme(cardScheme);
		ql.setSerialNumber(serialNumber);
		ql.setAuthIdN(authIDN);
		ql.setFieldOne(fieldOne);
		ql.setReason(reason);
		ql.setTipAmount(tipAmount);
		ql.setAdjAmount(adjAmount);
		ql.setQuantity(quantity);
		ql.setQuantityType(quantityType);
		ql.setPrdName(prdName);
		ql.setPrdCode(prdCode);
		ql.setAid(aid);
		ql.setTvr(tvr);
		ql.setCustomerName(customerName);
		ql.setCardType(cardType);
		ql.setCardExpiry(cardExpiry);

		return ql;

	}

	public static double calcTxnNetAmount(Double amount, Double mdrOffUs, Double mdrOnUs, Double fedRate)
			throws ScriptException {
		Object objValue = null;
		ScriptEngine engine = null;
		String eq = null;
		try {
			eq = AppProp.getProperty("calc.net.tax.amount.eq");
			ScriptEngineManager mgr = new ScriptEngineManager();
			engine = mgr.getEngineByName("JavaScript");
			if (!Objects.isNull(eq) && !eq.isEmpty()) {
				eq = eq.replace("@AMOUNT", amount.toString());
				eq = eq.replace("@FEDRATES", fedRate.toString());
				eq = eq.replace("@MDROFFUS", mdrOffUs.toString());
				eq = eq.replace("@MDRONUS", mdrOnUs.toString());
			}

			AgLogger.logInfo("calcTxnNetAmount" + eq);

			objValue = engine.eval(eq);
		} catch (Exception e) {
			AgLogger.logerror(null, "EXCEPTION ", e);

		}
		if (!Objects.isNull(objValue)) {
			return new BigDecimal(engine.eval(eq).toString()).setScale(2, RoundingMode.CEILING).doubleValue();
		} else {
			return 0.00;
		}

	}

	public static double calcTxnMdrOffus(Double amount, Double mdrOffUs, Double mdrOnUs, Double fedRate)
			throws ScriptException {
		String eq = AppProp.getProperty("calc.mdrofus.tax.amount.eq");
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		if (!Objects.isNull(eq) && !eq.isEmpty()) {
			eq = eq.replace("@AMOUNT", amount.toString());
			eq = eq.replace("@FEDRATES", fedRate.toString());
			eq = eq.replace("@MDROFFUS", mdrOffUs.toString());
			eq = eq.replace("@MDRONUS", mdrOnUs.toString());
		}
		AgLogger.logInfo("calcTxnMdrOffus" + eq);
		Object objValue = engine.eval(eq);
		if (!Objects.isNull(objValue)) {
			return new BigDecimal(engine.eval(eq).toString()).setScale(2, RoundingMode.CEILING).doubleValue();
		} else {
			return 0.00;
		}

	}

	public static double calcTxnMdrOnus(Double amount, Double mdrOffUs, Double mdrOnUs, Double fedRate)
			throws ScriptException {
		String eq = AppProp.getProperty("calc.mdronu.tax.amount.eq");
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		if (!Objects.isNull(eq) && !eq.isEmpty()) {
			eq = eq.replace("@AMOUNT", amount.toString());
			eq = eq.replace("@FEDRATES", fedRate.toString());
			eq = eq.replace("@MDROFFUS", mdrOffUs.toString());
			eq = eq.replace("@MDRONUS", mdrOnUs.toString());
		}
		AgLogger.logInfo("calcTxnMdrOnus" + eq);
		Object objValue = engine.eval(eq);
		if (!Objects.isNull(objValue)) {
			return new BigDecimal(engine.eval(eq).toString()).setScale(2, RoundingMode.CEILING).doubleValue();
		} else {
			return 0.00;
		}

	}

	public static double calcTxnFed(Double amount, Double mdrOffUs, Double mdrOnUs, Double fedRate)
			throws ScriptException {
		String eq = AppProp.getProperty("calc.fed.tax.amount.eq");
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		if (!Objects.isNull(eq) && !eq.isEmpty()) {
			eq = eq.replace("@AMOUNT", amount.toString());
			eq = eq.replace("@FEDRATES", fedRate.toString());
			eq = eq.replace("@MDROFFUS", mdrOffUs.toString());
			eq = eq.replace("@MDRONUS", mdrOnUs.toString());
		}
		AgLogger.logInfo("calcTxnFed" + eq);
		Object objValue = engine.eval(eq);
		if (!Objects.isNull(objValue)) {
			return new BigDecimal(engine.eval(eq).toString()).setScale(2, RoundingMode.CEILING).doubleValue();
		} else {
			return 0.00;
		}

	}

	public static HashMap<String, String> lstToMapUserSetting(List<UserSetting> lstUserSetting) {
		HashMap<String, String> mapUserSetting = new HashMap<String, String>();
		for (UserSetting userSetting : lstUserSetting) {
			mapUserSetting.put(userSetting.getPropName(), userSetting.getPropValue());
		}
		return mapUserSetting;
	}

	public String[] validateQueueLog(QueueLog log) {
		String[] kl = new String[3];
		kl[0] = "00";
		kl[1] = "SUCCESS";
		String mPattern = "^0([3]{1})([0123456]{1})([0-9]{8})$";

		if (log.getMid().equals("") || log.getMid().equals("null") || log.getMid().equals(null)
				|| log.getMid() == null) {
			kl[0] = "01";
			kl[1] = "MERCHANT ID CAN NOT BE NULL";
			return kl;
		}
		if (log.getTid().equals("") || log.getTid().equals("null") || log.getTid().equals(null)
				|| log.getTid() == null) {
			kl[0] = "02";
			kl[1] = "TERMINAL ID CAN NOT BE NULL";
			return kl;
		}
		if (log.getModel().equals("") || log.getModel().equals("null") || log.getModel().equals(null)
				|| log.getModel() == null) {
			kl[0] = "03";
			kl[1] = "MODEL  CAN NOT BE NULL";
			return kl;
		}
		if (log.getMsdisn().equals("") || log.getMsdisn().equals("null") || log.getMsdisn().equals(null)
				|| log.getMsdisn() == null) {
			kl[0] = "04";
			kl[1] = "MOBILE NUMBER CAN NOT BE NULL";
			return kl;
		} else if (log.getMsdisn().length() != 11 || !Pattern.matches(mPattern, log.getMsdisn())) {
			kl[0] = "07";
			kl[1] = "INVALID MOBILE NUMBER";
			return kl;
		}
		if (log.getSerialNum().equals("") || log.getSerialNum().equals("null") || log.getSerialNum().equals(null)
				|| log.getSerialNum() == null) {
			kl[0] = "05";
			kl[1] = "SERIAL NUMBER CAN NOT BE NULL";
			return kl;
		}
		if (log.getEmail().equals("") || log.getEmail().equals("null") || log.getEmail().equals(null)
				|| log.getEmail() == null) {
			kl[0] = "06";
			kl[1] = "EMAIL CAN NOT BE NULL";
			return kl;
		} else if (!Pattern.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}", log.getEmail())) {
			kl[0] = "08";
			kl[1] = "INVALID EMAIL ADDRESS";
			return kl;
		}

		return kl;

	}

	public static UserSetting createUserSettingObj(String propValue, String propName, Integer userId,
			String loginUser) {
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());

		UserSetting userSetting = new UserSetting();

		userSetting.setPropName(propName);
		userSetting.setIsActive(1);
		userSetting.setEntryDate(ts);
		userSetting.setUserLoginId(userId);
		userSetting.setEntryBy(loginUser);

		if (Objects.isNull(propValue) || propValue.isEmpty()) {
			userSetting.setPropValue("0");
		} else {
			userSetting.setPropValue(propValue);
		}

		return userSetting;
	}

	public static ResponseModel downloadRoc(ReportModel txnDetail, String reportPath, PosEntryModeConfig pc)
			throws IOException, JRException {
		ResponseModel responseModel = new ResponseModel();
		responseModel.setCode("0000");
		responseModel.setMessage("SUCCESS");

		if (Objects.isNull(reportPath) || reportPath.isEmpty()) {
			responseModel.setCode("0001");
			responseModel.setMessage("Roc is not available.");
			return responseModel;
		}

		if (Objects.isNull(txnDetail)) {
			responseModel.setCode("0002");
			responseModel.setMessage("Record is not available.");
			return responseModel;
		}

		String imagePath = JasperUtil.generateReport(txnDetail, reportPath, pc);

		if (Objects.isNull(imagePath)) {
			responseModel.setCode("0004");
			responseModel.setMessage("Failed to Process ROC");
			return responseModel;
		}

		// Create BLOB of file
		try {
			File file = new File(imagePath);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			HashMap<Object, Object> obj = new HashMap<Object, Object>();
			obj.put("rocBlob", Base64.getEncoder().encodeToString(fileContent));
			obj.put("imagePath", imagePath);
			responseModel.setData(obj);
		} catch (IOException e) {
			responseModel.setCode("0004");
			responseModel.setMessage("Failed to Process ROC");
			throw new IOException("Unable to convert file to byte array. " + e.getMessage());
		} finally {

		}

		return responseModel;
	}

	public static String convertFileToBlob(String filePath) throws IOException {
		String blob = null;
		try {
			File file = new File(filePath);
			byte[] fileContent = Files.readAllBytes(file.toPath());
			blob = Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			blob = null;
			throw new IOException("Unable to convert file to byte array. " + e.getMessage());
		}
		return blob;
	}

	public static List<GroupDeclarModel> fillProvince(List<FedRates> lstFedRates) {
		selectProvinceItem = new ArrayList<GroupDeclarModel>();
		if (!lstFedRates.isEmpty()) {
			Map<String, GroupDeclarModel> grp = null;
			grp = new LinkedHashMap<String, GroupDeclarModel>();
			for (FedRates u : lstFedRates) {
				grp.put(String.valueOf(u.getProvince()),
						new GroupDeclarModel(String.valueOf(u.getProvince()), u.getProvince()));
			}
			ArrayList<GroupDeclarModel> listFedRates = new ArrayList<GroupDeclarModel>(grp.values());
			for (GroupDeclarModel foog : listFedRates) {
				selectProvinceItem.add(new GroupDeclarModel(foog.getKey(), foog.getValue()));
			}
		}
		return selectProvinceItem;
	}

	public static List<GroupDeclarModel> getSelectProvinceItem() {
		return selectProvinceItem;
	}

	public static void setSelectProvinceItem(List<GroupDeclarModel> selectProvinceItem) {
		UtilAccess.selectProvinceItem = selectProvinceItem;
	}

	public static HashMap<String, String> getConfigPropMap() {
		return configPropMap;
	}

	public static void setConfigPropMap(HashMap<String, String> configPropMap) {
		UtilAccess.configPropMap = configPropMap;
	}

	public static List<PosEntryModeConfig> getLstPemConfig() {
		return lstPemConfig;
	}

	public static void setLstPemConfig(List<PosEntryModeConfig> lstPemConfig) {
		UtilAccess.lstPemConfig = lstPemConfig;
	}

	public static boolean allowableToClose(UserLogin ukl, String status, String AssignedTo) {

		boolean f = true;
		if (status.equals("CLOSED")) {
			f = false;
		} else {
			if (ukl.getGroupCode() == Integer.parseInt(AppProp.getProperty("super.admin.group.code"))) {
				f = true;
			} else {
				try {
					if (Integer.parseInt(AssignedTo) == ukl.getUserId()) {
						f = true;
					} else {
						f = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					f = false;
				}
			}
		}

		return f;
	}

	public static boolean allowableToAsign(UserLogin ukl, String status, String AssignedTo) {

		boolean f = false;
		if (status.equals("CLOSED")) {
			f = false;
		} else {
			String assignRightsGroupCode = AppProp.getProperty("compl.assign.rights.group.code");
			if (assignRightsGroupCode.contains(",")) {
				String[] sp = assignRightsGroupCode.split(",");
				for (int k = 0; k < sp.length; k++) {
					if (ukl.getGroupCode() == Integer.parseInt(sp[k])) {
						f = true;
						break;
					}
				}
			} else if (ukl.getGroupCode() == Integer.parseInt(AppProp.getProperty("compl.assign.rights.group.code"))) {
				f = true;
			} else {
				f = false;
			}
		}

		return f;
	}

	public static String rrn() {
		return String.valueOf(System.currentTimeMillis()).substring(1, 13);
	}

	public List<TxnSummary> convertObjToTxnSummaryModel(List<Object[]> objs) {
		List<TxnSummary> fileUploadProp = null;
		if (!Objects.isNull(objs) && !objs.isEmpty()) {
			fileUploadProp = new ArrayList<TxnSummary>();
			for (Object[] obj : objs) {
				TxnSummary txnSummaryModel = new TxnSummary();
				txnSummaryModel.setCnt(
						!Objects.isNull(obj[0]) && !obj[0].toString().isEmpty() ? Integer.valueOf(obj[0].toString())
								: 0);
				txnSummaryModel.setAmount(
						!Objects.isNull(obj[1]) && !obj[1].toString().isEmpty() ? Float.valueOf(obj[1].toString()) : 0);
				txnSummaryModel.setTxnType(
						!Objects.isNull(obj[2]) && !obj[2].toString().isEmpty() ? obj[2].toString() : "N/A");
				fileUploadProp.add(txnSummaryModel);
			}
		}
		return fileUploadProp;

	}

	public static Integer getCount(Map<String, TxnSummary> map, String totalCountFormula) {
		Object objValue = null;
		ScriptEngine engine = null;
		try {

			ScriptEngineManager mgr = new ScriptEngineManager();
			engine = mgr.getEngineByName("JavaScript");
			if (!Objects.isNull(totalCountFormula) && !totalCountFormula.isEmpty()) {
				totalCountFormula = (totalCountFormula.contains("@PARAMCASH ADV")
						? totalCountFormula.replaceAll("\\@PARAMCASH ADV\\b",
								!Objects.isNull(map.get("CASH ADV")) ? map.get("CASH ADV").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMCASH OUT")
						? totalCountFormula.replaceAll("\\@PARAMCASH OUT\\b",
								!Objects.isNull(map.get("CASH OUT")) ? map.get("CASH OUT").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMCASHADV")
						? totalCountFormula.replaceAll("\\@PARAMCASHADV\\b",
								!Objects.isNull(map.get("CASHADV")) ? map.get("CASHADV").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMCOMPLETE")
						? totalCountFormula.replaceAll("\\@PARAMCOMPLETE\\b",
								!Objects.isNull(map.get("COMPLETE")) ? map.get("COMPLETE").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMCOMPLETION") ? totalCountFormula.replaceAll(
						"\\@PARAMCOMPLETION\\b",
						!Objects.isNull(map.get("COMPLETION")) ? map.get("COMPLETION").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMORBIT INQUIRY") ? totalCountFormula.replaceAll(
						"\\@PARAMORBIT INQUIRY\\b",
						!Objects.isNull(map.get("ORBIT INQUIRY")) ? map.get("ORBIT INQUIRY").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMPRE AUTH")
						? totalCountFormula.replaceAll("\\@PARAMPRE AUTH\\b",
								!Objects.isNull(map.get("PRE AUTH")) ? map.get("PRE AUTH").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMPREAUTH")
						? totalCountFormula.replaceAll("\\@PARAMPREAUTH\\b",
								!Objects.isNull(map.get("PREAUTH")) ? map.get("PREAUTH").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMREDEEM")
						? totalCountFormula.replaceAll("\\@PARAMREDEEM\\b",
								!Objects.isNull(map.get("REDEEM")) ? map.get("REDEEM").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMREFUND")
						? totalCountFormula.replaceAll("\\@PARAMREFUND\\b",
								!Objects.isNull(map.get("REFUND")) ? map.get("REFUND").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMSALE")
						? totalCountFormula.replaceAll("\\@PARAMSALE\\b",
								!Objects.isNull(map.get("SALE")) ? map.get("SALE").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMSALEIPP")
						? totalCountFormula.replaceAll("\\@PARAMSALEIPP\\b",
								!Objects.isNull(map.get("SALEIPP")) ? map.get("SALEIPP").getCnt().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMVOID")
						? totalCountFormula.replaceAll("\\@PARAMVOID\\b",
								!Objects.isNull(map.get("VOID")) ? map.get("VOID").getCnt().toString() : "0")
						: totalCountFormula);
			}

			AgLogger.logDebug("", "Total Summary count after Replacing: " + totalCountFormula);

			objValue = engine.eval(totalCountFormula);
		} catch (Exception e) {
			AgLogger.logerror(null, "Exception while counting equation execution Txn Summary" + e.getMessage(), e);
		}

		return !Objects.isNull(objValue) ? (Integer) objValue : 0;
	}

	public static Float getAmount(Map<String, TxnSummary> map, String totalCountFormula) {
		Object objValue = null;
		ScriptEngine engine = null;
		try {
			ScriptEngineManager mgr = new ScriptEngineManager();
			engine = mgr.getEngineByName("JavaScript");
			if (!Objects.isNull(totalCountFormula) && !totalCountFormula.isEmpty()) {
				totalCountFormula = (totalCountFormula.contains("@PARAMCASH ADV")
						? totalCountFormula.replaceAll("\\@PARAMCASH ADV\\b",
								!Objects.isNull(map.get("CASH ADV")) ? map.get("CASH ADV").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMCASH OUT")
						? totalCountFormula.replaceAll("\\@PARAMCASH OUT\\b",
								!Objects.isNull(map.get("CASH OUT")) ? map.get("CASH OUT").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMCASHADV")
						? totalCountFormula.replaceAll("\\@PARAMCASHADV\\b",
								!Objects.isNull(map.get("CASHADV")) ? map.get("CASHADV").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMCOMPLETE")
						? totalCountFormula.replaceAll("\\@PARAMCOMPLETE\\b",
								!Objects.isNull(map.get("COMPLETE")) ? map.get("COMPLETE").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMCOMPLETION") ? totalCountFormula.replaceAll(
						"\\@PARAMCOMPLETION\\b",
						!Objects.isNull(map.get("COMPLETION")) ? map.get("COMPLETION").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMORBIT INQUIRY")
						? totalCountFormula.replaceAll("\\@PARAMORBIT INQUIRY\\b",
								!Objects.isNull(map.get("ORBIT INQUIRY"))
										? map.get("ORBIT INQUIRY").getAmount().toString()
										: "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMPRE AUTH")
						? totalCountFormula.replaceAll("\\@PARAMPRE AUTH\\b",
								!Objects.isNull(map.get("PRE AUTH")) ? map.get("PRE AUTH").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMPREAUTH")
						? totalCountFormula.replaceAll("\\@PARAMPREAUTH\\b",
								!Objects.isNull(map.get("PREAUTH")) ? map.get("PREAUTH").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMREDEEM")
						? totalCountFormula.replaceAll("\\@PARAMREDEEM\\b",
								!Objects.isNull(map.get("REDEEM")) ? map.get("REDEEM").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMREFUND")
						? totalCountFormula.replaceAll("\\@PARAMREFUND\\b",
								!Objects.isNull(map.get("REFUND")) ? map.get("REFUND").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMSALE")
						? totalCountFormula.replaceAll("\\@PARAMSALE\\b",
								!Objects.isNull(map.get("SALE")) ? map.get("SALE").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMSALEIPP")
						? totalCountFormula.replaceAll("\\@PARAMSALEIPP\\b",
								!Objects.isNull(map.get("SALEIPP")) ? map.get("SALEIPP").getAmount().toString() : "0")
						: totalCountFormula);
				totalCountFormula = (totalCountFormula.contains("@PARAMVOID")
						? totalCountFormula.replaceAll("\\@PARAMVOID\\b",
								!Objects.isNull(map.get("VOID")) ? map.get("VOID").getAmount().toString() : "0")
						: totalCountFormula);
			}

			AgLogger.logDebug("", "Total Summary amount after Replacing: " + totalCountFormula);

			objValue = engine.eval(totalCountFormula);

			if (!Objects.isNull(objValue)) {
				return new BigDecimal(engine.eval(totalCountFormula).toString()).setScale(2, RoundingMode.CEILING)
						.floatValue();
			} else {
				return 0f;
			}

		} catch (Exception e) {
			AgLogger.logDebug("", "Exception while amount equation execution Txn Summary" + e.getMessage());
		}

		return 0f;
	}

	public static String exponent(String value) {
		String finVal = "";
		try {

			if (value.contains("E")) {
				double valueInExponentialFormat = Double.parseDouble(value);
				BigDecimal bd = new BigDecimal(valueInExponentialFormat);
				bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
				finVal = String.format("%.02f", bd);
			} else {
				finVal = value;
			}
		} catch (Exception e) {
			finVal = value;
		}
		return finVal;
	}

	public static TxnSummaryModel getTxnSummaryModel(List<TxnSummary> lstTxnSummary) {
		TxnSummaryModel txnSummaryModel = new TxnSummaryModel();
		NumberFormat nf2 = NumberFormat.getInstance(new Locale("en", "US"));

		if (!Objects.isNull(lstTxnSummary) && !lstTxnSummary.isEmpty()) {
			Map<String, TxnSummary> map = lstTxnSummary.stream()
					.collect(Collectors.toMap(TxnSummary::getTxnType, Function.identity()));

			txnSummaryModel.setCashOutAmount("PKR "
					+ String.valueOf(nf2.format(getAmount(map, AppProp.getProperty("calc.total.amount.cash.out")))));
			txnSummaryModel.setRedeemAmount("PKR "
					+ String.valueOf(nf2.format(getAmount(map, AppProp.getProperty("calc.total.amount.redeem")))));
			txnSummaryModel.setRefundAmount("PKR "
					+ String.valueOf(nf2.format(getAmount(map, AppProp.getProperty("calc.total.amount.refund")))));
			txnSummaryModel.setSaleAmount(
					"PKR " + String.valueOf(nf2.format(getAmount(map, AppProp.getProperty("calc.total.amount.sale")))));
			txnSummaryModel.setVoidAmount(
					"PKR " + String.valueOf(nf2.format(getAmount(map, AppProp.getProperty("calc.total.amount.void")))));
			txnSummaryModel.setTotalAmount("PKR "
					+ String.valueOf(nf2.format(getAmount(map, AppProp.getProperty("calc.total.amount.txn.summary")))));

			txnSummaryModel.setCashOutCount(getCount(map, AppProp.getProperty("calc.total.count.cash.out")));
			txnSummaryModel.setRedeemCount(getCount(map, AppProp.getProperty("calc.total.count.redeem")));
			txnSummaryModel.setRefundCount(getCount(map, AppProp.getProperty("calc.total.count.refund")));
			txnSummaryModel.setSaleCount(getCount(map, AppProp.getProperty("calc.total.count.sale")));
			txnSummaryModel.setVoidCount(getCount(map, AppProp.getProperty("calc.total.count.void")));
			txnSummaryModel.setTotalCount(getCount(map, AppProp.getProperty("calc.total.count.txn.summary")));

		} else {
			txnSummaryModel.setCashOutAmount("PKR " + String.valueOf(nf2.format(0)));
			txnSummaryModel.setRedeemAmount("PKR " + String.valueOf(nf2.format(0)));
			txnSummaryModel.setRefundAmount("PKR " + String.valueOf(nf2.format(0)));
			txnSummaryModel.setSaleAmount("PKR " + String.valueOf(nf2.format(0)));
			txnSummaryModel.setVoidAmount("PKR " + String.valueOf(nf2.format(0)));
			txnSummaryModel.setTotalAmount("PKR " + String.valueOf(nf2.format(0)));

			txnSummaryModel.setCashOutCount(0);
			txnSummaryModel.setRedeemCount(0);
			txnSummaryModel.setRefundCount(0);
			txnSummaryModel.setSaleCount(0);
			txnSummaryModel.setVoidCount(0);
			txnSummaryModel.setTotalCount(0);
		}

		return txnSummaryModel;
	}

	public Timestamp getTimeStamp(String date) {
		DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS");
		LocalDateTime localDateTime = LocalDateTime.from(formatDateTime.parse(date));
		return Timestamp.valueOf(localDateTime);
	}

	public ResponseModel downloadRocForApp(ReportModel txnDetail, String reportPath, PosEntryModeConfig pc)
			throws IOException, JRException {
		ResponseModel responseModel = new ResponseModel();
		responseModel.setCode("0000");
		responseModel.setMessage("SUCCESS");

		if (Objects.isNull(reportPath) || reportPath.isEmpty()) {
			responseModel.setCode("0001");
			responseModel.setMessage("Roc is not available.");
			return responseModel;
		}

		if (Objects.isNull(txnDetail)) {
			responseModel.setCode("0002");
			responseModel.setMessage("Record is not available.");
			return responseModel;
		}

		String imagePath = JasperUtil.generateReport(txnDetail, reportPath, pc);

		if (Objects.isNull(imagePath)) {
			responseModel.setCode("0004");
			responseModel.setMessage("Failed to Process ROC");
			return responseModel;
		}

		HashMap<Object, Object> obj = new HashMap<Object, Object>();
		obj.put("rocImagePath", imagePath);
		responseModel.setData(obj);

		return responseModel;
	}

	public static String dateFormatter(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			return sdf2.format(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<MidTidDetailsConfig> getMerchantTerminalDetailsConfigList() {
		return MerchantTerminalDetailsConfigList;
	}

	public static void setMerchantTerminalDetailsConfigList(
			List<MidTidDetailsConfig> merchantTerminalDetailsConfigList) {
		MerchantTerminalDetailsConfigList = merchantTerminalDetailsConfigList;
	}

}
