package com.mportal.ws.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.SendNotifciation;

@Component("com.mportal.ws.classes.WsServiceCheckForSMS")
public class WsServiceCheckForSMS implements Wisher {

	@Autowired
	UserLoginService userLoginService;
	@Autowired
	SendNotifciation notification;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			String userId = rm.getUserid();

			UserLogin user = userLoginService.validetUserid(Integer.parseInt(userId)) ;
			if(user!=null) {
			com.ag.notification.client.ResponseModel rms = createSmsNotification(user);
			response.setCode(rms.getCode());
			response.setMessage(rms.getMessage());
		}
			else {
				response.setCode("0001");
				response.setMessage("INVALID USER");
			}
			
//			response.setCode("0000");
//			response.setMessage("active");

//			return response;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public com.ag.notification.client.ResponseModel createSmsNotification(UserLogin user) {
		SendNotificationModel sdm = new SendNotificationModel();
		com.ag.notification.client.ResponseModel responseModel = new com.ag.notification.client.ResponseModel();

		sdm.setMerchantName(user.getUserName());
		sdm.setReciverId(user.getUserId());

		sdm.setPass(null);
		sdm.setUserName(null);
		sdm.setClosedBy(null);
		sdm.setRequestComplDate(null);
		sdm.setClosureDate(null);
		sdm.setComplaintNum(null);
		sdm.setResolution(null);
		sdm.setAccountOpeningDate(null);
//		SendNotifciation notification = new SendNotifciation();
		responseModel = notification.doSentTestingSmsNotification("999", "0001", "00006", sdm, user.getUserId());
		return responseModel;

	}

}