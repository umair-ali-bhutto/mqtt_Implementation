package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.UtilAccess;

@Component("com.mportal.ws.classes.WsViewAllUsers")
public class WsViewAllUsers implements Wisher{
	@Autowired
	UserLoginService userLoginService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {

			String userId = rm.getAdditionalData().get("userid") == null ? null: rm.getAdditionalData().get("userid").toString();
			String mId = rm.getAdditionalData().get("mId") == null ? null: rm.getAdditionalData().get("mId").toString();
			String cnic = rm.getAdditionalData().get("cnic") == null ? null: rm.getAdditionalData().get("cnic").toString();
			String msisdn = rm.getAdditionalData().get("msisdn") == null ? null: rm.getAdditionalData().get("msisdn").toString();
			String merchantName = rm.getAdditionalData().get("merchantName")== null ? null : rm.getAdditionalData().get("merchantName").toString();
			String selectedCity = rm.getAdditionalData().get("selectedCity") == null ? null: rm.getAdditionalData().get("selectedCity").toString();
			String selectedRegion = rm.getAdditionalData().get("selectedCity") == null ? null : rm.getAdditionalData().get("selectedCity").toString();
			
			
			List<UserLogin> lstUsers = userLoginService.retrieveUsers(userId, mId, cnic, msisdn, merchantName,
					selectedCity, selectedRegion,rm.getCorpId());

			if (Objects.isNull(lstUsers) || lstUsers.isEmpty()) {
				response = UtilAccess.generateResponse("8888", "NO USER FOUND");
			} else {
				HashMap<Object, Object> mp = new HashMap<Object, Object>();
				mp.put("lst", lstUsers);
				response = UtilAccess.generateResponse("0000", "SUCCESS", mp);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}