package com.ag.generic.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.ag.mportal.entity.MerchantTerminalDetail;
import com.ag.mportal.entity.TxnReciept;

public interface UtilService {
	public String[] fetchMerchantUpd(String merchantID);

	public String[] fetchMerchantTerminalUpd(String merchantID, String tid);

	public void insertTxnReciept(TxnReciept rxn);

	public TxnReciept fetchTxnReciept(String rrn);

	public List<Object[]> fetchProfile(String model, String tid, String serialNum, String appName);

	public void updateVerisys(String mid, String tid, String serialNUmber, String model, String serial);

	public String[] fetchMerchantTerminalUpd(String merchantID);

	public String[] isAlreadyALinkMerchant(String merchantID);

	public List<String> getTidByMid(String merchantID);

	public String[] getLocationsName(String regionCode, String cityCode, String countryCode);

	public Integer[] getStats();

	public String getDbDetails();

	public void doSendSmsAppNotifications(String mobile, String smsText, int userId, String apptext,
			String apptextDetail);

	public void doSendEmailAppNotifications(String mobile, String smsText, String email, String emailSub,
			String emailText, int userId, String apptext, String apptextDetail);

	public void doSendSmsEmailAppNotifications(String mobile, String smsText, String email, String emailSub,
			String emailText, int userId, String apptext, String apptextDetail);

	public void doSendSmsEmail(String mobile, String smsText, String email, String emailSub, String emailText,
			String txnRef);

	public void doSendAppNotificationOnly(int userId, String apptext, String apptextDetail);

	public void doSendEmailOnly(String email, String emailSub, String emailText, String txnRef, String corpId);

	public void doSendSmsOnly(String mobile, String smsText, String corpId);

	public String[] responseServiceTxn(String xmls);

	public void processServiceSettelment(String xmls);

	public void doSendAppNotificationOnly(int userId, String apptext, String apptextDetail, String msgType,
			JSONObject content);

	public void setPlayerIdsPushNotificationModel(int userLoginId, String playerId, String channel);

	public String[] responseServiceFileAck(String xmls);

	public boolean isEditRights(String propValue, int groupCode);

	public void saveToMerchantTerminalDetails(List<MerchantTerminalDetail> lst);

	public List<String> fetchMidsByName(String name);

}
