package com.ag.mportal.model;
//package com.ag.util;
//
//import java.sql.Timestamp;
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONObject;
//
//import com.ag.entity.ChainMerchant;
//import com.ag.entity.ComplAssignment;
//import com.ag.entity.ComplChannelConfig;
//import com.ag.entity.Complaint;
//import com.ag.entity.UserChannel;
//import com.ag.entity.UserGroups;
//import com.ag.entity.UserLogin;
//import com.ag.group.model.SendNotificationModel;
//import com.ag.group.prop.AppProp;
//import com.ag.ldap.ad.LdapUtil;
//import com.ag.ldap.model.ResponseModel;
//import com.ag.servicesimpl.ChainMerchantsServiceImpl;
//import com.ag.servicesimpl.ComplAssignmentsServiceImpl;
//import com.ag.servicesimpl.ComplChannelConfigServiceImpl;
//import com.ag.servicesimpl.ComplaintsServiceImpl;
//import com.ag.servicesimpl.UserChannelsServiceImpl;
//import com.ag.servicesimpl.UserLoginServiceImpl;
//import com.ag.servicesimpl.UtilServiceImpl;
//
//public class CreateNewMerchantUtil {
//
//	static String mName = "";
//	static String loginID = "N/A";
//	
//
////	public String[] createNewMerchantProcess(String requestMid, String requestMobile, String requestEmail,
////			int assignmentId, String category, String type, String subtype, String groupId, String flagChangeMerchant,String userAdminLoginId) {
////		String[] resp = new String[2];
////		resp[0] = "0000";
////		resp[1] = "SUCCESS";
////		loginID = userAdminLoginId;
////		boolean b = new CreateNewMerchantUtil().validateUser(requestMid);
////		AgLogger.logInfo(rm.getUserid(), requestMid + ": VALIDATING USER :" + b);
////		if (!b) {
////			String[] k = new UtilServiceImpl().fetchMerchantUpd(requestMid);
////			if (k[0].equals("0000")) {
////				boolean bc = new CreateNewMerchantUtil().createUser(requestMid,
////						generatePassword(requestMid, requestMobile), k[1], requestMobile);
////				if (bc) {
////					AgLogger.logInfo(rm.getUserid(), requestMid + ": DONE");
////					doUpdatePassword(requestMid, generatePassword(requestMid, requestMobile));
////					int m = new CreateNewMerchantUtil().saveUser(requestMid, requestMobile, requestEmail, groupId,
////							flagChangeMerchant);
////					AgLogger.logInfo(rm.getUserid(), requestMid + ": USER CREATE IN DB WITH ID" + m);
////
////					if (assignmentId != 0) {
////						new CreateNewMerchantUtil().updateCompleAssignment(assignmentId);
////						AgLogger.logInfo(rm.getUserid(), requestMid + ": USER UPDATE ASSIGNMENT DB WITH ID" + assignmentId);
////					}
////					new CreateNewMerchantUtil().doInsertDefaultChannelDetails(m, requestMobile, requestEmail,
////							requestMid);
////					AgLogger.logInfo(rm.getUserid(), requestMid + ": USER INSERT CHANNEL WITH LOGIN ID" + m);
////
////					SendNotificationModel sdm = new SendNotificationModel();
////					sdm.setAccountOpeningDate("N/A");
////					sdm.setClosedBy("N/A");
////					sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
////					sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
////					sdm.setComplaintNum("N/A");
////					sdm.setMerchantName(mName);
////					sdm.setPass(generatePassword(requestMid, requestMobile));
////					sdm.setResolution("N/A");
////					sdm.setUserName(requestMid);
////					sdm.setReciverId(m);
////					sendNotifciation.doTask(category, type, subtype, sdm);
////					resp[0] = "0000";
////					resp[1] = "SUCESS.";
////
////				} else {
////					resp[0] = "7777";
////					resp[1] = "ERROR CREATING NEW USER.";
////				}
////			} else {
////				resp[0] = "8888";
////				resp[1] = "NO MERCHANT FOUND. VERISYS.";
////			}
////
////		} else {
////			resp[0] = "9999";
////			resp[1] = "ERRER.USER ALREADY EXISTS ON LDAP";
////		}
////
////		return resp;
////	}
//
//	boolean validateUser(String mid) {
//		boolean b = false;
//		ResponseModel rdl = new LdapUtil().getLdapValidate(mid);
//		if (rdl.getCode().equals("0000")) {
//			b = true;
//		}
//		return b;
//	}
//
//	boolean createUser(String mid, String pass, String mname, String mobile) {
//		String url = AppProp.getProperty("ldap.url.create");
//		JSONObject obj = new JSONObject();
//		obj.put("user", mid);
//		obj.put("pass", pass);
//		obj.put("name", mname);
//		obj.put("mobile", mobile);
//		obj.put("mid", mid);
//
//		boolean b = false;
//		try {
//			AgLogger.logInfo(rm.getUserid(), url);
//			AgLogger.logInfo(rm.getUserid(), obj.toString());
//			String sk = new HttpUtil().doPost(url, obj.toString());
//			AgLogger.logInfo(rm.getUserid(), sk);
//			Map<String, String> ms = UtilAccess.covnertMap(sk);
//			String kskl = ms.get("code");
//			String msg = ms.get("message");
//			AgLogger.logInfo(rm.getUserid(), kskl + "|" + msg);
//			if (kskl.equals("0000}")) {
//				b = true;
//			}
//		} catch (Exception e) {
//			AgLogger.logTrace(getClass(), " EXCEPTION  ", e);
//
//		}
//		return b;
//	}
//
//	int saveUser(String requestMid, String requestMobile, String requestEmail, String groupId, String flagChain) {
//		ResponseModel rdl = new LdapUtil().getLdapValidate(requestMid);
//		
//		int kl = 0;
//		if (rdl.getCode().equals("0000")) {
//			UserLogin ursl = new UserLogin();
//			ursl.setInsertBy(loginID);
//			ursl.setInsertOn(new Timestamp(new java.util.Date().getTime()));
//			ursl.setIsActive(1);
//			ursl.setUserAccountControl(rdl.getUsm().getUserAccountControl());
//			ursl.setUserAmaAccount(rdl.getUsm().getsAMAccountName());
//			ursl.setUserCn(rdl.getUsm().getCn());
//			ursl.setUserCode(requestMid);
//			ursl.setUserCountry(rdl.getUsm().getCo());
//			ursl.setUserCountryCode(rdl.getUsm().getCountryCode());
//			ursl.setUserDepartment(rdl.getUsm().getDepartment());
//			ursl.setUserDisplayName(rdl.getUsm().getDisplayName());
//			ursl.setUserDistinguishName(rdl.getUsm().getDistinguishedname());
//			ursl.setUserGivenName(rdl.getUsm().getGivenName());
//
//			ursl.setGroupCode(Integer.parseInt(groupId));
//			ursl.setUserName(rdl.getUsm().getsAMAccountName());
//			ursl.setUserPrincipleName(rdl.getUsm().getGivenName());
//			ursl.setUserStreetAddress(rdl.getUsm().getStreetAddress());
//			ursl.setUserTelephoneNumber(rdl.getUsm().getTelephoneNumber());
//			ursl.setUserType("external");
//			UserGroups grp = new UserLoginServiceImpl().getUserGroup(Integer.parseInt(groupId));
//			ursl.setUserGroup(grp.getGrpName());
//			ursl.setMid(requestMid);
//			String[] k = new UtilServiceImpl().fetchMerchantUpd(requestMid);
//			ursl.setMname(k[1]);
//			mName = k[1];
//			ursl.setMcity(k[2]);
//			ursl.setMaddress(k[3]);
//			ursl.setMfield(k[4]);
//			ursl.setForceLogin(1);
//			ursl.setUserEmail(requestEmail);
//			ursl.setUserMsisdn(requestMobile);
//			ursl.setTempPass(generatePassword(requestMid, requestMobile));
//			kl = new UserLoginServiceImpl().insertUser(ursl);
//
//			ChainMerchant smk = new ChainMerchant();
//			smk.setAddress("");
//			smk.setChainMerchantMid(requestMid);
//			smk.setCity(k[2]);
//			smk.setEntryBy(loginID);
//			smk.setEntryDate(new Timestamp(new java.util.Date().getTime()));
//			smk.setIsChainMerchant(flagChain);
//			smk.setMid(requestMid);
//			smk.setName(k[1]);
//			smk.setOffice(k[4]);
//			smk.setUserLoginId(kl);
//			smk.setUserName(requestMid);
//			new ChainMerchantsServiceImpl().insert(smk);
//
//		}
//		return kl;
//	}
//
//	void updateCompleAssignment(int assignmentID) {
//		// Update complaint Assignment
//		ComplAssignment cms = new ComplAssignmentsServiceImpl().fetchByID(assignmentID);
//		cms.setProcBy(loginID);
//		cms.setProcDate(new Timestamp(new java.util.Date().getTime()));
//		new ComplAssignmentsServiceImpl().update(cms);
//
//		// Update complaint
//		Complaint cks = new ComplaintsServiceImpl().fetchComplaintById(cms.getCompId());
//		cks.setStatus(UtilAccess.complStatusClosed);
//		cks.setClosedBy(loginID);
//		cks.setClosureDate(new Timestamp(new java.util.Date().getTime()));
//		cks.setIssueAddressed("Y");
//		cks.setReasonFailure("N/A");
//		cks.setResolution("NEW USER CREATED.");
//		new ComplaintsServiceImpl().updateComplaint(cks);
//	}
//
//	void doInsertDefaultChannelDetails(int userLoginId, String requestMobile, String requestEmail, String requestMid) {
//		List<ComplChannelConfig> jkl = new ComplChannelConfigServiceImpl().fetchAll();
//		for (ComplChannelConfig ck : jkl) {
//			UserChannel clm = new UserChannel();
//			clm.setChannel(ck.getChannel());
//			clm.setIsActive("Y");
//			clm.setUserLoginId(userLoginId);
//			if (ck.getChannel().equals("EMAIL")) {
//				clm.setValue(requestEmail);
//			} else {
//				clm.setValue(requestMobile);
//			}
//			new UserChannelsServiceImpl().insert(clm);
//		}
//
//	}
//
//	public String[] doUpdatePassword(String id, String pass) {
//
//		String[] slm = new String[2];
//		String url = AppProp.getProperty("ldap.url.change.pwd");
//		url = url.replaceAll("@PARUSER", id);
//		url = url.replaceAll("@PARPASS", pass);
//
//		try {
//			System.out.println(url);
//			String sk = new HttpUtil().doGet(url);
//			System.out.println(sk);
//			Map<String, String> ms = UtilAccess.covnertMap(sk);
//			String kskl = ms.get("code");
//			slm[1] = ms.get("message");
//
//			slm[0] = kskl.replaceAll("}", "");
//
//		} catch (Exception e) {
//			AgLogger.logTrace(getClass(), " EXCEPTION  ", e);
//			slm[0] = "9999";
//			slm[1] = e.getMessage();
//		}
//		return slm;
//
//	}
//
//	public static String generatePassword(String mid, String mobile) {
//		return DBUtil.getName() + mid.substring(mid.length() - 2, mid.length())
//				+ mobile.substring(mobile.length() - 2, mobile.length()) + "#@!";
//	}
//
//}
