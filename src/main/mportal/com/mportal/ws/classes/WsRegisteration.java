package com.mportal.ws.classes;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.ComplCategory;
import com.ag.generic.entity.Complaint;
import com.ag.generic.entity.UserLogin;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.ComplCategoryService;
import com.ag.generic.service.ComplaintsService;
import com.ag.generic.service.UserLoginService;
import com.ag.generic.service.UtilService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.AssignmentUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.QueueLog;
import com.ag.mportal.services.QueueLogService;

@Component("com.mportal.ws.classes.WsRegisteration")
public class WsRegisteration implements Wisher{
	
	@Autowired
	UtilService utilService;
	
	@Autowired
	UserLoginService userLoginService;
	@Autowired
	QueueLogService queueLogService;
	@Autowired
	ComplaintsService complaintsService;
	@Autowired
	ComplCategoryService complCategoryService;

	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			QueueLog queueLog = new QueueLog();
			String mid= (String) rm.getAdditionalData().get("mid");
			String tid= (String) rm.getAdditionalData().get("tid");
			String model= (String) rm.getAdditionalData().get("model");
			String mobile= (String) rm.getAdditionalData().get("mobile");
			String email= (String) rm.getAdditionalData().get("email");
			String serial= (String) rm.getAdditionalData().get("serial");
			
			queueLog.setMid(mid);
			queueLog.setTid(tid);
			queueLog.setModel(model);
			queueLog.setMsdisn(mobile);
			queueLog.setEmail(email);
			queueLog.setSerialNum(serial);
			queueLog.setEntryDate(new Timestamp(new java.util.Date().getTime()));

			String[] validateQueueLog = new UtilAccess().validateQueueLog(queueLog);
			if (validateQueueLog[0].equals("00")) {
				String[] validateMIDTID = utilService.fetchMerchantTerminalUpd(queueLog.getMid(),
						queueLog.getTid());
				AgLogger.logInfo("validateMIDTID REPSONSE : " + validateMIDTID[0] + " | " + validateMIDTID[1] + " | "
						+ validateMIDTID[2] + " | " + validateMIDTID[3]);

				if (validateMIDTID[0].equals("0000")) {
					AgLogger.logInfo("validateMIDTID SUCCESS : ");
					UserLogin usersLogin = userLoginService.validetUser(queueLog.getMid(),rm.getCorpId());

					if (usersLogin != null) {
							queueLog.setMaxHits(0);
							queueLog.setStatus("FAILURE");
							queueLog.setStatusMsg("MID EXISTS");
							response.setCode("0001");
							response.setMessage("Merchant ID Already Exists.");
							queueLogService.insertLog(queueLog);
					} else {
						QueueLog log = queueLogService.fetchQueueLogDetails(queueLog.getMid());
						if (log != null) {
								queueLog.setMaxHits(0);
								queueLog.setStatus("FAILURE");
								queueLog.setStatusMsg("RECORD ALREADY IN QUEUE");
								response.setCode("0001");
								response.setMessage("Record Already Exists in queue.");
								queueLogService.insertLog(queueLog);
						} else {
								queueLog.setMaxHits(0);
								queueLog.setStatus("NEW_REC");
								response.setCode("0000");
								response.setMessage("User Request Recieved.");
								queueLogService.insertLog(queueLog);
									doInsertAssignment(
											AssignmentUtil.doConvertToJSONUser(queueLog), queueLog.getMid(),
											queueLog.getTid(),rm.getCorpId());
						}
					}

				} else {
						queueLog.setMaxHits(0);
						queueLog.setStatus("FAILURE");
						queueLog.setStatusMsg("MID TID VALIDATION FAILD ");
						queueLogService.insertLog(queueLog);
						response.setCode("0001");
						response.setMessage("MID TID VALIDATION FAILED");
				}

			} else {
					queueLog.setMaxHits(0);
					queueLog.setStatusMsg(validateQueueLog[1]);
					queueLog.setStatus("FAILURE");
					queueLogService.insertLog(queueLog);
					response.setCode("0001");
					response.setMessage("SOMETHING WENT WRONG.");
			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
			AgLogger.logInfo("EXCEPTION  :  " + this.getClass().getName() + ex);
			ex.printStackTrace();
		}

		return response;
	}
	
	public void doInsertAssignment(String desc, String mid, String tid,String corpId) {
		ComplCategory cs = new ComplCategory();
		cs.setId(1);

		ComplCategory ctd = complCategoryService.fetchByID(cs);
		Complaint c = new Complaint();
		c.setAssignedDate(null);
		c.setAssignedTo(null);
		c.setClosureDate(null);
		c.setDescription(desc);
		//Get Entry By From CorpID Here.
		c.setEntryBy(getUserIdFromCorpId(corpId));
		c.setEntryDate(new Timestamp(new java.util.Date().getTime()));
		c.setMaker("N/A");
		c.setMid(mid);
		c.setModel("N/A");

		if (ctd != null) {
			c.setCategory(ctd.getCode());
			c.setSubType(ctd.getSubTypeCode());
			c.setType(ctd.getTypeCode());
		}

		c.setSerialNumber("N/A");
		c.setStatus("NEW");
		c.setTid(tid);
		c.setComplaintType("N/A");
		c.setComplaintSubType("N/A");
		try {
			complaintsService.insertComplaint(c);
		} catch (Exception e) {
			AgLogger.logTrace(getClass()," EXCEPTION ASSIGNMENT ", e);
		}
	}
	
	
	String getUserIdFromCorpId(String corpId) {
		int k = 0;
		String prop = AppProp.getProperty("reg.get.entryby.from.corpid");
		String[] br = prop.split(",");
		for(String s:br) {
			String[] m = s.split(":");
			if(m[0].equals(corpId)) {
				AgLogger.logInfo("........."+m[1]);
				return m[1];
			}
		}
		AgLogger.logInfo("........."+k);
		return k+"";
	}

}
