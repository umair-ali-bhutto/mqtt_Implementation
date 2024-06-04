package com.ag.generic.action.approve;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.ag.generic.util.DBUtil;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.PreauthMaster;
import com.ag.mportal.services.PreauthMasterService;

@Component("com.ag.generic.action.approve.WsPreauthGlobalParamsUpdate")
public class WsPreauthGlobalParamsUpdate implements WisherForApprover {

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

			String requestedData = md.getRequestedData();
			org.json.simple.JSONArray onjArray = (org.json.simple.JSONArray) JSONValue.parse(requestedData);
			JSONObject onj = (JSONObject) onjArray.get(0);
			PreauthMaster pm = preauthMasterService.fetchByID(Long.parseLong(onj.get("paid").toString()));
			if (pm != null) {

				SimpleDateFormat sdFrom = null;
				if (DBUtil.getDialect() != 1) {
					sdFrom = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					sdFrom = new SimpleDateFormat("dd-MM-yyyy");
				}

				SimpleDateFormat sdTo = null;
				if (DBUtil.getDialect() == 1) {
					sdTo = new SimpleDateFormat("yyyy-MM-dd");
				} else {
					sdTo = new SimpleDateFormat("dd-MM-yyyy");
				}

				Date cr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(onj.get("crOn").toString());

				pm.setActive(onj.get("status").toString());
				pm.setCorpid("00000");
				pm.setCrBy(onj.get("crBy").toString());
				pm.setUpdBy(rm.getUserid() + "");
				pm.setUpdOn(new Timestamp(new java.util.Date().getTime()));
				pm.setCrOn(new Timestamp(cr.getTime()));
				pm.setCurrency("586");
				if (onj.get("dateFrom").toString().contains("T")) {
					pm.setDateFrom(sdTo.parse(convertDate(onj.get("dateFrom").toString())));

				} else {
					pm.setDateFrom(sdFrom.parse(onj.get("dateFrom").toString()));
				}
				if (onj.get("dateTo").toString().contains("T")) {
					pm.setDateTo(sdTo.parse(convertDate(onj.get("dateTo").toString())));
				} else {
					pm.setDateTo(sdFrom.parse(onj.get("dateTo").toString()));
				}

				pm.setDefaultFlg("Y");
				pm.setName(onj.get("name").toString());
				pm.setSourceRef(UtilAccess.rrn());
				pm.setSubSeg(onj.get("subSeg").toString());
				pm.setTxnExp(Integer.parseInt(onj.get("txnExp").toString()));
				pm.setMaxPerc(Float.parseFloat(onj.get("maxPerc").toString()));
				pm.setMinPerc(Float.parseFloat(onj.get("minPerc").toString()));
				pm.setValTid(onj.get("valTid").toString());
				pm.setVersion(1f);

				boolean inserted = preauthMasterService.update(pm);

				if (inserted) {
					md.setStatus("APPROVED");
					md.setApprovedBy(Integer.parseInt(rm.getUserid()));
					md.setApprovedOn(new Timestamp(new java.util.Date().getTime()));
					md.setApproverRemarks(remarks);
					mdService.update(md);

				}

				response.setCode("0000");
				response.setMessage("Record Approved.");
			} else {
				response.setCode("0001");
				response.setMessage("No Record Found on ID.");

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong.");
		}

		return response;
	}

	private String convertDate(String date) {
		String finaldate = "";
		try {
			date = date.substring(0, date.indexOf("T"));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date df = (Date) sdf.parse(date);

			SimpleDateFormat sdfn = null;
			if (DBUtil.getDialect() == 1) {
				sdfn = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				sdfn = new SimpleDateFormat("dd-MM-yyyy");
			}
			finaldate = sdfn.format(df);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaldate;
	}

}
