package com.mportal.ws.classes;

import java.sql.Timestamp;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplCategory;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.ComplainDescriptionModel;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.service.ComplCategoryService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AssignmentUtil;
import com.ag.generic.util.SendNotifciation;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.ChainMerchant;
import com.ag.mportal.services.ChainMerchantsService;

@Component("com.mportal.ws.classes.WsLinkChainMerchant")
public class WsLinkChainMerchant implements Wisher{
	
	@Autowired
	ChainMerchantsService chainMerchantService;
	
	@Autowired
	ComplCategoryService complCategoryService;
	
	@Autowired
	SendNotifciation sendNotifciation; 
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	ComplaintsService complaintsService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String type = "N";
			String mid = rm.getAdditionalData().get("mid")== null ? null
					: rm.getAdditionalData().get("mid").toString();
			String maker = rm.getAdditionalData().get("maker")== null ? null
					: rm.getAdditionalData().get("maker").toString();
			String model = rm.getAdditionalData().get("model")== null ? null
					: rm.getAdditionalData().get("model").toString();
			String msisdn = rm.getAdditionalData().get("msisdn")== null ? null
					: rm.getAdditionalData().get("msisdn").toString();
			String email = rm.getAdditionalData().get("email")== null ? null
					: rm.getAdditionalData().get("email").toString();
			String cnic = rm.getAdditionalData().get("cnic")== null ? null
					: rm.getAdditionalData().get("cnic").toString();
			String serialNum = rm.getAdditionalData().get("serialNum")== null ? null
					: rm.getAdditionalData().get("serialNum").toString();
			String userId = rm.getUserid();
			
			String chainMID="N/A"; 

			if (!Objects.isNull(mid) && mid.length() != 0 && !Objects.isNull(maker) && maker.length() != 0 && !Objects.isNull(model) && model.length() != 0 && !Objects.isNull(msisdn) && msisdn.length() != 0
					&& !Objects.isNull(email) &&  email.length() != 0 && !Objects.isNull(cnic) &&  cnic.length() != 0) {
				
				ChainMerchant test = chainMerchantService.searchByTypeAndMID(type, mid);
				if (test != null) {
					ComplCategory cs = new ComplCategory();
					cs.setId(8);

					ComplCategory ctd = complCategoryService.fetchByID(cs);
					Complaint c = new Complaint();

					ComplainDescriptionModel tempComp = new ComplainDescriptionModel();

					c.setAssignedDate(null);
					c.setAssignedTo(null);
					c.setClosureDate(null);

					c.setComplaintDesc("N/A");

					c.setEntryBy(rm.getUserid()+"");
					c.setEntryDate(new Timestamp(new java.util.Date().getTime()));
					c.setMaker(maker);
					c.setMid(mid);
					c.setModel(model);

					if (ctd != null) {
						c.setCategory(ctd.getCode());
						c.setSubType(ctd.getSubTypeCode());
						c.setType(ctd.getTypeCode());
					}

					c.setSerialNumber(serialNum);
					c.setStatus("NEW");
					c.setTid("N/A");
					c.setComplaintType("N/A");
					c.setComplaintSubType("N/A");

					UserLogin user = userLoginService.validetUserid(Integer.valueOf(userId));
					if (user != null) {
						System.out.println("User Code Id" + ":" + user.getUserCode());
						chainMID = user.getUserCode();
					}

					tempComp.setEntryDate(new Timestamp(new java.util.Date().getTime()));
					tempComp.setMaker(maker);
					tempComp.setMid(mid);
					tempComp.setModel(model);
					tempComp.setSerialNumber(serialNum);
					tempComp.setEntryBy(rm.getUserid()+"");
					tempComp.setChainMID(chainMID);

					String descr = AssignmentUtil.doConvertToJSONComplaintDescription(tempComp);
					
					UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));

					c.setDescription(descr);
					//System.out.println(descr);
					long g = complaintsService.insertComplaint(c);
					SendNotificationModel sdm = new SendNotificationModel();
					sdm.setAccountOpeningDate("N/A");
					sdm.setClosedBy("N/A");
					sdm.setRequestComplDate(new Timestamp(new java.util.Date().getTime()));
					sdm.setClosureDate(new Timestamp(new java.util.Date().getTime()));
					sdm.setComplaintNum(String.valueOf(g));
					sdm.setMerchantName(ukl.getUserName());
					sdm.setPass("N/A");
					sdm.setResolution("N/A");
					sdm.setUserName("N/A");
					sdm.setReciverId(ukl.getUserId());
					sendNotifciation.doTask("002", "0001", "00001", sdm,ukl.getUserId());

					response = UtilAccess.generateResponse("0000", "Chain Merchant Link has been sent Successfully.");

				} else {
					response = UtilAccess.generateResponse("8888", "Merchant already linked.");
				}
			} else {
				response = UtilAccess.generateResponse("8888", "Unable to process request.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}