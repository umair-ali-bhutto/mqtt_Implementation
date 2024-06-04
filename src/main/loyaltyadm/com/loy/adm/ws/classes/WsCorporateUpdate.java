package com.loy.adm.ws.classes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.loy.adm.entity.CorporateMaster;
import com.ag.loy.adm.service.CorporateMasterService;

@Component("com.loy.adm.ws.classes.WsCorporateUpdate")
public class WsCorporateUpdate implements Wisher{
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
			case "FETCH":
				response = fetchRequest(rm);
				break;
			case "INIT":
				response = initRequest(rm);
				break;
			case "UPDATE":
				response = UpdateRequest(rm);
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
			List<CorporateMaster> lst = corporateMasterService.fetchAll();
			if (lst.size() != 0) {
				HashMap<Object, Object> obj = new HashMap<Object, Object>();
				obj.put("corporates", lst);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
				response.setData(obj);
			} else {
				response.setCode("0001");
				response.setMessage("No Corporates Found.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

	ResponseModel fetchRequest(RequestModel rm) {
		ResponseModel response = new ResponseModel();
		try {
			String corpid = null;
			if (!rm.getAdditionalData().get("corpid").toString().equals("null")) {
				corpid = rm.getAdditionalData().get("corpid").toString();
			}

			String status = (rm.getAdditionalData().containsKey("statusfilter"))
					? rm.getAdditionalData().get("statusfilter").toString()
					: null;

			List<CorporateMaster> lst = corporateMasterService.fetchByFilter(corpid, status);

			if (lst.size() != 0) {
				HashMap<Object, Object> okj = new HashMap<Object, Object>();
				okj.put("lst", lst);
				response.setCode("0000");
				response.setMessage("SUCCESS.");
				response.setData(okj);
			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;

	}
	ResponseModel UpdateRequest(RequestModel rm) {
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
			String status = (rm.getAdditionalData().containsKey("status"))
					? rm.getAdditionalData().get("status").toString()
					: null;
			
			String cron = (rm.getAdditionalData().containsKey("cron"))
					? rm.getAdditionalData().get("cron").toString()
					: null;
			String crby = (rm.getAdditionalData().containsKey("crby"))
					? rm.getAdditionalData().get("crby").toString()
					: null;
			String programstartdate = (rm.getAdditionalData().containsKey("programstartdate"))
					? rm.getAdditionalData().get("programstartdate").toString()
					: null;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			LocalDate date = LocalDate.of(2030, Month.DECEMBER, 31);
			Date programEnd = sdf.parse(date.toString());
			
			Date crOn = sdf.parse(cron.toString());
			Date programStartDate = sdf.parse(programstartdate.toString());

			CorporateMaster cm = new CorporateMaster();
			cm.setActive(status);
			cm.setCorpid(corpid);
			cm.setCrBy(crby);
			cm.setCrOn(crOn);
			cm.setProgramStart(programStartDate);
			cm.setName(name);
			cm.setProgramName(programName);
			cm.setAccountCr(null);
			cm.setAccountDr(null);
			cm.setProgramEnd(programEnd);
			cm.setRdmRatio(new BigDecimal("0"));
			cm.setUpdBy(rm.getUserid());
			cm.setUpdOn(new java.util.Date());
			
			corporateMasterService.update(cm);

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
