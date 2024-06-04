package com.generic.ws.classes;

import java.sql.Timestamp;
import java.util.Date;

import javax.imageio.ImageIO;
import org.castor.core.util.Base64Decoder;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.fuel.util.AGFuelUtil;
import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.prop.AppProp;
import com.ag.generic.service.MakerCheckerConfigService;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

@Component("com.generic.ws.classes.WsPerformVerifierMakerAction")
public class WsPerformVerifierMakerAction implements Wisher {

	@Autowired
	MakerCheckerConfigService makerCheckerConfigService;

	@Autowired
	MakerCheckerDataService makerCheckerDataService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());

		try {
			if (rm.getAdditionalData().containsKey("data")) {
				
				String requestedData = rm.getAdditionalData().get("data").toString();
				String finalData = new AGFuelUtil().parseData(requestedData,true);
				AgLogger.logInfo("REQUESTED DATA: " + finalData);
				
				String identifier = (rm.getAdditionalData().containsKey("identifier"))
						? rm.getAdditionalData().get("identifier").toString()
						: null;
				String identifier2 = (rm.getAdditionalData().containsKey("identifier2"))
						? rm.getAdditionalData().get("identifier2").toString()
						: null;
				
				
				//Validation Process here
				String[] prefromValidation = AGFuelUtil.fuelProcValidate(rm.getCorpId(), identifier2, finalData);
				if(!prefromValidation[0].equals("00")) {
					response.setCode("9999");
					response.setMessage(prefromValidation[1]);
					return response;
				}
				

				String recId = (rm.getAdditionalData().containsKey("recId"))
						? rm.getAdditionalData().get("recId").toString()
						: null;
				
				String identifier3 = (rm.getAdditionalData().containsKey("identifier3"))
						? rm.getAdditionalData().get("identifier3").toString()
						: null;

				String actionType = (rm.getAdditionalData().containsKey("actionType"))
						? rm.getAdditionalData().get("actionType").toString()
						: null;

				String remarksForApprover = (rm.getAdditionalData().containsKey("remarks"))
						? rm.getAdditionalData().get("remarks").toString()
						: null;

				MakerCheckerData mk = makerCheckerDataService.fetchByRecID(Long.parseLong(recId));

				if (mk != null) {
					mk.setVerifiedBy(rm.getUserid());
					mk.setVerifiedOn(new Timestamp(new java.util.Date().getTime()));

					String d = rm.getAdditionalData().get("data").toString();

					mk.setStatus("PROCESS");
					mk.setRequestedData(d);
					makerCheckerDataService.update(mk);
					response.setCode("0000");
					response.setMessage("Record Sent For Approval.");

				}

			}

			else {
				response.setCode("9999");
				response.setMessage("Validation Failed.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}
