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

@Component("com.mportal.ws.classes.WsServiceCheckForEmail")
public class WsServiceCheckForEmail implements Wisher{
	
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
			com.ag.notification.client.ResponseModel rms = createEmailNotification(user);
			response.setCode(rms.getCode());
			response.setMessage(rms.getMessage());}
			else {
				response.setCode("0001");
				response.setMessage("INVALID USER");
			}
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	public com.ag.notification.client.ResponseModel createEmailNotification(UserLogin user) {
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
		responseModel = notification.doSentTestingEmailNotification("999", "0001", "00006", sdm);
		return responseModel;
		// sendNotifciation.doTask("999", "0001", "00006", sdm);
	}

}