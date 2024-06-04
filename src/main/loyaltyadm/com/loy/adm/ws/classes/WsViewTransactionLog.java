package com.loy.adm.ws.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.generic.util.UtilAccess;
import com.ag.loy.adm.entity.AccountPaymentsLog;
import com.ag.loy.adm.service.AccountPaymentsLogService;

@Component("com.loy.adm.ws.classes.WsViewTransactionLog")
public class WsViewTransactionLog implements Wisher {
	

	@Autowired
	AccountPaymentsLogService accountPaymentsLogService;

	public ResponseModel doProcess(RequestModel rm) {

		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {

			String userId = Objects.isNull(rm.getAdditionalData().get("userId")) ? null
					: rm.getAdditionalData().get("userId").toString().trim();

			String devBatch = Objects.isNull(rm.getAdditionalData().get("devBatch")) ? null
					: rm.getAdditionalData().get("devBatch").toString().trim();

			String accountId = Objects.isNull(rm.getAdditionalData().get("accountId")) ? null
					: rm.getAdditionalData().get("accountId").toString().trim();

			String frDate = Objects.isNull(rm.getAdditionalData().get("frDate")) ? null
					: rm.getAdditionalData().get("frDate").toString().trim();

			String toDate = Objects.isNull(rm.getAdditionalData().get("toDate")) ? null
					: rm.getAdditionalData().get("toDate").toString().trim();

			String customerId = Objects.isNull(rm.getAdditionalData().get("customerId")) ? null
					: rm.getAdditionalData().get("customerId").toString().trim();

			String corporateId = Objects.isNull(rm.getAdditionalData().get("corporateId")) ? null
					: rm.getAdditionalData().get("corporateId").toString().trim();

			if (!Objects.isNull(frDate)) {
				if (Objects.isNull(toDate)) {
					response.setCode("0001");
					response.setMessage("Please select date range correctly.");
					return response;
				}
			} else if (!Objects.isNull(toDate)) {
				if (Objects.isNull(frDate)) {
					response.setCode("0001");
					response.setMessage("Please select date range correctly.");
					return response;
				}
			}

			ArrayList<AccountPaymentsLog> lst = accountPaymentsLogService.fetch(userId, devBatch, accountId, frDate, toDate,
					customerId, corporateId);

			if (Objects.isNull(lst) || lst.isEmpty()) {
				return UtilAccess.generateResponse("8888", "No Record Found.");
			} else {
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				map.put("lstViewTransactionLog", lst);
				return UtilAccess.generateResponse("0000", "SUCCESS", map);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}
		return response;
	}

}