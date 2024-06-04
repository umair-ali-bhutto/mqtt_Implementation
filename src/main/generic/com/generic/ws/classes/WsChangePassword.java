package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.LDAPResponseModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.LdapUtil;
import com.ag.generic.util.UtilAccess;

@Component("com.generic.ws.classes.WsChangePassword")
public class WsChangePassword implements Wisher {
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	UtilService utilService; 

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String userId = rm.getUserid();
			String password = rm.getPassword();
			String newPassword = (String) rm.getAdditionalData().get("newPassword");
			String confirmnewPassword = (String) rm.getAdditionalData().get("confirmnewPassword");
			boolean isValidUser = false;
			UserLogin ukl = userLoginService.validetUser(userId, rm.getCorpId());
			if(ukl!=null) {
				isValidUser = true;
			}else {
				ukl = userLoginService.validetUserid(Integer.parseInt(userId));
				isValidUser = true;
			}
			if (isValidUser) {
				if (ukl.getUserType().equals("internal")) {
					if (UtilAccess.md5Java(password).equals(ukl.getPassword())) {
						if (confirmnewPassword.equals(newPassword)) {
							ukl.setPassword(UtilAccess.md5Java(confirmnewPassword));
							ukl.setUpdBy(ukl.getUserId() + "");
							ukl.setUpdOn(new Timestamp(new java.util.Date().getTime()));
							userLoginService.updateUser(ukl);
							response.setCode("0000");
							response.setMessage("Password Changed Successfully.");
							utilService.doSendAppNotificationOnly(Integer.parseInt(rm.getUserid()) , AppProp.getProperty("change.password.notif"), AppProp.getProperty("change.password.notif"));
						} else {
							response.setCode("0001");
							response.setMessage("New And Confirm Password Mismatched.");
						}
					} else {
						response.setCode("0001");
						response.setMessage("Current Password is Invalid.");
					}

				} else {
					LDAPResponseModel rmKL = new LdapUtil().getLdapLogin(ukl.getUserCode(), password);
					if (rmKL.getCode().equals("0000")) {
						if (confirmnewPassword.equals(newPassword)) {
							String[] k = doUpdatePassword(ukl.getUserCode(), newPassword);
							if (k[0].equals("0000")) {
								response.setCode(k[0]);
								response.setMessage("Password Changed Successfully.");
							} else {
								response.setCode("0001");
								response.setMessage("Ldap Password Change Error.");
							}
						} else {
							response.setCode("0001");
							response.setMessage("New And Confirm Password Mismatched.");
						}

					} else {
						response.setCode("0001");
						response.setMessage("Current Password is Invalid.");
					}

				}
			} else {
				response.setCode("0001");
				response.setMessage("Invalid User.");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo(rm.getUserid(), "EXCEPTION  :  " + this.getClass().getName() + ex);
			ex.printStackTrace();
		}

		return response;
	}

	public static String[] doUpdatePassword(String id, String pass) {

		String[] slm = new String[2];
		// String url = SystemProperties.getProperty("ldap.url.change.pwd");
		String url = AppProp.getProperty("ldap.url.change.pwd");
		url = url.replaceAll("@PARUSER", id);
		url = url.replaceAll("@PARPASS", pass);

		try {
			System.out.println(url);
			String sk = new HttpUtil().doGet(url);
			Map<String, String> ms = UtilAccess.convertMap(sk);
			String kskl = ms.get("code");
			slm[1] = ms.get("{msg");
			slm[0] = kskl.replaceAll("}", "");

		} catch (Exception e) {
			e.printStackTrace();
			AgLogger.logInfo(" EXCEPTION  " + e.getMessage());
			slm[0] = "9999";
			slm[1] = e.getMessage();
		}
		return slm;

	}

}
