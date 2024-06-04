package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserChannel;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.UserChannelsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.UtilAccess;



@Component("com.generic.ws.classes.WsForceLogin")
public class WsForceLogin implements Wisher{
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	UserChannelsService userChannelService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String userId = rm.getUserid();
			String msisdn = rm.getAdditionalData().get("msisdn").toString();
			String email = rm.getAdditionalData().get("email").toString();
			String city = rm.getAdditionalData().get("city").toString();
			String region = rm.getAdditionalData().get("region").toString();
			String cnic = rm.getAdditionalData().get("cnic").toString();
			String channels = rm.getAdditionalData().get("channels").toString();
			String newPass = rm.getAdditionalData().get("newPass").toString();
			String newConfirmPass = rm.getAdditionalData().get("newConfirmPass").toString();

			UserLogin userModel = userLoginService.validetUser(userId,rm.getCorpId());
			
			String[] channelSelected = channels.split(",");

			String mPattern = "^0([3]{1})([0123456]{1})([0-9]{8})$";
			String cnicnPattern = "[0-9]{13}";

			if (userModel != null) {
				if (msisdn == null || msisdn.length() == 0) {
					response = UtilAccess.generateResponse("8888", "Msisdn cant not be null.");
					return response;
				} else if (email == null || email.length() == 0) {
					response = UtilAccess.generateResponse("8888", "Email can not be null.");
					return response;
				}

				else if (city == null || city.length() == 0) {
					response = UtilAccess.generateResponse("8888", "City can not be null.");
					return response;
				} else if (region == null || region.length() == 0) {
					response = UtilAccess.generateResponse("8888", "City can not be null.");
					return response;
				}

				else if (cnic == null || cnic.length() == 0) {
					response = UtilAccess.generateResponse("8888", "CNIC can not be null.");
					return response;
				}

				else if (channels.length() == 0) {
					response = UtilAccess.generateResponse("8888", "Please select medium of communication.");
					return response;
				}

				else if (newPass == null || newPass.length() == 0) {
					response = UtilAccess.generateResponse("8888", "New Password can not be empty.");
					return response;
				}

				else if (newConfirmPass == null || newConfirmPass.length() == 0) {
					response = UtilAccess.generateResponse("8888", "Retype New Password can not be empty.");
					return response;
				}

				else if (!newConfirmPass.equals(newPass)) {
					response = UtilAccess.generateResponse("8888", "Password Mismatched.");
					return response;
				} else if (!Pattern.matches(mPattern, msisdn)) {
					response = UtilAccess.generateResponse("8888", "Please enter Valid Mobile Number.");
					return response;
				} else if (!Pattern.matches(cnicnPattern, cnic)) {
					response = UtilAccess.generateResponse("8888", "Please Enter Valid CNIC.");
					return response;
				} else {
					
					String[] gk = doUpdatePassword(userModel.getUserCode(), newPass, userModel.getUserType());
					if (gk[0].equals("0000")) {
						userChannelService.delete(userModel.getUserId());

						if (userModel.getUserType().equalsIgnoreCase("internal")) {
							userModel.setIsReg(1);
							userModel.setPassword(UtilAccess.md5Java(newPass));
						}else {
							userModel.setIsReg(1);
							userModel.setPassword(null);
						}
						
						userModel.setCity(city);
						userModel.setRegion(region);
						userModel.setCnic(cnic);
						userModel.setEmail(email);
						userModel.setMsisdn(msisdn);
						userModel.setUpdBy(userId);
						userModel.setUpdOn(new Timestamp(new java.util.Date().getTime()));
					
						userModel.setForceLogin(0);
						userModel.setTempPass(null);
						userLoginService.updateUser(userModel);
						for (String ck : channelSelected) {
							
							ck = ck.replace("]", "");
							ck = ck.replace("[", "");
							ck = ck.trim();
							UserChannel clm = new UserChannel();
							clm.setChannel(ck);
							clm.setIsActive("Y");
							clm.setUserLoginId(userModel.getUserId());
							if (ck.equalsIgnoreCase("email")) {
								clm.setValue(email);
							} else {
								clm.setValue(msisdn);
							}
							userChannelService.insert(clm);
						}
						response = UtilAccess.generateResponse("0000",
								"Password has been changed, please login again.");
					}

					else {
						response = UtilAccess.generateResponse("8888", "Password change failed.");
					}
				}

			} else {
				response.setCode("8888");
				response.setMessage("NO USER FOUND.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public String[] doUpdatePassword(String id, String pass, String userType) {

		String[] slm = new String[2];

		if (userType.equalsIgnoreCase("internal")) {
			slm[1] = "SUCCESS";
			slm[0] = "0000";
		} else {
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

		}

		return slm;

	}

}