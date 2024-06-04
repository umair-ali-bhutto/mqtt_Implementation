package com.ag.generic.action.approve;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.entity.MakerCheckerConfig;
import com.ag.generic.entity.MakerCheckerData;
import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.MakerCheckerDataService;
import com.ag.generic.service.WisherForApprover;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.PreauthMaster;
import com.ag.mportal.entity.PreauthMerchant;
import com.ag.mportal.services.PreauthMasterService;

@Component("com.ag.generic.action.approve.WsPreauthGlobalParamsAdd")
public class WsPreauthGlobalParamsAdd implements WisherForApprover {

	@Autowired
	PreauthMasterService preauthMasterService;

	@Autowired
	MakerCheckerDataService mdService;

	@Override
	public ResponseModel doProcess(RequestModel rm, MakerCheckerConfig mck, MakerCheckerData md) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {

			String remarks = (rm.getAdditionalData().containsKey("remarks"))
					? rm.getAdditionalData().get("remarks").toString()
					: null;

			List<PreauthMaster> lst = preauthMasterService.fetchAll();

			if (lst.size() != 0) {
				response.setCode("9999");
				response.setMessage("Global Parameter with Active Status is Already Available.");
			} else {

				PreauthMaster pm = new PreauthMaster();
				String requestedData = md.getRequestedData();
				org.json.simple.JSONArray onjArray = (org.json.simple.JSONArray) JSONValue.parse(requestedData);
				JSONObject onj = (JSONObject) onjArray.get(0);

				SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");

				pm.setActive("Y");
				pm.setCorpid("00000");
				pm.setCrBy(rm.getUserid() + "");
				pm.setCrOn(new Timestamp(new java.util.Date().getTime()));
				pm.setCurrency("586");
				pm.setDateFrom(sd.parse(onj.get("dateFrom").toString()));
				pm.setDateTo(sd.parse(onj.get("dateTo").toString()));
				pm.setDefaultFlg("Y");
				pm.setName(onj.get("name").toString());
				pm.setSourceRef(UtilAccess.rrn());
				pm.setSubSeg(onj.get("cardEntry").toString());
				pm.setTxnExp(Integer.parseInt(onj.get("expiryDay").toString()));
				pm.setMaxPerc(Float.parseFloat(onj.get("maximum").toString()));
				pm.setMinPerc(Float.parseFloat(onj.get("minimum").toString()));
				pm.setValTid(onj.get("tid").toString());
				pm.setVersion(1f);

				boolean inserted = preauthMasterService.insert(pm);

				if (inserted) {
					md.setStatus("APPROVED");
					md.setApprovedBy(Integer.parseInt(rm.getUserid()));
					md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
					md.setApproverRemarks(remarks);
					mdService.update(md);

				}

				response.setCode("0000");
				response.setMessage("Record Approved.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

}
