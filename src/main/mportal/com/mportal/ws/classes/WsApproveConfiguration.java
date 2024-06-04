package com.mportal.ws.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerConfigService;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.Wisher;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;

@Component("com.mportal.ws.classes.WsApproveConfiguration")
public class WsApproveConfiguration implements Wisher {

	@Autowired
	private ApplicationContext context;

	@Autowired
	MakerCheckerDataService makerCheckerDataService;

	@Autowired
	MakerCheckerConfigService makerCheckerConfigService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		WisherForApprover wisher = null;
		try {
			String id = (rm.getAdditionalData().containsKey("id")) 
					? rm.getAdditionalData().get("id").toString() : null;
			String action = rm.getAdditionalData().get("action") == null ? null
					: rm.getAdditionalData().get("action").toString();
			
			Double dValue = Double.parseDouble(id);
			MakerCheckerData m = makerCheckerDataService.fetchByID(dValue.intValue());
		
			if (m != null) {
				if (m.getStatus().equals("PROCESS")) {
					MakerCheckerConfig c = makerCheckerConfigService.fetchByID(((Long) m.getConfigId()).intValue());
					if (c != null) {
						switch (action) {
						case "APPROVE":
							wisher = setupClass(c.getApproverActionClass());
							response = doCallClass(rm, wisher,c,m);
							break;
						case "REJECT":
							wisher = setupClass(c.getRejectionActionClass());
							response = doCallClass(rm, wisher,c,m);
							break;

						default:
							response.setCode("0001");
							response.setMessage("Invalid Message Type.");
							break;
						}

					} else {
						response.setCode("0002");
						response.setMessage("no configuration found.");
					}
				} else {
					response.setCode("0003");
					response.setMessage("data already updated.");
				}
			} else {
				response.setCode("0004");
				response.setMessage("no data found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

	WisherForApprover setupClass(String className) {
		WisherForApprover wisher = null;
		try {
			AgLogger.logDebug("", "calling class value "+className);
			wisher = (WisherForApprover) context.getBean(className);
		} catch (Exception ex) {
			ex.printStackTrace();
			AgLogger.logInfo("SETUP CLASS EXCEPTION: " + ex);
		}
		return wisher;
	}

	ResponseModel doCallClass(RequestModel rms, WisherForApprover wisher, MakerCheckerConfig mck, MakerCheckerData md) {
		ResponseModel rmks;
		try {
			rmks = wisher.doProcess(rms, mck, md);
		} catch (Exception e) {
			rmks = new ResponseModel();
			rmks.setCode("9991");
			rmks.setMessage("ERROR OCCURED.");
		}
		return rmks;
	}

}
