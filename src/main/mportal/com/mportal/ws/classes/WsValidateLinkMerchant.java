package com.mportal.ws.classes;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;

@Component("com.mportal.ws.classes.WsValidateLinkMerchant")
public class WsValidateLinkMerchant implements Wisher {

	@Autowired
	UserLoginService userLoginService;
	@Autowired
	UtilService utilService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String mid = null;
			String maker = "maker";
			String serialNum = "serialNum";
			String model = "model";
			String message = "message";
			String email = "email";
			String msisdn = "msisdn";
			String cnic = "cnic";
			String name = "name";
			try {
				mid = rm.getAdditionalData().get("mid").toString();
			} catch (Exception e) {

			}
			String userId = rm.getUserid();

			if (mid != null) {
				UserLogin user = userLoginService.validetUserid(Integer.parseInt(userId));

				String[] isAllowedToLInk = utilService.isAlreadyALinkMerchant(user.getMid());

				AgLogger.logInfo(rm.getUserid(), Arrays.toString(isAllowedToLInk));

				if (isAllowedToLInk[0].equals("0001")) {
					return UtilAccess.generateResponse("8888", "Merchant Already Linked with " + isAllowedToLInk[2]);
				} else {
					if (user.getMid().equals(mid)) {
						return UtilAccess.generateResponse("8888", "Self Linking not allowed.");

					} else {
						String[] k = utilService.fetchMerchantTerminalUpd(mid);

						if (k[0].equals("0000")) {
							UserLogin uskl = null;
							uskl = userLoginService.validetUser(mid,rm.getCorpId());
							if (uskl != null) {
								response.setCode("0000");
								response.setMessage("SUCCESS.");
								HashMap<Object, Object> mapResult = new HashMap<Object, Object>();
								mapResult.put(maker, k[3]);
								mapResult.put(serialNum, k[2]);
								mapResult.put(model, k[1]);
								mapResult.put(message, "");
								mapResult.put(name, uskl.getUserName());
								mapResult.put(msisdn, uskl.getMsisdn());
								mapResult.put(cnic, uskl.getCnic());
								mapResult.put(email, uskl.getEmail());
								response.setData(mapResult);
								
								return response;

							} else {
								return UtilAccess.generateResponse("8888", "No Merchant Found In Rec.");
							}
						} else {
							return UtilAccess.generateResponse("8888", "No Merchant Found.");
						}
					}
				}

			}
			else {
				return UtilAccess.generateResponse("8888", "Invalid MID.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}