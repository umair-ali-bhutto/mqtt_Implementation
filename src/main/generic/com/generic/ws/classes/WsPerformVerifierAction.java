package com.generic.ws.classes;

import java.sql.Timestamp;
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

@Component("com.generic.ws.classes.WsPerformVerifierAction")
public class WsPerformVerifierAction implements Wisher {

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
				String finalData = new AGFuelUtil().parseData(requestedData, true);
				AgLogger.logInfo("REQUESTED DATA: " + finalData);

				String identifier = (rm.getAdditionalData().containsKey("identifier"))
						? rm.getAdditionalData().get("identifier").toString()
						: null;
				String identifier2 = (rm.getAdditionalData().containsKey("identifier2"))
						? rm.getAdditionalData().get("identifier2").toString()
						: null;

				// Validation Process here
				String[] prefromValidation = AGFuelUtil.fuelProcValidate(rm.getCorpId(), identifier2, finalData);
				if (!prefromValidation[0].equals("00")) {
					response.setCode("9999");
					response.setMessage(prefromValidation[1]);
					return response;
				}

				String screenId = (rm.getAdditionalData().containsKey("screenId"))
						? rm.getAdditionalData().get("screenId").toString()
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

				if (screenId == null || identifier == null || actionType == null || remarksForApprover == null) {
					response.setCode("9999");
					response.setMessage("Screen Id or Identifier or Action Type or Remarks must not be empty.");
					return response;
				}

				MakerCheckerData md = makerCheckerDataService.fetchByUniqueIdentifierVerifier(Long.parseLong(screenId),
						identifier, identifier2, identifier3);

				MakerCheckerData md2 = makerCheckerDataService.fetchByUniqueIdentifier(Long.parseLong(screenId),
						identifier, identifier2, identifier3);

				if (md != null || md2 != null) {
					AgLogger.logInfo(md != null ? "PENDING AT VERIFIER" : md2 != null ? "PENDING AT APPROVER" : "");
					response.setCode("9999");
					response.setMessage("Record Already Pending at Verifier/Approver End.");
				} else {

					MakerCheckerConfig mk = makerCheckerConfigService.fetchByScreenID(Integer.parseInt(screenId));

					if (mk != null) {

						MakerCheckerData m = new MakerCheckerData();
						m.setActionType(actionType);
						m.setScreenId(Long.parseLong(screenId));
						m.setConfigId(mk.getId());
						m.setPageName(m.getPageName());
						m.setRequestedBy(Integer.parseInt(rm.getUserid()));
						m.setRequestedByRemarks(remarksForApprover);
						String d;

						d = rm.getAdditionalData().get("data").toString();

						m.setRequestedData(d);
						m.setRequestedOn(new Timestamp(new java.util.Date().getTime()));
						m.setStatus("PROCESS_FOR_VERIFIER");
						m.setUniqueidentifier(identifier);
						m.setUniqueidentifier2(identifier2);
						m.setUniqueidentifier3(identifier3);
						m.setViewerId(Integer.parseInt(rm.getUserid()));
						makerCheckerDataService.insert(m);
						response.setCode("0000");
						response.setMessage("Record Sent For Checker.");
					} else {
						response.setCode("9999");
						response.setMessage("Invalid Screen Based Configuration.");
					}
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