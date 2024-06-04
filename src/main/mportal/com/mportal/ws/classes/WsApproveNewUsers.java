
package com.mportal.ws.classes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserChannel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.entity.UserScreen;
import com.ag.generic.entity.UserSetting;
import com.ag.generic.model.ComplAssignmentsCustom;
import com.ag.generic.model.LDAPResponseModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplAssignmentsService;
import com.ag.generic.service.ComplChannelConfigService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserChannelsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserSettingService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.LdapUtil;
import com.ag.generic.util.PasswordGenerator;
import com.ag.generic.util.PortalNotificationUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.entity.QueueLog;
import com.ag.mportal.services.ChainMerchantsService;
import com.ag.mportal.services.FedRateService;
import com.ag.mportal.services.QueueLogService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component("com.mportal.ws.classes.WsApproveNewUsers")
public class WsApproveNewUsers implements Wisher {

	@Autowired
	UtilService utilService;
	@Autowired
	ComplChannelConfigService complChannelConfigService;
	@Autowired
	ComplAssignmentsService complAssignmentsService;
	@Autowired
	ComplaintsService complaintsService;
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	ChainMerchantsService chainMerchantsService;
	@Autowired
	UserChannelsService userChannelsService;
	@Autowired
	FedRateService fedRateService;
	@Autowired
	UserSettingService userSettingService;
	@Autowired
	SendNotifciation sendNotifciation;
	@Autowired
	QueueLogService queueLogService;

	@Autowired
	PortalNotificationUtil portalNotificationUtil;
	
	static String mName = "";
	static String loginID = "N/A";

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			// String selectedComp = .toString();
			String json = new Gson().toJson(rm.getAdditionalData().get("selectedComp"));
			String userId = rm.getUserid();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			ArrayList<ComplAssignmentsCustom> selectedComplaint = gson.fromJson(json,
					new TypeToken<ArrayList<ComplAssignmentsCustom>>() {
					}.getType());

			String[] respString = null;
			if (selectedComplaint.size() != 0) {
				respString = new String[selectedComplaint.size()];
				int i = 0;
				for (ComplAssignmentsCustom com : selectedComplaint) {
					String[] a = validateObj(com);
					if (a[0].equals("00")) {
						String[] s = createNewMerchantProcess(rm, com.getMid(), com.getMsdisn(), com.getEmail(),
								com.getId(), "001", "0001", "00001", AppProp.getProperty("defult.merchant.group"), "N",
								userId, com.getMdrOnUs(), com.getMdrOffUs(), com.getProvince(),
								fedRateService.retrieveRateByProvince(com.getProvince()).getRateValue().toString(),
								rm.getCorpId());

						QueueLog queueLog = new Gson().fromJson(com.getDescription(), QueueLog.class);
						if (s[0].equals("0000")) {
							queueLog.setStatus("APPROVED");
							queueLog.setRemarks(com.getMid() + " is approved by " + userId);
						} else {
							queueLog.setStatus("ERROR");
							queueLog.setRemarks(com.getMid() + " is in error state.");
						}
						queueLogService.update(queueLog);
						respString[i] = com.getMid() + ":" + s[1];
					} else {
						respString[i] = com.getMid() + ":" + a[1];
					}
					i++;
				}
				response.setCode("0000");
				response.setMessage(Arrays.toString(respString));
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				HashMap<Object, Object> mpk = portalNotificationUtil.getNotificationsUpdated(Integer.parseInt(userId),rm.getCorpId());
				map.put("notifications", mpk.get("notifications"));
				map.put("notifications_count", mpk.get("notifications_count"));
				response.setData(map);

			} else {
				response.setCode("8888");
				response.setMessage("No User List Selected.");
			}
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String[] createNewMerchantProcess(RequestModel rm, String requestMid, String requestMobile,
			String requestEmail, int assignmentId, String category, String type, String subtype, String groupId,
			String flagChangeMerchant, String userAdminLoginId, String mdrOnUs, String mdrOffUs, String province,
			String fedRateValue, String corpId) {
		String[] resp = new String[2];
		resp[0] = "0000";
		resp[1] = "Merchant Registered.";
		loginID = userAdminLoginId;
		boolean b = validateUser(requestMid);
		AgLogger.logInfo(rm.getUserid(), requestMid + ": VALIDATING USER :" + b);
		if (!b) {
			String[] k = utilService.fetchMerchantUpd(requestMid);
			if (k[0].equals("0000")) {
				String passwordGenerated = PasswordGenerator.generateStrongPassword();
				boolean bc = createUser(rm, requestMid, passwordGenerated, k[1], requestMobile);
				if (bc) {
					AgLogger.logInfo(rm.getUserid(), requestMid + ": DONE");
					doUpdatePassword(requestMid, passwordGenerated);
					int m = saveUser(requestMid, requestMobile, requestEmail, groupId, flagChangeMerchant, mdrOnUs,
							mdrOffUs, province, fedRateValue, corpId, passwordGenerated);
					AgLogger.logInfo(rm.getUserid(), requestMid + ": USER CREATE IN DB WITH ID" + m);

					if (assignmentId != 0) {
						updateCompleAssignment(assignmentId);
						AgLogger.logInfo(rm.getUserid(),
								requestMid + ": USER UPDATE ASSIGNMENT DB WITH ID" + assignmentId);
					}
					doInsertDefaultChannelDetails(m, requestMobile, requestEmail, requestMid);
					AgLogger.logInfo(rm.getUserid(), requestMid + ": USER INSERT CHANNEL WITH LOGIN ID" + m);

					SendNotificationModel sdm = new SendNotificationModel();
					sdm.setAccountOpeningDate("N/A");
					sdm.setClosedBy("N/A");
					sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
					sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
					sdm.setComplaintNum("N/A");
					sdm.setMerchantName(mName);
					sdm.setPass(passwordGenerated);
					sdm.setResolution("N/A");
					sdm.setUserName(requestMid);
					sdm.setReciverId(m);
					sendNotifciation.doTask(category, type, subtype, sdm, m);
					resp[0] = "0000";
					resp[1] = "Merchant Registered.";

				} else {
					resp[0] = "7777";
					resp[1] = "Error Creating New User.";
				}
			} else {
				resp[0] = "8888";
				resp[1] = "No Merchant Found On VERISYS.";
			}

		} else {
			resp[0] = "9999";
			resp[1] = "Error Already Exists On LDAP.";
		}

		return resp;
	}

	boolean validateUser(String mid) {
		boolean b = false;
		LDAPResponseModel rdl = new LdapUtil().getLdapValidate(mid);
		if (rdl.getCode().equals("0000")) {
			b = true;
		}
		return b;
	}

	boolean createUser(RequestModel rm, String mid, String pass, String mname, String mobile) {
		String url = AppProp.getProperty("ldap.url.create");
		JSONObject obj = new JSONObject();
		obj.put("user", mid);
		obj.put("pass", pass);
		obj.put("name", mname);
		obj.put("mobile", mobile);
		obj.put("mid", mid);

		boolean b = false;
		try {
			AgLogger.logInfo(rm.getUserid(), url);
			AgLogger.logInfo(rm.getUserid(), obj.toString());
			String sk = new HttpUtil().doPost(url, obj.toString());
			AgLogger.logInfo(rm.getUserid(), sk);
			Map<String, String> ms = UtilAccess.convertMap(sk);
			String kskl = ms.get("code");
			String msg = ms.get("message");
			AgLogger.logInfo(rm.getUserid(), kskl + "|" + msg);
			if (kskl.equals("0000}")) {
				b = true;
			}
		} catch (Exception e) {
			AgLogger.logTrace(getClass(), " EXCEPTION  ", e);

		}
		return b;
	}

//	public static String generatePassword(String mid, String mobile) {
//		return DBUtil.getName() + mid.substring(mid.length() - 2, mid.length())
//				+ mobile.substring(mobile.length() - 2, mobile.length()) + "#@!";
//	}

	public String[] doUpdatePassword(String id, String pass) {

		String[] slm = new String[2];
		String url = AppProp.getProperty("ldap.url.change.pwd");
		url = url.replaceAll("@PARUSER", id);
		url = url.replaceAll("@PARPASS", pass);

		try {
			System.out.println(url);
			String sk = new HttpUtil().doGet(url);
			System.out.println(sk);
			Map<String, String> ms = UtilAccess.convertMap(sk);
			String kskl = ms.get("code");
			slm[1] = ms.get("message");

			slm[0] = kskl.replaceAll("}", "");

		} catch (Exception e) {
			AgLogger.logTrace(getClass(), " EXCEPTION  ", e);
			slm[0] = "9999";
			slm[1] = e.getMessage();
		}
		return slm;

	}

	void doInsertDefaultChannelDetails(int userLoginId, String requestMobile, String requestEmail, String requestMid) {
		List<String> strChannelList = new ArrayList<String>();
		List<String> jkl = complChannelConfigService.fetchAll();
		for (String c : jkl) {
			strChannelList.add(c);
		}
		List<String> listWithoutDuplicates = strChannelList.stream().distinct().collect(Collectors.toList());
		for (String ck : listWithoutDuplicates) {
			UserChannel clm = new UserChannel();
			clm.setChannel(ck);
			clm.setIsActive("Y");
			clm.setUserLoginId(userLoginId);
			if (ck.equals("EMAIL")) {
				clm.setValue(requestEmail);
			} else {
				clm.setValue(requestMobile);
			}
			userChannelsService.insert(clm);
		}

	}

	void updateCompleAssignment(int assignmentID) {
		// Update complaint Assignment
		ComplAssignment cms = complAssignmentsService.fetchByID(assignmentID);
		cms.setProcBy(loginID);
		cms.setProcDate(new Timestamp(new java.util.Date().getTime()));
		complAssignmentsService.update(cms);

		// Update complaint
		Complaint cks = complaintsService.fetchComplaintById(cms.getCompId());
		cks.setStatus(UtilAccess.complStatusClosed);
		cks.setClosedBy(loginID);
		cks.setClosureDate(new Timestamp(new java.util.Date().getTime()));
		cks.setIssueAddressed("Y");
		cks.setReasonFailure("N/A");
		cks.setResolution("NEW USER CREATED.");
		complaintsService.updateComplaint(cks);
	}

	int saveUser(String requestMid, String requestMobile, String requestEmail, String groupId, String flagChain,
			String mdrOnUs, String mdrOffUs, String province, String fedRateValue, String corpId,
			String passwordGenerated) {
		LDAPResponseModel rdl = new LdapUtil().getLdapValidate(requestMid);

		int kl = 0;
		rdl.setCode("0000");
		if (rdl.getCode().equals("0000")) {
			UserLogin ursl = new UserLogin();
			ursl.setCrBy(loginID);
			ursl.setCrOn(new Timestamp(new java.util.Date().getTime()));
			ursl.setIsActive(1);
			ursl.setUserCode(requestMid);

			ursl.setGroupCode(Integer.parseInt(groupId));
			ursl.setUserName(rdl.getUsm().getsAMAccountName());
			ursl.setUserType("external");
			ursl.setMid(requestMid);
			String[] k = utilService.fetchMerchantUpd(requestMid);
			ursl.setAdditionalData(k[2] + "||" + k[3] + "||" + k[4] + "||" + k[5]);

			ursl.setUserName(k[1]);
			mName = k[1];
			ursl.setCity("N/A");
			ursl.setForceLogin(1);
			ursl.setEmail(requestEmail);
			ursl.setMsisdn(requestMobile);
			ursl.setTempPass(passwordGenerated);
			ursl.setCorpId(corpId);
			ursl.setIsReg(0);
			ursl.setFirstName(k[1]);
			kl = userLoginService.insertUser(ursl);

			ChainMerchant smk = new ChainMerchant();
			smk.setAddress("");
			smk.setChainMerchantMid(requestMid);
			smk.setCity(k[2]);
			smk.setEntryBy(loginID);
			smk.setEntryDate(new Timestamp(new java.util.Date().getTime()));
			smk.setIsChainMerchant(flagChain);
			smk.setMid(requestMid);
			smk.setName(k[1]);
			smk.setOffice(k[4]);
			smk.setUserLoginId(kl);
			smk.setUserName(requestMid);
			chainMerchantsService.insert(smk);

			List<UserSetting> lstUserSettings = new ArrayList<UserSetting>();
			lstUserSettings.add(UtilAccess.createUserSettingObj(mdrOnUs, "MDR_ON_US", kl, loginID));
			lstUserSettings.add(UtilAccess.createUserSettingObj(mdrOffUs, "MDR_OFF_US", kl, loginID));
			lstUserSettings.add(UtilAccess.createUserSettingObj(province, "PROVINCE", kl, loginID));
			lstUserSettings.add(UtilAccess.createUserSettingObj(fedRateValue, "FED_RATES", kl, loginID));
			userSettingService.insertLstUserSettings(lstUserSettings);

		}
		return kl;
	}

	String[] validateObj(ComplAssignmentsCustom complAssignmentsCustom) {
		String[] s = new String[2];
		boolean isValid = true;
		s[0] = "";
		s[1] = "";

		if (Objects.isNull(complAssignmentsCustom.getMdrOnUs()) || complAssignmentsCustom.getMdrOnUs().isEmpty()) {
			s[0] = "11";
			s[1] += "MDR ON US IS REQUIRED.";
			isValid = false;
		} else {
			if (!NumberUtils.isParsable(complAssignmentsCustom.getMdrOnUs())) {
				s[0] = "11";
				s[1] += "MDR ON US SHOULD BE NUMBER.";
				isValid = false;
			}
		}

		if (Objects.isNull(complAssignmentsCustom.getMdrOffUs()) || complAssignmentsCustom.getMdrOffUs().isEmpty()) {
			s[0] = "11";
			s[1] += "MDR OFF US IS REQUIRED.";
			isValid = false;
		} else {
			if (!NumberUtils.isParsable(complAssignmentsCustom.getMdrOffUs())) {
				s[0] = "11";
				s[1] += "MDR OFF US SHOULD BE NUMBER.";
				isValid = false;
			}
		}

		if (Objects.isNull(complAssignmentsCustom.getProvince()) || complAssignmentsCustom.getProvince().isEmpty()) {
			s[0] = "11";
			s[1] += "PROVINCE IS REQUIRED.";
			isValid = false;
		}

		if (isValid) {
			s[0] = "00";
			s[1] = "SUCCESS";
		}

		return s;
	}

	public Map<Integer, UserScreen> convertListToMapScreen(List<UserScreen> list) {
		Map<Integer, UserScreen> map = list.stream()
				.collect(Collectors.toMap(UserScreen::getScreenId, Function.identity()));
		return map;
	}
}