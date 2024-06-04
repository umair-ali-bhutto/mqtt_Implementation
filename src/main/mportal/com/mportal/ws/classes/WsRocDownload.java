package com.mportal.ws.classes;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.UtilAccess;
import com.ag.mportal.entity.PosEntryModeConfig;
import com.ag.mportal.model.ReportModel;
import com.ag.mportal.services.PosEntryModeConfigService;
import com.ag.mportal.services.RocConfigurationService;
import com.ag.mportal.services.TxnDetailsService;

@Component("com.mportal.ws.classes.WsRocDownload")
public class WsRocDownload implements Wisher {

	@Autowired
	TxnDetailsService txnDetailsService;

	@Autowired
	RocConfigurationService rocConfigurationService;

	@Autowired
	PosEntryModeConfigService posEntryModeConfigService;

	public ResponseModel doProcess(RequestModel rm) {
		ResponseModel response = new ResponseModel();

		response.setCode("0000");
		response.setMessage("SUCCESS.");

		String txnDetailId = Objects.isNull(rm.getAdditionalData().get("txnDetailId")) ? null
				: rm.getAdditionalData().get("txnDetailId").toString();

		if (Objects.isNull(txnDetailId)) {
			response.setCode("0002");
			response.setMessage("Parameter is missing.");
			return response;
		}

		ReportModel txnDetail = txnDetailsService.fetchTxnDetailByIdForRoc(txnDetailId);

		if (Objects.isNull(txnDetail)) {
			response.setCode("0003");
			response.setMessage("Record not found.");
			return response;
		}

		String reportPath = rocConfigurationService.fetch(txnDetail.getType(), txnDetail.getPosEntryMode(),
				txnDetail.getModel(),rm.getCorpId());

		PosEntryModeConfig pc = posEntryModeConfigService.fetchByMode(txnDetail.getPosEntryMode());


		try {
			response = UtilAccess.downloadRoc(txnDetail, reportPath, pc);
		} catch (Exception ex) {
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}