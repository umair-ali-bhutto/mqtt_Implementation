package com.ag.generic.action.approve;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.model.ChangePinServiceModel;
import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.OTPLoggingService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UserScreenService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.HttpUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.mportal.services.MerConfDetailsService;
import com.ag.mportal.services.MerConfMasterService;
import com.google.gson.Gson;

@Component("com.ag.generic.action.approve.WsCardTxnPinReset")
public class WsCardTxnPinReset implements WisherForApprover {

	@Autowired
	MakerCheckerDataService mdService;
	
	@Autowired
	UserScreenService screenService;

	@Autowired
	UserLoginService userLoginService;

	@Autowired
	UtilService utilService;

	@Autowired
	SendNotifciation sendNotifciation;

	@Autowired
	OTPLoggingService loggingService;

	@Override
	public ResponseModel doProcess(RequestModel rm, MakerCheckerConfig mck, MakerCheckerData md) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {

			String remarks = (rm.getAdditionalData().containsKey("remarks"))
					? rm.getAdditionalData().get("remarks").toString()
					: null;
			
			String cardNo = null;
			String requestedData = md.getRequestedData();
			String finalData = new AGFuelUtil().parseData(requestedData,true);
			AgLogger.logInfo("REQUESTED DATA: " + finalData);			
			AgLogger.logInfo("REMARKS: " + remarks);
			
			String[] keyValuePairs = finalData.split(",");	        
	        for (String keyValuePair : keyValuePairs) {
	            if (keyValuePair.startsWith("Card:")) {
	            	cardNo = keyValuePair.substring("Card:".length());
	                break;
	            }
	        }
	        if (cardNo != null) {
	            System.out.println("CardNumber: " + cardNo);
	        } else {
	            System.out.println("CardNumber not found in the string.");
	        }
			String pinChangeURL = AppProp.getProperty("changePinUrl") + "card=" + cardNo + "&pin=0000&t=002";
			AgLogger.logInfo(pinChangeURL);
			String respData = new HttpUtil().doGet(pinChangeURL);
			AgLogger.logInfo("Response Data: " + respData);

			ChangePinServiceModel tempModel = new Gson().fromJson(respData, ChangePinServiceModel.class);
			if (null != tempModel.getResponce() && tempModel.getResponce().equals("0000")) {

				UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

				SendNotificationModel sdm = new SendNotificationModel();
				sdm.setAccountOpeningDate("N/A");
				sdm.setClosedBy("N/A");
				sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
				sdm.setComplaintNum("N/A");
				sdm.setMerchantName(ukl.getFirstName() + " " + ukl.getLastName());
				sdm.setPass("N/A");
				sdm.setResolution("N/A");
				sdm.setUserName(ukl.getUserCode());
				sdm.setReciverId(ukl.getUserId());
				AgLogger.logInfo("SMS/EMAIL NOT SENT FOR NOW");
				// sendNotifciation.doTask("001", "0002", "00001", sdm, ukl.getUserId());

				utilService.doSendAppNotificationOnly(Integer.parseInt(rm.getUserid()),
						AppProp.getProperty("reset.pin.notif"), AppProp.getProperty("reset.pin.notif"));

				response.setCode("0000");
				response.setMessage("Reset Pin Successful.");
			} else {
				response.setCode("1111");
				response.setMessage("Could Not Reset Pin.");
			}

			
			md.setStatus("APPROVED");
			md.setApprovedBy(Integer.parseInt(rm.getUserid()));
			md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
			md.setApproverRemarks(remarks);
			mdService.update(md);

			response.setCode("0000");
			response.setMessage("Record Approved.");

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
