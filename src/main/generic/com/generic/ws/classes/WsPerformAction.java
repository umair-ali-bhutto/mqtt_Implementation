package com.generic.ws.classes;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;

@Component("com.generic.ws.classes.WsPerformAction")
public class WsPerformAction implements Wisher {

	@Autowired
	private ApplicationContext context;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		Integer complaintId = null;
		Integer complaintAssignmentId = null;
		Wisher wisher = null;

		String compId = rm.getAdditionalData().get("compId") == null ? null
				: rm.getAdditionalData().get("compId").toString();
		String compAssignId = rm.getAdditionalData().get("compAssignId") == null ? null
				: rm.getAdditionalData().get("compAssignId").toString();
		String actionType = rm.getAdditionalData().get("actionType") == null ? null
				: rm.getAdditionalData().get("actionType").toString();
		String className = rm.getAdditionalData().get("className") == null ? null
				: rm.getAdditionalData().get("className").toString();

		try {
			complaintId = Integer.parseInt(compId);
		} catch (Exception ex) {
			complaintId = null;
		}

		try {
			complaintAssignmentId = Integer.parseInt(compAssignId);
		} catch (Exception ex) {
			complaintAssignmentId = null;
		}

		if (Objects.isNull(complaintId) || Objects.isNull(complaintAssignmentId) || Objects.isNull(className)
				|| className.isEmpty()) {
			response.setCode("9999");
			response.setMessage("Required Parameters are missing.");
			return response;
		}

		wisher = setupClass(className);
		
		if (!Objects.isNull(wisher)) {
			response = doCallClass(rm, wisher);
		} else {
			AgLogger.logInfo("CLASS NOT INITIALIZED -> " + className);
			response.setCode("9991");
			response.setMessage("ERROR OCCURED.");
		}

		

		return response;

	}

	Wisher setupClass(String className) {
		Wisher wisher = null;
		try {
			wisher = (Wisher) context.getBean(className);
			//AgLogger.logInfo("CLASS SETUP FOR -> " + wisher.getClass());
		} catch (Exception ex) {
			AgLogger.logInfo("SETUP CLASS EXCEPTION: " + ex);
		}
		return wisher;
	}

	ResponseModel doCallClass(RequestModel rms, Wisher wisher) {
		ResponseModel rmks;
		try {
			rmks = wisher.doProcess(rms);
		} catch (Exception e) {
			rmks = new ResponseModel();
			rmks.setCode("9991");
			rmks.setMessage("ERROR OCCURED.");
		}
		return rmks;
	}

}
