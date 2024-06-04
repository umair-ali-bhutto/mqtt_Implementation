package com.fuel.ws.classes;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.model.SendNotificationModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AssignmentUtil;
import com.ag.generic.util.SendNotifciation;

@Component("com.fuel.ws.classes.WsFuelAppLaunchComplaints")
public class WsFuelAppLaunchComplaints implements Wisher {
	@Autowired
	ComplaintsService complaintService;
	
	@Autowired
	UserLoginService userLoginService;
	
	@Autowired
	SendNotifciation sendNotifciation; 
	@Autowired
	UtilService utilService; 
	

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			try {

				String userid = rm.getUserid();
				String mid = rm.getAdditionalData().get("mid").toString();
				String type = rm.getAdditionalData().get("type").toString();
				String tid = rm.getAdditionalData().get("tid").toString();
				String maker = rm.getAdditionalData().get("maker").toString();
				String serialNum = rm.getAdditionalData().get("serialNum").toString();
				String model = rm.getAdditionalData().get("model").toString();
				String description = rm.getAdditionalData().get("description").toString();
				String subtype = rm.getAdditionalData().get("subtype").toString();

				if (!type.equalsIgnoreCase("-") && mid.length() != 0 && tid.length() != 0 && maker.length() != 0
						&& serialNum.length() != 0 && model.length() != 0) {
					Complaint c = new Complaint();
					Complaint cTemp = new Complaint();

					c.setAssignedDate(null);
					c.setAssignedTo(null);
					c.setClosureDate(null);
					c.setComplaintDesc(description);

					c.setEntryBy(userid);
					c.setEntryDate(new Timestamp(new java.util.Date().getTime()));
					c.setMaker(maker);
					c.setMid(mid);
					c.setModel(model);

					c.setCategory("002");
					c.setSubType(subtype);
					c.setType(type);
					c.setSerialNumber(serialNum);
					c.setStatus("NEW");
					c.setTid(tid);
					c.setComplaintType(type);
					c.setComplaintSubType(subtype);

					cTemp.setEntryDate(new Timestamp(new java.util.Date().getTime()));
					cTemp.setMaker(maker);
					cTemp.setMid(mid);
					cTemp.setModel(model);
					cTemp.setSerialNumber(serialNum);
					cTemp.setEntryBy(userid);

					String descr = AssignmentUtil.doConvertToJSONComplaint(cTemp);

					c.setDescription(descr);
					System.out.println(descr);
					try {
						UserLogin ukl = userLoginService.validetUserid(Integer.parseInt(rm.getUserid()));
						
						long g = complaintService.insertComplaint(c);
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
						sendNotifciation.doTask("002", type, subtype, sdm,ukl.getUserId());
						utilService.doSendAppNotificationOnly(Integer.parseInt(rm.getUserid()) , AppProp.getProperty("launch.compl.notif"), AppProp.getProperty("launch.compl.notif"));

						response.setCode("0000");
						response.setMessage("Complaint registered successfully. with complaint Id " + g);

					} catch (Exception e) {
						e.printStackTrace();
						response.setCode("8888");
						response.setMessage("Something went wrong.Please try again later.");
					}
				} else {
					response.setCode("8888");
					response.setMessage("Please enter valid data.");
				}
			} catch (Exception e) {
				response.setCode("8888");
				response.setMessage("Please enter valid data.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}
}
