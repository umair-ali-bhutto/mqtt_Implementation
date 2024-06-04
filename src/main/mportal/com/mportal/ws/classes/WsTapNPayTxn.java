package com.mportal.ws.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ag.generic.model.RequestModel;
import com.ag.generic.model.ResponseModel;
import com.ag.generic.service.Wisher;
import com.ag.generic.util.AgLogger;
import com.ag.mportal.entity.TapNPayTransactions;
import com.ag.mportal.services.TapNPayTransactionService;

@Component("com.mportal.ws.classes.WsTapNPayTxn")
public class WsTapNPayTxn implements Wisher {

	@Autowired
	TapNPayTransactionService tapNPayTransactionService;

	@Override
	public ResponseModel doProcess(RequestModel rm) {
		AgLogger.logInfo(rm.getUserid(), " CLASS CALLED " + rm.getMessage());
		ResponseModel response = new ResponseModel();
		try {
			String mid = rm.getAdditionalData().get("MID").toString();
			String tid = rm.getAdditionalData().get("TID").toString();
			List<TapNPayTransactions> lst = tapNPayTransactionService.fetchTxnByMidTidForViewAll(mid, tid);
			if (lst.size() != 0) {
				response.setCode("0000");
				response.setMessage("SUCCESS");
				HashMap<Object, Object> o = new HashMap<Object, Object>();
				o.put("lst", lst);
				response.setData(o);

			} else {
				response.setCode("0001");
				response.setMessage("No Record Found.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setCode("9999");
			response.setMessage("Something Went Wrong, Please try again.");
		}

		return response;

	}

}