package com.mportal.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TmsParameters;
import com.ag.mportal.entity.TmsParametersDefaultValues;
import com.ag.mportal.services.impl.TmsParametersServiceImpl;

@Component("com.mportal.ws.classes.WsAddTmsProfileCheck")
public class WsAddTmsProfileCheck implements Wisher {

	@Autowired
	TmsParametersServiceImpl tmsParametersServiceImpl;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {
			List<TmsParameters> tmsParamsFin = tmsParametersServiceImpl.fetchByTid(
					rm.getAdditionalData().containsKey("tid") ? rm.getAdditionalData().get("tid").toString() : "N/A");
			
			String posModel = rm.getAdditionalData().get("posModel").toString(); 

			if (tmsParamsFin.size() != 0) {
				response.setCode("0001");
				response.setMessage("Terminal Already Exists.");
			} else {
				

				List<TmsParametersDefaultValues> tmsParams = tmsParametersServiceImpl.fetchAllDefaultParameters();
				List<TmsParameters> parms = new ArrayList<TmsParameters>();
				for (TmsParametersDefaultValues d : tmsParams) {
					TmsParameters s = new TmsParameters();
					s.setAppNm("BAF");
					s.setDldType("FP");
					s.setFamNm(posModel);
					s.setFlag("P");
					s.setParInfo("");
					s.setParNameLoc(d.getParamName());
					s.setPartId(rm.getAdditionalData().get("tid").toString());
					s.setpSize(0);
					s.setSeqInfo(0);
					s.setValue(d.getDefaultValue());
					parms.add(s);
				}

				response.setCode("0000");
				response.setMessage("SUCCESS");
				HashMap<Object, Object> i = new HashMap<Object, Object>();
				i.put("defaultParams", parms);
				response.setData(i);

			}

		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}