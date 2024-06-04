package com.mportal.ws.classes;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplAssignment;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserChannel;
import com.ag.generic.entity.UserLogin;
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
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.LdapUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.services.ChainMerchantsService;

@Component("com.mportal.ws.classes.WsCreateSupervisorUser")
public class WsCreateSupervisorUser implements Wisher {
	@Autowired
	UtilService utilService;
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	ChainMerchantsService chainMerchantsService;
	@Autowired
	ComplAssignmentsService chainAssignmentsService;
	@Autowired
	ComplaintsService complaintsService;
	@Autowired
	ComplChannelConfigService complChannelConfigService;
	@Autowired
	UserChannelsService userChannelsService;
	@Autowired
	SendNotifciation sendNotifciation;

	static String loginID = "N/A";

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			String userCode = rm.getAdditionalData().get("Usercode").toString();
			String userName = rm.getAdditionalData().get("UserName").toString();
			String msisdn = rm.getAdditionalData().get("Mobile").toString();
			String email = rm.getAdditionalData().get("Email").toString();
			String mid = rm.getAdditionalData().get("Mid").toString();
			String userloginId = rm.getUserid();

			String mPattern = "^0([3]{1})([0123456]{1})([0-9]{8})$";

			if (userCode == null || userCode.length() == 0) {
				return UtilAccess.generateResponse("8888", "UserID cant not be null.");
			} else if (userName == null || userName.length() == 0) {
				return UtilAccess.generateResponse("8888", "UserName can not be null.");
			} else if (msisdn == null || msisdn.length() == 0) {
				return UtilAccess.generateResponse("8888", "MSISDN can not be null.");
			}

			else if (email == null || email.length() == 0) {
				return UtilAccess.generateResponse("8888", "EMAIL can not be null.");
			} else if (!Pattern.matches(mPattern, msisdn)) {
				return UtilAccess.generateResponse("8888", "Please enter Valid Mobile Number.");
			} else if (!validate(email)) {
				return UtilAccess.generateResponse("8888", "Please enter Valid Email ID.");
			}

			else {
				if (mid.length() != 0 && userCode.length() != 0 && userName.length() != 0 && msisdn.length() != 0
						&& email.length() != 0 && userloginId.length() != 0) {
					boolean b = validateUser(userCode);
					if (b) {
						return UtilAccess.generateResponse("8888",
								"User Id " + userCode + " already exists.Please chose a differnt id.");
					} else {
						String[] result = createNewMerchantProcess(userCode, userName, mid, msisdn, email, 0, "001",
								"0002", "00001", AppProp.getProperty("supervisor.group.code"), "Y", userloginId,rm.getCorpId());
						String[] resp = new String[2];
						AgLogger.logInfo("message" + result[0] + "|" + result[1]);

						if (result[0].equals("0000")) {
							resp[0] = "0000";
							resp[1] = "SUCCESS";
							return UtilAccess.generateResponse("0000", "User Created Successfully.");
						} else {
							resp[0] = "1111";
							resp[1] = "FAIL";
							return UtilAccess.generateResponse("8888", "User Not Created Successfully.");
						}
					}
				} else {
					return UtilAccess.generateResponse("8888", "Please enter valid data.");
				}
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return UtilAccess.generateResponse("0000", "User Created Successfully.");
	}

	static boolean validate(String emailStr) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public String[] createNewMerchantProcess(String userID, String userName, String requestMidsss, String requestMobile,
			String requestEmail, int assignmentId, String category, String type, String subtype, String groupId,
			String flagChangeMerchant, String userAdminLoginId,String corpId) {
		String[] resp = new String[2];
		resp[0] = "0000";
		resp[1] = "SUCCESS";

		loginID = userAdminLoginId;

		String[] k = utilService.fetchMerchantUpd(requestMidsss);
		if (k[0].equals("0000")) {
			boolean bc = createUser(userID, generatePassword(userID, requestMobile), k[1], requestMobile);
			if (bc) {
				AgLogger.logInfo(userID + ": DONE");
				doUpdatePassword(userID, generatePassword(requestMidsss, requestMobile));
				int m = saveUser(userID, userName, requestMidsss, requestMobile, requestEmail, groupId,
						flagChangeMerchant,corpId);
				AgLogger.logInfo(userID + ": USER CREATE IN DB WITH ID" + m);

				if (assignmentId != 0) {
					updateCompleAssignment(assignmentId);
					AgLogger.logInfo(userID + ": USER UPDATE ASSIGNMENT DB WITH ID" + assignmentId);
				}
				doInsertDefaultChannelDetails(m, requestMobile, requestEmail, userID);
				AgLogger.logInfo(userID + ": USER INSERT CHANNEL WITH LOGIN ID" + m);

				SendNotificationModel sdm = new SendNotificationModel();
				UserLogin ukl = userLoginService.validetUserid(m);
				sdm.setAccountOpeningDate("N/A");
				sdm.setClosedBy("N/A");
				sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setComplaintNum("N/A");
				sdm.setMerchantName(userName);
				sdm.setPass(generatePassword(requestMidsss, requestMobile));
				sdm.setResolution("N/A");
				sdm.setUserName(userID);
				sdm.setReciverId(m);
				sendNotifciation.doTask(category, type, subtype, sdm, ukl.getUserId());

			} else {
				resp[0] = "7777";
				resp[1] = "ERROR CREATING NEW USER.";
			}
		} else {
			resp[0] = "8888";
			resp[1] = "NO MERCHANT FOUND. VERISYS.";
		}

		return resp;
	}

	public boolean validateUser(String mid) {
		boolean b = false;
		LDAPResponseModel rdl = new LdapUtil().getLdapValidate(mid);
		if (rdl.getCode().equals("0000")) {
			b = true;
		}
		return b;
	}

	boolean createUser(String mid, String pass, String mname, String mobile) {
		String url = AppProp.getProperty("ldap.url.create");
		JSONObject obj = new JSONObject();
		obj.put("user", mid);
		obj.put("pass", pass);
		obj.put("name", mname);
		obj.put("mobile", mobile);
		obj.put("mid", mid);

		boolean b = false;
		try {
			AgLogger.logInfo(url);
			AgLogger.logInfo(obj.toString());
			String sk = new HttpUtil().doPost(url, obj.toString());
			AgLogger.logInfo(sk);
			Map<String, String> ms = UtilAccess.convertMap(sk);
			String kskl = ms.get("code");
			String msg = ms.get("message");
			AgLogger.logInfo(kskl + "|" + msg);
			if (kskl.equals("0000}")) {
				b = true;
			}
		} catch (Exception e) {
			AgLogger.logTrace(getClass(), " EXCEPTION  ", e);

		}
		return b;
	}

	int saveUser(String useriD, String userName, String requestMid, String requestMobile, String requestEmail,
			String groupId, String flagChain,String corpId) {
		UserLogin usMDetails = userLoginService.validetUser(requestMid,corpId);
		String[] additionalData = usMDetails.getAdditionalData().split("||");

		int kl = 0;
		UserLogin ursl = new UserLogin();
		ursl.setCrBy(loginID);
		ursl.setCrOn(new Timestamp(new java.util.Date().getTime()));
		ursl.setIsActive(1);
		ursl.setUserCode(useriD);

		ursl.setGroupCode(Integer.parseInt(groupId));
		ursl.setUserName(userName);
		ursl.setUserType("external");
		ursl.setMid(requestMid);

		ursl.setCity(usMDetails.getCity());
		ursl.setForceLogin(1);
		ursl.setEmail(requestEmail);
		ursl.setMsisdn(requestMobile);
		ursl.setTempPass(generatePassword(requestMid, requestMobile));
		ursl.setCorpId(corpId);
		ursl.setIsReg(0);
		ursl.setAdditionalData("N/A || N/A || N/A || N/A");
		kl = userLoginService.insertUser(ursl);

		ChainMerchant smk = new ChainMerchant();
		smk.setAddress("");
		smk.setChainMerchantMid(requestMid);
		smk.setCity(usMDetails.getCity());
		smk.setEntryBy(loginID);
		smk.setEntryDate(new Timestamp(new java.util.Date().getTime()));
		smk.setIsChainMerchant(flagChain);
		smk.setMid(requestMid);
		smk.setName(userName);
		smk.setOffice(additionalData[2]);
		smk.setUserLoginId(kl);
		smk.setUserName(requestMid);
		chainMerchantsService.insert(smk);

		return kl;
	}

	void updateCompleAssignment(int assignmentID) {
		// Update complaint Assignment
		ComplAssignment cms = chainAssignmentsService.fetchByID(assignmentID);
		cms.setProcBy(loginID);
		cms.setProcDate(new Timestamp(new java.util.Date().getTime()));
		chainAssignmentsService.update(cms);

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

	void doInsertDefaultChannelDetails(int userLoginId, String requestMobile, String requestEmail, String requestMid) {
		List<String> jkl = complChannelConfigService.fetchAll();
		for (String ck : jkl) {
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

	static String generatePassword(String mid, String mobile) {
		return DBUtil.getName() + mid.substring(mid.length() - 2, mid.length())
				+ mobile.substring(mobile.length() - 2, mobile.length()) + "#@!";
	}

}