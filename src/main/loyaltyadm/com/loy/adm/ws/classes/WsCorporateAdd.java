package com.loy.adm.ws.classes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.service.CorporateMasterService;

@Component("com.loy.adm.ws.classes.WsCorporateAdd")
public class WsCorporateAdd implements Wisher {

	@Autowired
	CorporateMasterService corporateMasterService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();

		try {

			String reqType = (rm.getAdditionalData().containsKey("reqType"))
					? rm.getAdditionalData().get("reqType").toString()
					: "N/A";

			switch (reqType) {
			case "ADD":
				response = addRequest(rm);
				break;
			case "INIT":
				response = initRequest(rm);
				break;

			default:
				response.setCode("9998");
				response.setMessage("Invalid Request Type.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;
	}

	ResponseModel initRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpid = corporateMasterService.fetchMaxCorpid();
			HashMap<Object, Object> obj = new HashMap<Object, Object>();
			obj.put("corpid", corpid);
			response.setCode("0000");
			response.setMessage("SUCCESS.");
			response.setData(obj);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

	ResponseModel addRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpid = (rm.getAdditionalData().containsKey("corpid"))
					? rm.getAdditionalData().get("corpid").toString()
					: null;
			String name = (rm.getAdditionalData().containsKey("name")) ? rm.getAdditionalData().get("name").toString()
					: null;
			String programName = (rm.getAdditionalData().containsKey("programName"))
					? rm.getAdditionalData().get("programName").toString()
					: null;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			LocalDate date = LocalDate.of(2030, Month.DECEMBER, 31);
			Date programEnd = sdf.parse(date.toString());
			
			CorporateMaster cm = new CorporateMaster();
			cm.setActive("Y");
			cm.setCorpid(corpid);
			cm.setCrBy(rm.getUserid());
			cm.setCrOn(new java.util.Date());
			cm.setName(name);
			cm.setProgramName(programName);
			cm.setAccountCr(null);
			cm.setAccountDr(null);
			cm.setProgramEnd(programEnd);
			cm.setProgramStart(new java.util.Date());
			cm.setRdmRatio(new BigDecimal("0"));
			corporateMasterService.insert(cm);

			response.setCode("0000");
			response.setMessage("SUCCESS.");
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}

}
