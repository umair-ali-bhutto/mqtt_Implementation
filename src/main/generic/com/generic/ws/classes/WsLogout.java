package com.generic.ws.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsLogout")
public class WsLogout implements Wisher {
	
	@Autowired
	UserLoginService userLoginService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			String UserId = rm.getUserid();
			UserLogin u = userLoginService.validetUserid(Integer.parseInt(UserId));
			u.setToken(null);
			u.setTokenGenerationTime(null);
			userLoginService.updateUser(u);
			response.setCode("0000");
			response.setMessage("Logout successful.");

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}